/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeRelationshipComponent from '@/entities/office-relationship/office-relationship.vue';
import OfficeRelationshipClass from '@/entities/office-relationship/office-relationship.component';
import OfficeRelationshipService from '@/entities/office-relationship/office-relationship.service';

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
  describe('OfficeRelationship Management Component', () => {
    let wrapper: Wrapper<OfficeRelationshipClass>;
    let comp: OfficeRelationshipClass;
    let officeRelationshipServiceStub: SinonStubbedInstance<OfficeRelationshipService>;

    beforeEach(() => {
      officeRelationshipServiceStub = sinon.createStubInstance<OfficeRelationshipService>(OfficeRelationshipService);
      officeRelationshipServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeRelationshipClass>(OfficeRelationshipComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeRelationshipService: () => officeRelationshipServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOfficeRelationships();
      await comp.$nextTick();

      // THEN
      expect(officeRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      officeRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(officeRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      officeRelationshipServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(officeRelationshipServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      officeRelationshipServiceStub.retrieve.reset();
      officeRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(officeRelationshipServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.officeRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      officeRelationshipServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOfficeRelationship();
      await comp.$nextTick();

      // THEN
      expect(officeRelationshipServiceStub.delete.called).toBeTruthy();
      expect(officeRelationshipServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
