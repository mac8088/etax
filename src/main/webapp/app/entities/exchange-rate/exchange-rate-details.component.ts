import { Component, Vue, Inject } from 'vue-property-decorator';

import { IExchangeRate } from '@/shared/model/exchange-rate.model';
import ExchangeRateService from './exchange-rate.service';

@Component
export default class ExchangeRateDetails extends Vue {
  @Inject('exchangeRateService') private exchangeRateService: () => ExchangeRateService;
  public exchangeRate: IExchangeRate = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exchangeRateId) {
        vm.retrieveExchangeRate(to.params.exchangeRateId);
      }
    });
  }

  public retrieveExchangeRate(exchangeRateId) {
    this.exchangeRateService()
      .find(exchangeRateId)
      .then(res => {
        this.exchangeRate = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
