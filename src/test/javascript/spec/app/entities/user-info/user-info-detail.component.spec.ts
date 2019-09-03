/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UserInfoDetailComponent from '@/entities/user-info/user-info-details.vue';
import UserInfoClass from '@/entities/user-info/user-info-details.component';
import UserInfoService from '@/entities/user-info/user-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserInfo Management Detail Component', () => {
    let wrapper: Wrapper<UserInfoClass>;
    let comp: UserInfoClass;
    let userInfoServiceStub: SinonStubbedInstance<UserInfoService>;

    beforeEach(() => {
      userInfoServiceStub = sinon.createStubInstance<UserInfoService>(UserInfoService);

      wrapper = shallowMount<UserInfoClass>(UserInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { userInfoService: () => userInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserInfo = { id: 123 };
        userInfoServiceStub.find.resolves(foundUserInfo);

        // WHEN
        comp.retrieveUserInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userInfo).toBe(foundUserInfo);
      });
    });
  });
});
