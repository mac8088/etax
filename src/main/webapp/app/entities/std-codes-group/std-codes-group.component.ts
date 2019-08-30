import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodesGroup } from '@/shared/model/std-codes-group.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesGroupService from './std-codes-group.service';

@Component
export default class StdCodesGroup extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesGroupService') private stdCodesGroupService: () => StdCodesGroupService;
  private removeId: number = null;
  public stdCodesGroups: IStdCodesGroup[] = [];

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
    this.retrieveAllStdCodesGroups();
  }

  public clear(): void {
    this.retrieveAllStdCodesGroups();
  }

  public retrieveAllStdCodesGroups(): void {
    this.isFetching = true;

    this.stdCodesGroupService()
      .retrieve()
      .then(
        res => {
          this.stdCodesGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStdCodesGroup): void {
    this.removeId = instance.id;
  }

  public removeStdCodesGroup(): void {
    this.stdCodesGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.stdCodesGroup.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllStdCodesGroups();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
