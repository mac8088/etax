/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OperationComponent from '@/entities/operation/operation.vue';
import OperationClass from '@/entities/operation/operation.component';
import OperationService from '@/entities/operation/operation.service';

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
  describe('Operation Management Component', () => {
    let wrapper: Wrapper<OperationClass>;
    let comp: OperationClass;
    let operationServiceStub: SinonStubbedInstance<OperationService>;

    beforeEach(() => {
      operationServiceStub = sinon.createStubInstance<OperationService>(OperationService);
      operationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OperationClass>(OperationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          operationService: () => operationServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      operationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOperations();
      await comp.$nextTick();

      // THEN
      expect(operationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.operations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      operationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOperation();
      await comp.$nextTick();

      // THEN
      expect(operationServiceStub.delete.called).toBeTruthy();
      expect(operationServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
