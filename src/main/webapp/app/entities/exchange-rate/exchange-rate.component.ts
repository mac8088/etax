import { Component, Inject, Vue } from 'vue-property-decorator';
import { IExchangeRate } from '@/shared/model/exchange-rate.model';
import AlertService from '@/shared/alert/alert.service';

import ExchangeRateService from './exchange-rate.service';

@Component
export default class ExchangeRate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('exchangeRateService') private exchangeRateService: () => ExchangeRateService;
  private removeId: number = null;
  public exchangeRates: IExchangeRate[] = [];

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
    this.retrieveAllExchangeRates();
  }

  public clear(): void {
    this.retrieveAllExchangeRates();
  }

  public retrieveAllExchangeRates(): void {
    this.isFetching = true;

    this.exchangeRateService()
      .retrieve()
      .then(
        res => {
          this.exchangeRates = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IExchangeRate): void {
    this.removeId = instance.id;
  }

  public removeExchangeRate(): void {
    this.exchangeRateService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.exchangeRate.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllExchangeRates();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
