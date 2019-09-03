/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesGroupComponent from '@/entities/std-codes-group/std-codes-group.vue';
import StdCodesGroupClass from '@/entities/std-codes-group/std-codes-group.component';
import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';

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
  describe('StdCodesGroup Management Component', () => {
    let wrapper: Wrapper<StdCodesGroupClass>;
    let comp: StdCodesGroupClass;
    let stdCodesGroupServiceStub: SinonStubbedInstance<StdCodesGroupService>;

    beforeEach(() => {
      stdCodesGroupServiceStub = sinon.createStubInstance<StdCodesGroupService>(StdCodesGroupService);
      stdCodesGroupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StdCodesGroupClass>(StdCodesGroupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          stdCodesGroupService: () => stdCodesGroupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stdCodesGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStdCodesGroups();
      await comp.$nextTick();

      // THEN
      expect(stdCodesGroupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodesGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stdCodesGroupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStdCodesGroup();
      await comp.$nextTick();

      // THEN
      expect(stdCodesGroupServiceStub.delete.called).toBeTruthy();
      expect(stdCodesGroupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
