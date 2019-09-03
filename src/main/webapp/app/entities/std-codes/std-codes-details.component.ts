import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStdCodes } from '@/shared/model/std-codes.model';
import StdCodesService from './std-codes.service';

@Component
export default class StdCodesDetails extends Vue {
  @Inject('stdCodesService') private stdCodesService: () => StdCodesService;
  public stdCodes: IStdCodes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesId) {
        vm.retrieveStdCodes(to.params.stdCodesId);
      }
    });
  }

  public retrieveStdCodes(stdCodesId) {
    this.stdCodesService()
      .find(stdCodesId)
      .then(res => {
        this.stdCodes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
