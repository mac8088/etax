import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOfficeWeekday } from '@/shared/model/office-weekday.model';
import AlertService from '@/shared/alert/alert.service';

import OfficeWeekdayService from './office-weekday.service';

@Component
export default class OfficeWeekday extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeWeekdayService') private officeWeekdayService: () => OfficeWeekdayService;
  private removeId: number = null;
  public officeWeekdays: IOfficeWeekday[] = [];

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
    this.retrieveAllOfficeWeekdays();
  }

  public clear(): void {
    this.retrieveAllOfficeWeekdays();
  }

  public retrieveAllOfficeWeekdays(): void {
    this.isFetching = true;

    this.officeWeekdayService()
      .retrieve()
      .then(
        res => {
          this.officeWeekdays = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOfficeWeekday): void {
    this.removeId = instance.id;
  }

  public removeOfficeWeekday(): void {
    this.officeWeekdayService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.officeWeekday.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficeWeekdays();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
