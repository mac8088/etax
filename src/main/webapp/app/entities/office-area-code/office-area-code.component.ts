import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOfficeAreaCode } from '@/shared/model/office-area-code.model';
import AlertService from '@/shared/alert/alert.service';

import OfficeAreaCodeService from './office-area-code.service';

@Component
export default class OfficeAreaCode extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeAreaCodeService') private officeAreaCodeService: () => OfficeAreaCodeService;
  private removeId: number = null;
  public officeAreaCodes: IOfficeAreaCode[] = [];

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
    this.retrieveAllOfficeAreaCodes();
  }

  public clear(): void {
    this.retrieveAllOfficeAreaCodes();
  }

  public retrieveAllOfficeAreaCodes(): void {
    this.isFetching = true;

    this.officeAreaCodeService()
      .retrieve()
      .then(
        res => {
          this.officeAreaCodes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOfficeAreaCode): void {
    this.removeId = instance.id;
  }

  public removeOfficeAreaCode(): void {
    this.officeAreaCodeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.officeAreaCode.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficeAreaCodes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
