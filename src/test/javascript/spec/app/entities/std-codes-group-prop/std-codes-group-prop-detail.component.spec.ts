/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StdCodesGroupPropDetailComponent from '@/entities/std-codes-group-prop/std-codes-group-prop-details.vue';
import StdCodesGroupPropClass from '@/entities/std-codes-group-prop/std-codes-group-prop-details.component';
import StdCodesGroupPropService from '@/entities/std-codes-group-prop/std-codes-group-prop.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StdCodesGroupProp Management Detail Component', () => {
    let wrapper: Wrapper<StdCodesGroupPropClass>;
    let comp: StdCodesGroupPropClass;
    let stdCodesGroupPropServiceStub: SinonStubbedInstance<StdCodesGroupPropService>;

    beforeEach(() => {
      stdCodesGroupPropServiceStub = sinon.createStubInstance<StdCodesGroupPropService>(StdCodesGroupPropService);

      wrapper = shallowMount<StdCodesGroupPropClass>(StdCodesGroupPropDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stdCodesGroupPropService: () => stdCodesGroupPropServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStdCodesGroupProp = { id: 123 };
        stdCodesGroupPropServiceStub.find.resolves(foundStdCodesGroupProp);

        // WHEN
        comp.retrieveStdCodesGroupProp(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stdCodesGroupProp).toBe(foundStdCodesGroupProp);
      });
    });
  });
});
