/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProfileSetupComponent from '@/entities/profile-setup/profile-setup.vue';
import ProfileSetupClass from '@/entities/profile-setup/profile-setup.component';
import ProfileSetupService from '@/entities/profile-setup/profile-setup.service';

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
  describe('ProfileSetup Management Component', () => {
    let wrapper: Wrapper<ProfileSetupClass>;
    let comp: ProfileSetupClass;
    let profileSetupServiceStub: SinonStubbedInstance<ProfileSetupService>;

    beforeEach(() => {
      profileSetupServiceStub = sinon.createStubInstance<ProfileSetupService>(ProfileSetupService);
      profileSetupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProfileSetupClass>(ProfileSetupComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          profileSetupService: () => profileSetupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      profileSetupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProfileSetups();
      await comp.$nextTick();

      // THEN
      expect(profileSetupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.profileSetups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      profileSetupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(profileSetupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.profileSetups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      profileSetupServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(profileSetupServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      profileSetupServiceStub.retrieve.reset();
      profileSetupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(profileSetupServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.profileSetups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      profileSetupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProfileSetup();
      await comp.$nextTick();

      // THEN
      expect(profileSetupServiceStub.delete.called).toBeTruthy();
      expect(profileSetupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
