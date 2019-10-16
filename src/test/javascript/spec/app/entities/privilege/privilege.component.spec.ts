/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PrivilegeComponent from '@/entities/privilege/privilege.vue';
import PrivilegeClass from '@/entities/privilege/privilege.component';
import PrivilegeService from '@/entities/privilege/privilege.service';

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
  describe('Privilege Management Component', () => {
    let wrapper: Wrapper<PrivilegeClass>;
    let comp: PrivilegeClass;
    let privilegeServiceStub: SinonStubbedInstance<PrivilegeService>;

    beforeEach(() => {
      privilegeServiceStub = sinon.createStubInstance<PrivilegeService>(PrivilegeService);
      privilegeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrivilegeClass>(PrivilegeComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          privilegeService: () => privilegeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      privilegeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPrivileges();
      await comp.$nextTick();

      // THEN
      expect(privilegeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.privileges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      privilegeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(privilegeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.privileges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      privilegeServiceStub.retrieve.reset();
      privilegeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(privilegeServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.privileges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      privilegeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePrivilege();
      await comp.$nextTick();

      // THEN
      expect(privilegeServiceStub.delete.called).toBeTruthy();
      expect(privilegeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
