import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IUserInfo } from '@/shared/model/user-info.model';
import UserInfoService from './user-info.service';

@Component
export default class UserInfoDetails extends mixins(JhiDataUtils) {
  @Inject('userInfoService') private userInfoService: () => UserInfoService;
  public userInfo: IUserInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userInfoId) {
        vm.retrieveUserInfo(to.params.userInfoId);
      }
    });
  }

  public retrieveUserInfo(userInfoId) {
    this.userInfoService()
      .find(userInfoId)
      .then(res => {
        this.userInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
