import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodesProp } from '@/shared/model/std-codes-prop.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesPropService from './std-codes-prop.service';

@Component
export default class StdCodesProp extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesPropService') private stdCodesPropService: () => StdCodesPropService;
  private removeId: number = null;
  public stdCodesProps: IStdCodesProp[] = [];

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
    this.retrieveAllStdCodesProps();
  }

  public clear(): void {
    this.retrieveAllStdCodesProps();
  }

  public retrieveAllStdCodesProps(): void {
    this.isFetching = true;

    this.stdCodesPropService()
      .retrieve()
      .then(
        res => {
          this.stdCodesProps = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStdCodesProp): void {
    this.removeId = instance.id;
  }

  public removeStdCodesProp(): void {
    this.stdCodesPropService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.stdCodesProp.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllStdCodesProps();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
