/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeTaxFuncComponent from '@/entities/office-tax-func/office-tax-func.vue';
import OfficeTaxFuncClass from '@/entities/office-tax-func/office-tax-func.component';
import OfficeTaxFuncService from '@/entities/office-tax-func/office-tax-func.service';

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
  describe('OfficeTaxFunc Management Component', () => {
    let wrapper: Wrapper<OfficeTaxFuncClass>;
    let comp: OfficeTaxFuncClass;
    let officeTaxFuncServiceStub: SinonStubbedInstance<OfficeTaxFuncService>;

    beforeEach(() => {
      officeTaxFuncServiceStub = sinon.createStubInstance<OfficeTaxFuncService>(OfficeTaxFuncService);
      officeTaxFuncServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeTaxFuncClass>(OfficeTaxFuncComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeTaxFuncService: () => officeTaxFuncServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeTaxFuncServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOfficeTaxFuncs();
      await comp.$nextTick();

      // THEN
      expect(officeTaxFuncServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeTaxFuncs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      officeTaxFuncServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(officeTaxFuncServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeTaxFuncs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      officeTaxFuncServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(officeTaxFuncServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      officeTaxFuncServiceStub.retrieve.reset();
      officeTaxFuncServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(officeTaxFuncServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.officeTaxFuncs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      officeTaxFuncServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOfficeTaxFunc();
      await comp.$nextTick();

      // THEN
      expect(officeTaxFuncServiceStub.delete.called).toBeTruthy();
      expect(officeTaxFuncServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
