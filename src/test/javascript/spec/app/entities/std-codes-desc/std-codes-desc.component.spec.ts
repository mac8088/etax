/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesDescComponent from '@/entities/std-codes-desc/std-codes-desc.vue';
import StdCodesDescClass from '@/entities/std-codes-desc/std-codes-desc.component';
import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';

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
  describe('StdCodesDesc Management Component', () => {
    let wrapper: Wrapper<StdCodesDescClass>;
    let comp: StdCodesDescClass;
    let stdCodesDescServiceStub: SinonStubbedInstance<StdCodesDescService>;

    beforeEach(() => {
      stdCodesDescServiceStub = sinon.createStubInstance<StdCodesDescService>(StdCodesDescService);
      stdCodesDescServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StdCodesDescClass>(StdCodesDescComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          stdCodesDescService: () => stdCodesDescServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stdCodesDescServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStdCodesDescs();
      await comp.$nextTick();

      // THEN
      expect(stdCodesDescServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodesDescs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      stdCodesDescServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(stdCodesDescServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stdCodesDescs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      stdCodesDescServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(stdCodesDescServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      stdCodesDescServiceStub.retrieve.reset();
      stdCodesDescServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(stdCodesDescServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.stdCodesDescs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stdCodesDescServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStdCodesDesc();
      await comp.$nextTick();

      // THEN
      expect(stdCodesDescServiceStub.delete.called).toBeTruthy();
      expect(stdCodesDescServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
