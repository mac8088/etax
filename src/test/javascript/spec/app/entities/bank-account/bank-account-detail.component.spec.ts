/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BankAccountDetailComponent from '@/entities/bank-account/bank-account-details.vue';
import BankAccountClass from '@/entities/bank-account/bank-account-details.component';
import BankAccountService from '@/entities/bank-account/bank-account.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BankAccount Management Detail Component', () => {
    let wrapper: Wrapper<BankAccountClass>;
    let comp: BankAccountClass;
    let bankAccountServiceStub: SinonStubbedInstance<BankAccountService>;

    beforeEach(() => {
      bankAccountServiceStub = sinon.createStubInstance<BankAccountService>(BankAccountService);

      wrapper = shallowMount<BankAccountClass>(BankAccountDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { bankAccountService: () => bankAccountServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBankAccount = { id: 123 };
        bankAccountServiceStub.find.resolves(foundBankAccount);

        // WHEN
        comp.retrieveBankAccount(123);
        await comp.$nextTick();

        // THEN
        expect(comp.bankAccount).toBe(foundBankAccount);
      });
    });
  });
});
