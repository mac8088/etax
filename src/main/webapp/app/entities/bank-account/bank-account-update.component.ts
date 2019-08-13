import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import OperationService from '../operation/operation.service';
import { IOperation } from '@/shared/model/operation.model';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IBankAccount, BankAccount } from '@/shared/model/bank-account.model';
import BankAccountService from './bank-account.service';

const validations: any = {
  bankAccount: {
    name: {
      required
    },
    balance: {
      required,
      numeric
    }
  }
};

@Component({
  validations
})
export default class BankAccountUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  public bankAccount: IBankAccount = new BankAccount();

  @Inject('operationService') private operationService: () => OperationService;

  public operations: IOperation[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bankAccountId) {
        vm.retrieveBankAccount(to.params.bankAccountId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.bankAccount.id) {
      this.bankAccountService()
        .update(this.bankAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.bankAccount.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.bankAccountService()
        .create(this.bankAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.bankAccount.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveBankAccount(bankAccountId): void {
    this.bankAccountService()
      .find(bankAccountId)
      .then(res => {
        this.bankAccount = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.operationService()
      .retrieve()
      .then(res => {
        this.operations = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
