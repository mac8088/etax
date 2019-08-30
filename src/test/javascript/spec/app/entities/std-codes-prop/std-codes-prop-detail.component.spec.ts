/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StdCodesPropDetailComponent from '@/entities/std-codes-prop/std-codes-prop-details.vue';
import StdCodesPropClass from '@/entities/std-codes-prop/std-codes-prop-details.component';
import StdCodesPropService from '@/entities/std-codes-prop/std-codes-prop.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StdCodesProp Management Detail Component', () => {
    let wrapper: Wrapper<StdCodesPropClass>;
    let comp: StdCodesPropClass;
    let stdCodesPropServiceStub: SinonStubbedInstance<StdCodesPropService>;

    beforeEach(() => {
      stdCodesPropServiceStub = sinon.createStubInstance<StdCodesPropService>(StdCodesPropService);

      wrapper = shallowMount<StdCodesPropClass>(StdCodesPropDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stdCodesPropService: () => stdCodesPropServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStdCodesProp = { id: 123 };
        stdCodesPropServiceStub.find.resolves(foundStdCodesProp);

        // WHEN
        comp.retrieveStdCodesProp(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stdCodesProp).toBe(foundStdCodesProp);
      });
    });
  });
});
