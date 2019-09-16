/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StdCodesGroupDetailComponent from '@/entities/std-codes-group/std-codes-group-details.vue';
import StdCodesGroupClass from '@/entities/std-codes-group/std-codes-group-details.component';
import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StdCodesGroup Management Detail Component', () => {
    let wrapper: Wrapper<StdCodesGroupClass>;
    let comp: StdCodesGroupClass;
    let stdCodesGroupServiceStub: SinonStubbedInstance<StdCodesGroupService>;

    beforeEach(() => {
      stdCodesGroupServiceStub = sinon.createStubInstance<StdCodesGroupService>(StdCodesGroupService);

      wrapper = shallowMount<StdCodesGroupClass>(StdCodesGroupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stdCodesGroupService: () => stdCodesGroupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStdCodesGroup = { id: 123 };
        stdCodesGroupServiceStub.find.resolves(foundStdCodesGroup);

        // WHEN
        comp.retrieveStdCodesGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stdCodesGroup).toBe(foundStdCodesGroup);
      });
    });
  });
});
