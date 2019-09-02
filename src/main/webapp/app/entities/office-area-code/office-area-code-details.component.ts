import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOfficeAreaCode } from '@/shared/model/office-area-code.model';
import OfficeAreaCodeService from './office-area-code.service';

@Component
export default class OfficeAreaCodeDetails extends Vue {
  @Inject('officeAreaCodeService') private officeAreaCodeService: () => OfficeAreaCodeService;
  public officeAreaCode: IOfficeAreaCode = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeAreaCodeId) {
        vm.retrieveOfficeAreaCode(to.params.officeAreaCodeId);
      }
    });
  }

  public retrieveOfficeAreaCode(officeAreaCodeId) {
    this.officeAreaCodeService()
      .find(officeAreaCodeId)
      .then(res => {
        this.officeAreaCode = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
