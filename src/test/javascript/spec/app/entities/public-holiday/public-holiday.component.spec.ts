/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PublicHolidayComponent from '@/entities/public-holiday/public-holiday.vue';
import PublicHolidayClass from '@/entities/public-holiday/public-holiday.component';
import PublicHolidayService from '@/entities/public-holiday/public-holiday.service';

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
  describe('PublicHoliday Management Component', () => {
    let wrapper: Wrapper<PublicHolidayClass>;
    let comp: PublicHolidayClass;
    let publicHolidayServiceStub: SinonStubbedInstance<PublicHolidayService>;

    beforeEach(() => {
      publicHolidayServiceStub = sinon.createStubInstance<PublicHolidayService>(PublicHolidayService);
      publicHolidayServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicHolidayClass>(PublicHolidayComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          publicHolidayService: () => publicHolidayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicHolidayServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublicHolidays();
      await comp.$nextTick();

      // THEN
      expect(publicHolidayServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publicHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicHolidayServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePublicHoliday();
      await comp.$nextTick();

      // THEN
      expect(publicHolidayServiceStub.delete.called).toBeTruthy();
      expect(publicHolidayServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
