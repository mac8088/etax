/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeAreaCodeComponent from '@/entities/office-area-code/office-area-code.vue';
import OfficeAreaCodeClass from '@/entities/office-area-code/office-area-code.component';
import OfficeAreaCodeService from '@/entities/office-area-code/office-area-code.service';

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
  describe('OfficeAreaCode Management Component', () => {
    let wrapper: Wrapper<OfficeAreaCodeClass>;
    let comp: OfficeAreaCodeClass;
    let officeAreaCodeServiceStub: SinonStubbedInstance<OfficeAreaCodeService>;

    beforeEach(() => {
      officeAreaCodeServiceStub = sinon.createStubInstance<OfficeAreaCodeService>(OfficeAreaCodeService);
      officeAreaCodeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeAreaCodeClass>(OfficeAreaCodeComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeAreaCodeService: () => officeAreaCodeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeAreaCodeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOfficeAreaCodes();
      await comp.$nextTick();

      // THEN
      expect(officeAreaCodeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeAreaCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      officeAreaCodeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(officeAreaCodeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeAreaCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      officeAreaCodeServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(officeAreaCodeServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      officeAreaCodeServiceStub.retrieve.reset();
      officeAreaCodeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(officeAreaCodeServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.officeAreaCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      officeAreaCodeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOfficeAreaCode();
      await comp.$nextTick();

      // THEN
      expect(officeAreaCodeServiceStub.delete.called).toBeTruthy();
      expect(officeAreaCodeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
