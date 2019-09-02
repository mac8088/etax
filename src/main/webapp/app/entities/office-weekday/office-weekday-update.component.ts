import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IOfficeWeekday, OfficeWeekday } from '@/shared/model/office-weekday.model';
import OfficeWeekdayService from './office-weekday.service';

const validations: any = {
  officeWeekday: {
    officeId: {
      required,
      numeric
    },
    cstdWeekworkingDay: {
      required,
      maxLength: maxLength(40)
    },
    startDate: {},
    endDate: {}
  }
};

@Component({
  validations
})
export default class OfficeWeekdayUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeWeekdayService') private officeWeekdayService: () => OfficeWeekdayService;
  public officeWeekday: IOfficeWeekday = new OfficeWeekday();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeWeekdayId) {
        vm.retrieveOfficeWeekday(to.params.officeWeekdayId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.officeWeekday.id) {
      this.officeWeekdayService()
        .update(this.officeWeekday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeWeekday.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeWeekdayService()
        .create(this.officeWeekday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeWeekday.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOfficeWeekday(officeWeekdayId): void {
    this.officeWeekdayService()
      .find(officeWeekdayId)
      .then(res => {
        this.officeWeekday = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
