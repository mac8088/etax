import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOfficeRelationship } from '@/shared/model/office-relationship.model';
import AlertService from '@/shared/alert/alert.service';

import OfficeRelationshipService from './office-relationship.service';

@Component
export default class OfficeRelationship extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeRelationshipService') private officeRelationshipService: () => OfficeRelationshipService;
  private removeId: number = null;
  public officeRelationships: IOfficeRelationship[] = [];

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
    this.retrieveAllOfficeRelationships();
  }

  public clear(): void {
    this.retrieveAllOfficeRelationships();
  }

  public retrieveAllOfficeRelationships(): void {
    this.isFetching = true;

    this.officeRelationshipService()
      .retrieve()
      .then(
        res => {
          this.officeRelationships = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOfficeRelationship): void {
    this.removeId = instance.id;
  }

  public removeOfficeRelationship(): void {
    this.officeRelationshipService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.officeRelationship.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficeRelationships();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
