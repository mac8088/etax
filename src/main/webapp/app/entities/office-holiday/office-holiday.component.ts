import { Component, Inject, Vue } from 'vue-property-decorator';
import { IOfficeHoliday } from '@/shared/model/office-holiday.model';
import AlertService from '@/shared/alert/alert.service';

import OfficeHolidayService from './office-holiday.service';

@Component
export default class OfficeHoliday extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeHolidayService') private officeHolidayService: () => OfficeHolidayService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage: number = null;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public officeHolidays: IOfficeHoliday[] = [];

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
    this.retrieveAllOfficeHolidays();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllOfficeHolidays();
  }

  public retrieveAllOfficeHolidays(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.officeHolidayService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.officeHolidays = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOfficeHoliday): void {
    this.removeId = instance.id;
  }

  public removeOfficeHoliday(): void {
    this.officeHolidayService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.officeHoliday.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficeHolidays();
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
    this.retrieveAllOfficeHolidays();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
