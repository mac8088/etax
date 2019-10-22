/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProfileSetupUpdateComponent from '@/entities/profile-setup/profile-setup-update.vue';
import ProfileSetupClass from '@/entities/profile-setup/profile-setup-update.component';
import ProfileSetupService from '@/entities/profile-setup/profile-setup.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProfileSetup Management Update Component', () => {
    let wrapper: Wrapper<ProfileSetupClass>;
    let comp: ProfileSetupClass;
    let profileSetupServiceStub: SinonStubbedInstance<ProfileSetupService>;

    beforeEach(() => {
      profileSetupServiceStub = sinon.createStubInstance<ProfileSetupService>(ProfileSetupService);

      wrapper = shallowMount<ProfileSetupClass>(ProfileSetupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          profileSetupService: () => profileSetupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.profileSetup = entity;
        profileSetupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(profileSetupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.profileSetup = entity;
        profileSetupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(profileSetupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
