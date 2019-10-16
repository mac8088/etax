import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IPrivilege, Privilege } from '@/shared/model/privilege.model';
import PrivilegeService from './privilege.service';

const validations: any = {
  privilege: {
    appCode: {
      required,
      maxLength: maxLength(50)
    },
    userName: {
      required,
      maxLength: maxLength(50)
    },
    profileName: {
      required,
      maxLength: maxLength(50)
    },
    limit: {},
    confirmStatus: {
      maxLength: maxLength(20)
    },
    effectiveDate: {
      required
    },
    expiryDate: {}
  }
};

@Component({
  validations
})
export default class PrivilegeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('privilegeService') private privilegeService: () => PrivilegeService;
  public privilege: IPrivilege = new Privilege();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privilegeId) {
        vm.retrievePrivilege(to.params.privilegeId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.privilege.id) {
      this.privilegeService()
        .update(this.privilege)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.privilege.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.privilegeService()
        .create(this.privilege)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.privilege.created', { param: param.id });
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
      this.privilege[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.privilege[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.privilege[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.privilege[field] = null;
    }
  }

  public retrievePrivilege(privilegeId): void {
    this.privilegeService()
      .find(privilegeId)
      .then(res => {
        this.privilege = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
