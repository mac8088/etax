import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBankAccount } from '@/shared/model/bank-account.model';
import BankAccountService from './bank-account.service';

@Component
export default class BankAccountDetails extends Vue {
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  public bankAccount: IBankAccount = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bankAccountId) {
        vm.retrieveBankAccount(to.params.bankAccountId);
      }
    });
  }

  public retrieveBankAccount(bankAccountId) {
    this.bankAccountService()
      .find(bankAccountId)
      .then(res => {
        this.bankAccount = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
