/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesComponent from '@/entities/std-codes/std-codes.vue';
import StdCodesClass from '@/entities/std-codes/std-codes.component';
import StdCodesService from '@/entities/std-codes/std-codes.service';

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
  describe('StdCodes Management Component', () => {
    let wrapper: Wrapper<StdCodesClass>;
    let comp: StdCodesClass;
    let stdCodesServiceStub: SinonStubbedInstance<StdCodesService>;

    beforeEach(() => {
      stdCodesServiceStub = sinon.createStubInstance<StdCodesService>(StdCodesService);
      stdCodesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StdCodesClass>(StdCodesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          stdCodesService: () => stdCodesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stdCodesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStdCodess();
      await comp.$nextTick();

      // THEN
      expect(stdCodesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stdCodesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStdCodes();
      await comp.$nextTick();

      // THEN
      expect(stdCodesServiceStub.delete.called).toBeTruthy();
      expect(stdCodesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
