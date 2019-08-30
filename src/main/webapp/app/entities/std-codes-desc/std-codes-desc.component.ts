import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodesDesc } from '@/shared/model/std-codes-desc.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesDescService from './std-codes-desc.service';

@Component
export default class StdCodesDesc extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesDescService') private stdCodesDescService: () => StdCodesDescService;
  private removeId: number = null;
  public stdCodesDescs: IStdCodesDesc[] = [];

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
    this.retrieveAllStdCodesDescs();
  }

  public clear(): void {
    this.retrieveAllStdCodesDescs();
  }

  public retrieveAllStdCodesDescs(): void {
    this.isFetching = true;

    this.stdCodesDescService()
      .retrieve()
      .then(
        res => {
          this.stdCodesDescs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStdCodesDesc): void {
    this.removeId = instance.id;
  }

  public removeStdCodesDesc(): void {
    this.stdCodesDescService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.stdCodesDesc.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllStdCodesDescs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
