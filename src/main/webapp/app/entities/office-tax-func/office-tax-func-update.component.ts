import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IOfficeTaxFunc, OfficeTaxFunc } from '@/shared/model/office-tax-func.model';
import OfficeTaxFuncService from './office-tax-func.service';

const validations: any = {
  officeTaxFunc: {
    taxOfficeId: {
      required,
      numeric
    },
    funcOfficeId: {
      required,
      numeric
    },
    startDate: {
      required
    },
    endDate: {}
  }
};

@Component({
  validations
})
export default class OfficeTaxFuncUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeTaxFuncService') private officeTaxFuncService: () => OfficeTaxFuncService;
  public officeTaxFunc: IOfficeTaxFunc = new OfficeTaxFunc();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeTaxFuncId) {
        vm.retrieveOfficeTaxFunc(to.params.officeTaxFuncId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.officeTaxFunc.id) {
      this.officeTaxFuncService()
        .update(this.officeTaxFunc)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeTaxFunc.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeTaxFuncService()
        .create(this.officeTaxFunc)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeTaxFunc.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOfficeTaxFunc(officeTaxFuncId): void {
    this.officeTaxFuncService()
      .find(officeTaxFuncId)
      .then(res => {
        this.officeTaxFunc = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
