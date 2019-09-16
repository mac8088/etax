import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IOfficeAreaCode, OfficeAreaCode } from '@/shared/model/office-area-code.model';
import OfficeAreaCodeService from './office-area-code.service';

const validations: any = {
  officeAreaCode: {
    officeId: {
      required,
      numeric
    },
    fromPin: {
      required,
      maxLength: maxLength(10)
    },
    toPin: {
      maxLength: maxLength(10)
    }
  }
};

@Component({
  validations
})
export default class OfficeAreaCodeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeAreaCodeService') private officeAreaCodeService: () => OfficeAreaCodeService;
  public officeAreaCode: IOfficeAreaCode = new OfficeAreaCode();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeAreaCodeId) {
        vm.retrieveOfficeAreaCode(to.params.officeAreaCodeId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.officeAreaCode.id) {
      this.officeAreaCodeService()
        .update(this.officeAreaCode)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeAreaCode.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeAreaCodeService()
        .create(this.officeAreaCode)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeAreaCode.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOfficeAreaCode(officeAreaCodeId): void {
    this.officeAreaCodeService()
      .find(officeAreaCodeId)
      .then(res => {
        this.officeAreaCode = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
