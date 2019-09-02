import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IExchangeRate, ExchangeRate } from '@/shared/model/exchange-rate.model';
import ExchangeRateService from './exchange-rate.service';

const validations: any = {
  exchangeRate: {
    cstdCurrencyA: {
      required,
      maxLength: maxLength(40)
    },
    cstdCurrencyB: {
      required,
      maxLength: maxLength(40)
    },
    rate: {
      required,
      numeric
    },
    startDate: {
      required
    },
    endDate: {},
    ccVersion: {}
  }
};

@Component({
  validations
})
export default class ExchangeRateUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('exchangeRateService') private exchangeRateService: () => ExchangeRateService;
  public exchangeRate: IExchangeRate = new ExchangeRate();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exchangeRateId) {
        vm.retrieveExchangeRate(to.params.exchangeRateId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.exchangeRate.id) {
      this.exchangeRateService()
        .update(this.exchangeRate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.exchangeRate.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.exchangeRateService()
        .create(this.exchangeRate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.exchangeRate.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveExchangeRate(exchangeRateId): void {
    this.exchangeRateService()
      .find(exchangeRateId)
      .then(res => {
        this.exchangeRate = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
