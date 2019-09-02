/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeWeekdayComponent from '@/entities/office-weekday/office-weekday.vue';
import OfficeWeekdayClass from '@/entities/office-weekday/office-weekday.component';
import OfficeWeekdayService from '@/entities/office-weekday/office-weekday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('OfficeWeekday Management Component', () => {
    let wrapper: Wrapper<OfficeWeekdayClass>;
    let comp: OfficeWeekdayClass;
    let officeWeekdayServiceStub: SinonStubbedInstance<OfficeWeekdayService>;

    beforeEach(() => {
      officeWeekdayServiceStub = sinon.createStubInstance<OfficeWeekdayService>(OfficeWeekdayService);
      officeWeekdayServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeWeekdayClass>(OfficeWeekdayComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeWeekdayService: () => officeWeekdayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeWeekdayServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOfficeWeekdays();
      await comp.$nextTick();

      // THEN
      expect(officeWeekdayServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeWeekdays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      officeWeekdayServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOfficeWeekday();
      await comp.$nextTick();

      // THEN
      expect(officeWeekdayServiceStub.delete.called).toBeTruthy();
      expect(officeWeekdayServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
