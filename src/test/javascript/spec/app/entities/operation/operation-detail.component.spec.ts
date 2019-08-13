/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OperationDetailComponent from '@/entities/operation/operation-details.vue';
import OperationClass from '@/entities/operation/operation-details.component';
import OperationService from '@/entities/operation/operation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Operation Management Detail Component', () => {
    let wrapper: Wrapper<OperationClass>;
    let comp: OperationClass;
    let operationServiceStub: SinonStubbedInstance<OperationService>;

    beforeEach(() => {
      operationServiceStub = sinon.createStubInstance<OperationService>(OperationService);

      wrapper = shallowMount<OperationClass>(OperationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { operationService: () => operationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOperation = { id: 123 };
        operationServiceStub.find.resolves(foundOperation);

        // WHEN
        comp.retrieveOperation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.operation).toBe(foundOperation);
      });
    });
  });
});
