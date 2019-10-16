import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUiapp } from '@/shared/model/uiapp.model';
import UiappService from './uiapp.service';

@Component
export default class UiappDetails extends Vue {
  @Inject('uiappService') private uiappService: () => UiappService;
  public uiapp: IUiapp = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.uiappId) {
        vm.retrieveUiapp(to.params.uiappId);
      }
    });
  }

  public retrieveUiapp(uiappId) {
    this.uiappService()
      .find(uiappId)
      .then(res => {
        this.uiapp = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
