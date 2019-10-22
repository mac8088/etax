import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProfileSetup } from '@/shared/model/profile-setup.model';
import ProfileSetupService from './profile-setup.service';

@Component
export default class ProfileSetupDetails extends Vue {
  @Inject('profileSetupService') private profileSetupService: () => ProfileSetupService;
  public profileSetup: IProfileSetup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.profileSetupId) {
        vm.retrieveProfileSetup(to.params.profileSetupId);
      }
    });
  }

  public retrieveProfileSetup(profileSetupId) {
    this.profileSetupService()
      .find(profileSetupId)
      .then(res => {
        this.profileSetup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
