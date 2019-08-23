/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BankAccountUpdateComponent from '@/entities/bank-account/bank-account-update.vue';
import BankAccountClass from '@/entities/bank-account/bank-account-update.component';
import BankAccountService from '@/entities/bank-account/bank-account.service';

import OperationService from '@/entities/operation/operation.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('BankAccount Management Update Component', () => {
    let wrapper: Wrapper<BankAccountClass>;
    let comp: BankAccountClass;
    let bankAccountServiceStub: SinonStubbedInstance<BankAccountService>;

    beforeEach(() => {
      bankAccountServiceStub = sinon.createStubInstance<BankAccountService>(BankAccountService);

      wrapper = shallowMount<BankAccountClass>(BankAccountUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          bankAccountService: () => bankAccountServiceStub,

          operationService: () => new OperationService(),

          userService: () => new UserService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.bankAccount = entity;
        bankAccountServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bankAccountServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.bankAccount = entity;
        bankAccountServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bankAccountServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
