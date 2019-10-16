import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrivilege } from '@/shared/model/privilege.model';
import PrivilegeService from './privilege.service';

@Component
export default class PrivilegeDetails extends Vue {
  @Inject('privilegeService') private privilegeService: () => PrivilegeService;
  public privilege: IPrivilege = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privilegeId) {
        vm.retrievePrivilege(to.params.privilegeId);
      }
    });
  }

  public retrievePrivilege(privilegeId) {
    this.privilegeService()
      .find(privilegeId)
      .then(res => {
        this.privilege = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
