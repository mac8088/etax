import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodes } from '@/shared/model/std-codes.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesService from './std-codes.service';

@Component
export default class StdCodes extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesService') private stdCodesService: () => StdCodesService;
  private removeId: number = null;
  public stdCodes: IStdCodes[] = [];

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
    this.retrieveAllStdCodess();
  }

  public clear(): void {
    this.retrieveAllStdCodess();
  }

  public retrieveAllStdCodess(): void {
    this.isFetching = true;

    this.stdCodesService()
      .retrieve()
      .then(
        res => {
          this.stdCodes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStdCodes): void {
    this.removeId = instance.id;
  }

  public removeStdCodes(): void {
    this.stdCodesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.stdCodes.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllStdCodess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
