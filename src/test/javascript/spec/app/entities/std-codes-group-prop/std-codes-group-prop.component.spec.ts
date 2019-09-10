/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesGroupPropComponent from '@/entities/std-codes-group-prop/std-codes-group-prop.vue';
import StdCodesGroupPropClass from '@/entities/std-codes-group-prop/std-codes-group-prop.component';
import StdCodesGroupPropService from '@/entities/std-codes-group-prop/std-codes-group-prop.service';

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
  describe('StdCodesGroupProp Management Component', () => {
    let wrapper: Wrapper<StdCodesGroupPropClass>;
    let comp: StdCodesGroupPropClass;
    let stdCodesGroupPropServiceStub: SinonStubbedInstance<StdCodesGroupPropService>;

    beforeEach(() => {
      stdCodesGroupPropServiceStub = sinon.createStubInstance<StdCodesGroupPropService>(StdCodesGroupPropService);
      stdCodesGroupPropServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StdCodesGroupPropClass>(StdCodesGroupPropComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          stdCodesGroupPropService: () => stdCodesGroupPropServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stdCodesGroupPropServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStdCodesGroupProps();
      await comp.$nextTick();

      // THEN
      expect(stdCodesGroupPropServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodesGroupProps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stdCodesGroupPropServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStdCodesGroupProp();
      await comp.$nextTick();

      // THEN
      expect(stdCodesGroupPropServiceStub.delete.called).toBeTruthy();
      expect(stdCodesGroupPropServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
