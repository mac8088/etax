import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IPublicHoliday, PublicHoliday } from '@/shared/model/public-holiday.model';
import PublicHolidayService from './public-holiday.service';

const validations: any = {
  publicHoliday: {
    cstdHolidayTypes: {
      maxLength: maxLength(40)
    },
    description: {
      maxLength: maxLength(70)
    },
    date: {
      required
    },
    workingFlag: {},
    countryWide: {},
    ccVersion: {}
  }
};

@Component({
  validations
})
export default class PublicHolidayUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('publicHolidayService') private publicHolidayService: () => PublicHolidayService;
  public publicHoliday: IPublicHoliday = new PublicHoliday();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicHolidayId) {
        vm.retrievePublicHoliday(to.params.publicHolidayId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.publicHoliday.id) {
      this.publicHolidayService()
        .update(this.publicHoliday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.publicHoliday.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.publicHolidayService()
        .create(this.publicHoliday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.publicHoliday.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePublicHoliday(publicHolidayId): void {
    this.publicHolidayService()
      .find(publicHolidayId)
      .then(res => {
        this.publicHoliday = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
