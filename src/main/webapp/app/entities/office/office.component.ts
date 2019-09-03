import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import { IOffice } from '@/shared/model/office.model';
import AlertService from '@/shared/alert/alert.service';

import JhiDataUtils from '@/shared/data/data-utils.service';

import OfficeService from './office.service';

@Component
export default class Office extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeService') private officeService: () => OfficeService;
  private removeId: number = null;
  public offices: IOffice[] = [];

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
    this.retrieveAllOffices();
  }

  public clear(): void {
    this.retrieveAllOffices();
  }

  public retrieveAllOffices(): void {
    this.isFetching = true;

    this.officeService()
      .retrieve()
      .then(
        res => {
          this.offices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOffice): void {
    this.removeId = instance.id;
  }

  public removeOffice(): void {
    this.officeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.office.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOffices();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
