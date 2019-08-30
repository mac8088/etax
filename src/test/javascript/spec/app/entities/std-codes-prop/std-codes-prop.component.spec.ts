/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesPropComponent from '@/entities/std-codes-prop/std-codes-prop.vue';
import StdCodesPropClass from '@/entities/std-codes-prop/std-codes-prop.component';
import StdCodesPropService from '@/entities/std-codes-prop/std-codes-prop.service';

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
  describe('StdCodesProp Management Component', () => {
    let wrapper: Wrapper<StdCodesPropClass>;
    let comp: StdCodesPropClass;
    let stdCodesPropServiceStub: SinonStubbedInstance<StdCodesPropService>;

    beforeEach(() => {
      stdCodesPropServiceStub = sinon.createStubInstance<StdCodesPropService>(StdCodesPropService);
      stdCodesPropServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StdCodesPropClass>(StdCodesPropComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          stdCodesPropService: () => stdCodesPropServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stdCodesPropServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStdCodesProps();
      await comp.$nextTick();

      // THEN
      expect(stdCodesPropServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodesProps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stdCodesPropServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStdCodesProp();
      await comp.$nextTick();

      // THEN
      expect(stdCodesPropServiceStub.delete.called).toBeTruthy();
      expect(stdCodesPropServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
