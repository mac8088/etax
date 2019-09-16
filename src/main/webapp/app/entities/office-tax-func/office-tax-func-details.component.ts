import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOfficeTaxFunc } from '@/shared/model/office-tax-func.model';
import OfficeTaxFuncService from './office-tax-func.service';

@Component
export default class OfficeTaxFuncDetails extends Vue {
  @Inject('officeTaxFuncService') private officeTaxFuncService: () => OfficeTaxFuncService;
  public officeTaxFunc: IOfficeTaxFunc = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeTaxFuncId) {
        vm.retrieveOfficeTaxFunc(to.params.officeTaxFuncId);
      }
    });
  }

  public retrieveOfficeTaxFunc(officeTaxFuncId) {
    this.officeTaxFuncService()
      .find(officeTaxFuncId)
      .then(res => {
        this.officeTaxFunc = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
