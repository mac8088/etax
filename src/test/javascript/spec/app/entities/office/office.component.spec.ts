/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeComponent from '@/entities/office/office.vue';
import OfficeClass from '@/entities/office/office.component';
import OfficeService from '@/entities/office/office.service';

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
  describe('Office Management Component', () => {
    let wrapper: Wrapper<OfficeClass>;
    let comp: OfficeClass;
    let officeServiceStub: SinonStubbedInstance<OfficeService>;

    beforeEach(() => {
      officeServiceStub = sinon.createStubInstance<OfficeService>(OfficeService);
      officeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeClass>(OfficeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeService: () => officeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOffices();
      await comp.$nextTick();

      // THEN
      expect(officeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.offices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      officeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOffice();
      await comp.$nextTick();

      // THEN
      expect(officeServiceStub.delete.called).toBeTruthy();
      expect(officeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
