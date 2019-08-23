import { Component, Inject, Vue } from 'vue-property-decorator';
import { IBankAccount } from '@/shared/model/bank-account.model';
import AlertService from '@/shared/alert/alert.service';

import BankAccountService from './bank-account.service';

@Component
export default class BankAccount extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  private removeId: number = null;
  public bankAccounts: IBankAccount[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllBankAccounts();
  }

  public clear(): void {
    this.retrieveAllBankAccounts();
  }

  public retrieveAllBankAccounts(): void {
    this.isFetching = true;

    this.bankAccountService()
      .retrieve()
      .then(
        res => {
          this.bankAccounts = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBankAccount): void {
    this.removeId = instance.id;
  }

  public removeBankAccount(): void {
    this.bankAccountService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.bankAccount.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllBankAccounts();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
