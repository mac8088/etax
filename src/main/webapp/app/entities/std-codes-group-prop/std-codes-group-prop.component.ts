import { Component, Inject, Vue } from 'vue-property-decorator';
import { IStdCodesGroupProp } from '@/shared/model/std-codes-group-prop.model';
import AlertService from '@/shared/alert/alert.service';

import StdCodesGroupPropService from './std-codes-group-prop.service';

@Component
export default class StdCodesGroupProp extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesGroupPropService') private stdCodesGroupPropService: () => StdCodesGroupPropService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage: number = null;
  public propOrder = 'id';
  public reverse = true;
  public totalItems = 0;
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
    this.page = 1;
    this.retrieveAllStdCodesGroupProps();
  }

  public retrieveAllStdCodesGroupProps(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.stdCodesGroupPropService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.stdCodesGroupProps = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
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

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllStdCodesGroupProps();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
