import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodesGroupProp } from '@/shared/model/std-codes-group-prop.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesGroupPropService from './std-codes-group-prop.service';

@Component
export default class StdCodesGroupProp extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesGroupPropService') private stdCodesGroupPropService: () => StdCodesGroupPropService;
  private removeId: number = null;
  public stdCodesGroupProps: IStdCodesGroupProp[] = [];

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
    this.retrieveAllStdCodesGroupProps();
  }

  public clear(): void {
    this.retrieveAllStdCodesGroupProps();
  }

  public retrieveAllStdCodesGroupProps(): void {
    this.isFetching = true;

    this.stdCodesGroupPropService()
      .retrieve()
      .then(
        res => {
          this.stdCodesGroupProps = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStdCodesGroupProp): void {
    this.removeId = instance.id;
  }

  public removeStdCodesGroupProp(): void {
    this.stdCodesGroupPropService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.stdCodesGroupProp.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllStdCodesGroupProps();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
