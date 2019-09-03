/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserInfoUpdateComponent from '@/entities/user-info/user-info-update.vue';
import UserInfoClass from '@/entities/user-info/user-info-update.component';
import UserInfoService from '@/entities/user-info/user-info.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('UserInfo Management Update Component', () => {
    let wrapper: Wrapper<UserInfoClass>;
    let comp: UserInfoClass;
    let userInfoServiceStub: SinonStubbedInstance<UserInfoService>;

    beforeEach(() => {
      userInfoServiceStub = sinon.createStubInstance<UserInfoService>(UserInfoService);

      wrapper = shallowMount<UserInfoClass>(UserInfoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          userInfoService: () => userInfoServiceStub,

          userService: () => new UserService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.userInfo = entity;
        userInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userInfo = entity;
        userInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
