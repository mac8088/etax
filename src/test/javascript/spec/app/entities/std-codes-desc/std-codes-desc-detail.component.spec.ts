/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StdCodesDescDetailComponent from '@/entities/std-codes-desc/std-codes-desc-details.vue';
import StdCodesDescClass from '@/entities/std-codes-desc/std-codes-desc-details.component';
import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StdCodesDesc Management Detail Component', () => {
    let wrapper: Wrapper<StdCodesDescClass>;
    let comp: StdCodesDescClass;
    let stdCodesDescServiceStub: SinonStubbedInstance<StdCodesDescService>;

    beforeEach(() => {
      stdCodesDescServiceStub = sinon.createStubInstance<StdCodesDescService>(StdCodesDescService);

      wrapper = shallowMount<StdCodesDescClass>(StdCodesDescDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stdCodesDescService: () => stdCodesDescServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStdCodesDesc = { id: 123 };
        stdCodesDescServiceStub.find.resolves(foundStdCodesDesc);

        // WHEN
        comp.retrieveStdCodesDesc(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stdCodesDesc).toBe(foundStdCodesDesc);
      });
    });
  });
});
