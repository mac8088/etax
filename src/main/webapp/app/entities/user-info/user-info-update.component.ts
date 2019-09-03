import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IUserInfo, UserInfo } from '@/shared/model/user-info.model';
import UserInfoService from './user-info.service';

const validations: any = {
  userInfo: {
    cstdAdmSection: {
      required,
      maxLength: maxLength(40)
    },
    cstdSecurityLevel: {
      required,
      maxLength: maxLength(40)
    },
    cstdUserType: {
      required,
      maxLength: maxLength(40)
    },
    description: {
      maxLength: maxLength(200)
    },
    middleName: {
      maxLength: maxLength(50)
    },
    gender: {
      required,
      maxLength: maxLength(40)
    },
    phoneNum: {
      maxLength: maxLength(50)
    },
    faxNum: {
      maxLength: maxLength(50)
    },
    effiectiveDate: {},
    expiryDate: {},
    blocked: {
      required
    },
    blockedReason: {
      required,
      maxLength: maxLength(10)
    },
    forcedPwdChange: {
      required
    },
    cstdTitles: {
      required,
      maxLength: maxLength(40)
    },
    cstdStatus: {
      required,
      maxLength: maxLength(40)
    },
    cstdAdmDivsison: {
      required,
      maxLength: maxLength(40)
    },
    loginStatus: {
      maxLength: maxLength(20)
    },
    loginTime: {},
    attempt: {
      required,
      numeric
    },
    needApprove: {
      required
    },
    logoutTime: {},
    nationalId: {
      required,
      maxLength: maxLength(10)
    },
    cstdOrganizationGrade: {
      maxLength: maxLength(40)
    },
    cstdEmploymentType: {
      maxLength: maxLength(40)
    },
    manuScript: {},
    ccVersion: {}
  }
};

@Component({
  validations
})
export default class UserInfoUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userInfoService') private userInfoService: () => UserInfoService;
  public userInfo: IUserInfo = new UserInfo();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userInfoId) {
        vm.retrieveUserInfo(to.params.userInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.userInfo.id) {
      this.userInfoService()
        .update(this.userInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.userInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userInfoService()
        .create(this.userInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.userInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.userInfo[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.userInfo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.userInfo[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.userInfo[field] = null;
    }
  }

  public retrieveUserInfo(userInfoId): void {
    this.userInfoService()
      .find(userInfoId)
      .then(res => {
        this.userInfo = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.userInfo && field && fieldContentType) {
      if (this.userInfo.hasOwnProperty(field)) {
        this.userInfo[field] = null;
      }
      if (this.userInfo.hasOwnProperty(fieldContentType)) {
        this.userInfo[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
