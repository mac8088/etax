/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProfileSetupDetailComponent from '@/entities/profile-setup/profile-setup-details.vue';
import ProfileSetupClass from '@/entities/profile-setup/profile-setup-details.component';
import ProfileSetupService from '@/entities/profile-setup/profile-setup.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProfileSetup Management Detail Component', () => {
    let wrapper: Wrapper<ProfileSetupClass>;
    let comp: ProfileSetupClass;
    let profileSetupServiceStub: SinonStubbedInstance<ProfileSetupService>;

    beforeEach(() => {
      profileSetupServiceStub = sinon.createStubInstance<ProfileSetupService>(ProfileSetupService);

      wrapper = shallowMount<ProfileSetupClass>(ProfileSetupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { profileSetupService: () => profileSetupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProfileSetup = { id: 123 };
        profileSetupServiceStub.find.resolves(foundProfileSetup);

        // WHEN
        comp.retrieveProfileSetup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.profileSetup).toBe(foundProfileSetup);
      });
    });
  });
});
