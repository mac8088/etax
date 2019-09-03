import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStdCodesProp } from '@/shared/model/std-codes-prop.model';
import StdCodesPropService from './std-codes-prop.service';

@Component
export default class StdCodesPropDetails extends Vue {
  @Inject('stdCodesPropService') private stdCodesPropService: () => StdCodesPropService;
  public stdCodesProp: IStdCodesProp = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesPropId) {
        vm.retrieveStdCodesProp(to.params.stdCodesPropId);
      }
    });
  }

  public retrieveStdCodesProp(stdCodesPropId) {
    this.stdCodesPropService()
      .find(stdCodesPropId)
      .then(res => {
        this.stdCodesProp = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
