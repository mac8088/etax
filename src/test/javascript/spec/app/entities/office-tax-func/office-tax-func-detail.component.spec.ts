/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeTaxFuncDetailComponent from '@/entities/office-tax-func/office-tax-func-details.vue';
import OfficeTaxFuncClass from '@/entities/office-tax-func/office-tax-func-details.component';
import OfficeTaxFuncService from '@/entities/office-tax-func/office-tax-func.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OfficeTaxFunc Management Detail Component', () => {
    let wrapper: Wrapper<OfficeTaxFuncClass>;
    let comp: OfficeTaxFuncClass;
    let officeTaxFuncServiceStub: SinonStubbedInstance<OfficeTaxFuncService>;

    beforeEach(() => {
      officeTaxFuncServiceStub = sinon.createStubInstance<OfficeTaxFuncService>(OfficeTaxFuncService);

      wrapper = shallowMount<OfficeTaxFuncClass>(OfficeTaxFuncDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeTaxFuncService: () => officeTaxFuncServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOfficeTaxFunc = { id: 123 };
        officeTaxFuncServiceStub.find.resolves(foundOfficeTaxFunc);

        // WHEN
        comp.retrieveOfficeTaxFunc(123);
        await comp.$nextTick();

        // THEN
        expect(comp.officeTaxFunc).toBe(foundOfficeTaxFunc);
      });
    });
  });
});
