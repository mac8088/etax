import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import { IUserInfo } from '@/shared/model/user-info.model';
import AlertService from '@/shared/alert/alert.service';

import JhiDataUtils from '@/shared/data/data-utils.service';

import UserInfoService from './user-info.service';

@Component
export default class UserInfo extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userInfoService') private userInfoService: () => UserInfoService;
  private removeId: number = null;
  public userInfos: IUserInfo[] = [];

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
    this.retrieveAllUserInfos();
  }

  public clear(): void {
    this.retrieveAllUserInfos();
  }

  public retrieveAllUserInfos(): void {
    this.isFetching = true;

    this.userInfoService()
      .retrieve()
      .then(
        res => {
          this.userInfos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IUserInfo): void {
    this.removeId = instance.id;
  }

  public removeUserInfo(): void {
    this.userInfoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('etaxApp.userInfo.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllUserInfos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
