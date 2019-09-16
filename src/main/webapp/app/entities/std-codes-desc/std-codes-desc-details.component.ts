import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStdCodesDesc } from '@/shared/model/std-codes-desc.model';
import StdCodesDescService from './std-codes-desc.service';

@Component
export default class StdCodesDescDetails extends Vue {
  @Inject('stdCodesDescService') private stdCodesDescService: () => StdCodesDescService;
  public stdCodesDesc: IStdCodesDesc = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesDescId) {
        vm.retrieveStdCodesDesc(to.params.stdCodesDescId);
      }
    });
  }

  public retrieveStdCodesDesc(stdCodesDescId) {
    this.stdCodesDescService()
      .find(stdCodesDescId)
      .then(res => {
        this.stdCodesDesc = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
