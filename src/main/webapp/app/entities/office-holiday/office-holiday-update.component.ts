import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IOfficeHoliday, OfficeHoliday } from '@/shared/model/office-holiday.model';
import OfficeHolidayService from './office-holiday.service';

const validations: any = {
  officeHoliday: {
    officeId: {
      required,
      numeric
    },
    holidayId: {
      required,
      maxLength: maxLength(7)
    }
  }
};

@Component({
  validations
})
export default class OfficeHolidayUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeHolidayService') private officeHolidayService: () => OfficeHolidayService;
  public officeHoliday: IOfficeHoliday = new OfficeHoliday();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeHolidayId) {
        vm.retrieveOfficeHoliday(to.params.officeHolidayId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.officeHoliday.id) {
      this.officeHolidayService()
        .update(this.officeHoliday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeHoliday.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeHolidayService()
        .create(this.officeHoliday)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeHoliday.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOfficeHoliday(officeHolidayId): void {
    this.officeHolidayService()
      .find(officeHolidayId)
      .then(res => {
        this.officeHoliday = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
