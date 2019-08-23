import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOperation } from '@/shared/model/operation.model';
import AlertService from '@/shared/alert/alert.service';

import OperationService from './operation.service';

@Component
export default class Operation extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('operationService') private operationService: () => OperationService;
  private removeId: number = null;
  public operations: IOperation[] = [];

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
    this.retrieveAllOperations();
  }

  public clear(): void {
    this.retrieveAllOperations();
  }

  public retrieveAllOperations(): void {
    this.isFetching = true;

    this.operationService()
      .retrieve()
      .then(
        res => {
          this.operations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOperation): void {
    this.removeId = instance.id;
  }

  public removeOperation(): void {
    this.operationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.operation.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOperations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
