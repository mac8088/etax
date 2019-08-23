/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BankAccountComponent from '@/entities/bank-account/bank-account.vue';
import BankAccountClass from '@/entities/bank-account/bank-account.component';
import BankAccountService from '@/entities/bank-account/bank-account.service';

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
  describe('BankAccount Management Component', () => {
    let wrapper: Wrapper<BankAccountClass>;
    let comp: BankAccountClass;
    let bankAccountServiceStub: SinonStubbedInstance<BankAccountService>;

    beforeEach(() => {
      bankAccountServiceStub = sinon.createStubInstance<BankAccountService>(BankAccountService);
      bankAccountServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BankAccountClass>(BankAccountComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          bankAccountService: () => bankAccountServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      bankAccountServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBankAccounts();
      await comp.$nextTick();

      // THEN
      expect(bankAccountServiceStub.retrieve.called).toBeTruthy();
      expect(comp.bankAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      bankAccountServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBankAccount();
      await comp.$nextTick();

      // THEN
      expect(bankAccountServiceStub.delete.called).toBeTruthy();
      expect(bankAccountServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
