/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StdCodesDetailComponent from '@/entities/std-codes/std-codes-details.vue';
import StdCodesClass from '@/entities/std-codes/std-codes-details.component';
import StdCodesService from '@/entities/std-codes/std-codes.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StdCodes Management Detail Component', () => {
    let wrapper: Wrapper<StdCodesClass>;
    let comp: StdCodesClass;
    let stdCodesServiceStub: SinonStubbedInstance<StdCodesService>;

    beforeEach(() => {
      stdCodesServiceStub = sinon.createStubInstance<StdCodesService>(StdCodesService);

      wrapper = shallowMount<StdCodesClass>(StdCodesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stdCodesService: () => stdCodesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStdCodes = { id: 123 };
        stdCodesServiceStub.find.resolves(foundStdCodes);

        // WHEN
        comp.retrieveStdCodes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stdCodes).toBe(foundStdCodes);
      });
    });
  });
});
