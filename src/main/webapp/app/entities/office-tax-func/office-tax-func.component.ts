import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOfficeTaxFunc } from '@/shared/model/office-tax-func.model';
import AlertService from '@/shared/alert/alert.service';

import OfficeTaxFuncService from './office-tax-func.service';

@Component
export default class OfficeTaxFunc extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeTaxFuncService') private officeTaxFuncService: () => OfficeTaxFuncService;
  private removeId: number = null;
  public officeTaxFuncs: IOfficeTaxFunc[] = [];

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
    this.retrieveAllOfficeTaxFuncs();
  }

  public clear(): void {
    this.retrieveAllOfficeTaxFuncs();
  }

  public retrieveAllOfficeTaxFuncs(): void {
    this.isFetching = true;

    this.officeTaxFuncService()
      .retrieve()
      .then(
        res => {
          this.officeTaxFuncs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOfficeTaxFunc): void {
    this.removeId = instance.id;
  }

  public removeOfficeTaxFunc(): void {
    this.officeTaxFuncService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.officeTaxFunc.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficeTaxFuncs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
