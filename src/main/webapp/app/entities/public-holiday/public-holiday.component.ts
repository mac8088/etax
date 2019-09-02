import { Component, Inject, Vue } from 'vue-property-decorator';
import { IPublicHoliday } from '@/shared/model/public-holiday.model';
import AlertService from '@/shared/alert/alert.service';

import PublicHolidayService from './public-holiday.service';

@Component
export default class PublicHoliday extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('publicHolidayService') private publicHolidayService: () => PublicHolidayService;
  private removeId: number = null;
  public publicHolidays: IPublicHoliday[] = [];

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
    this.retrieveAllPublicHolidays();
  }

  public clear(): void {
    this.retrieveAllPublicHolidays();
  }

  public retrieveAllPublicHolidays(): void {
    this.isFetching = true;

    this.publicHolidayService()
      .retrieve()
      .then(
        res => {
          this.publicHolidays = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPublicHoliday): void {
    this.removeId = instance.id;
  }

  public removePublicHoliday(): void {
    this.publicHolidayService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.publicHoliday.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllPublicHolidays();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
