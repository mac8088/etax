/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserInfoComponent from '@/entities/user-info/user-info.vue';
import UserInfoClass from '@/entities/user-info/user-info.component';
import UserInfoService from '@/entities/user-info/user-info.service';

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
  describe('UserInfo Management Component', () => {
    let wrapper: Wrapper<UserInfoClass>;
    let comp: UserInfoClass;
    let userInfoServiceStub: SinonStubbedInstance<UserInfoService>;

    beforeEach(() => {
      userInfoServiceStub = sinon.createStubInstance<UserInfoService>(UserInfoService);
      userInfoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserInfoClass>(UserInfoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          userInfoService: () => userInfoServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserInfos();
      await comp.$nextTick();

      // THEN
      expect(userInfoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userInfoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeUserInfo();
      await comp.$nextTick();

      // THEN
      expect(userInfoServiceStub.delete.called).toBeTruthy();
      expect(userInfoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
