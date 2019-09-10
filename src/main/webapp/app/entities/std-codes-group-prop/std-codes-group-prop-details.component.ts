import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStdCodesGroupProp } from '@/shared/model/std-codes-group-prop.model';
import StdCodesGroupPropService from './std-codes-group-prop.service';

@Component
export default class StdCodesGroupPropDetails extends Vue {
  @Inject('stdCodesGroupPropService') private stdCodesGroupPropService: () => StdCodesGroupPropService;
  public stdCodesGroupProp: IStdCodesGroupProp = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesGroupPropId) {
        vm.retrieveStdCodesGroupProp(to.params.stdCodesGroupPropId);
      }
    });
  }

  public retrieveStdCodesGroupProp(stdCodesGroupPropId) {
    this.stdCodesGroupPropService()
      .find(stdCodesGroupPropId)
      .then(res => {
        this.stdCodesGroupProp = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
