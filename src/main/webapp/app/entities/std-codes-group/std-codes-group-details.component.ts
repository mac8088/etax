import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStdCodesGroup } from '@/shared/model/std-codes-group.model';
import StdCodesGroupService from './std-codes-group.service';

@Component
export default class StdCodesGroupDetails extends Vue {
  @Inject('stdCodesGroupService') private stdCodesGroupService: () => StdCodesGroupService;
  public stdCodesGroup: IStdCodesGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesGroupId) {
        vm.retrieveStdCodesGroup(to.params.stdCodesGroupId);
      }
    });
  }

  public retrieveStdCodesGroup(stdCodesGroupId) {
    this.stdCodesGroupService()
      .find(stdCodesGroupId)
      .then(res => {
        this.stdCodesGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
