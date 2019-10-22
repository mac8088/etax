import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IProfileSetup, ProfileSetup } from '@/shared/model/profile-setup.model';
import ProfileSetupService from './profile-setup.service';

const validations: any = {
  profileSetup: {
    profileCode: {
      required,
      maxLength: maxLength(50)
    },
    cstdOfficeType: {
      required,
      maxLength: maxLength(40)
    },
    cstdUserType: {
      required,
      maxLength: maxLength(40)
    }
  }
};

@Component({
  validations
})
export default class ProfileSetupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('profileSetupService') private profileSetupService: () => ProfileSetupService;
  public profileSetup: IProfileSetup = new ProfileSetup();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.profileSetupId) {
        vm.retrieveProfileSetup(to.params.profileSetupId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.profileSetup.id) {
      this.profileSetupService()
        .update(this.profileSetup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.profileSetup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.profileSetupService()
        .create(this.profileSetup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.profileSetup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProfileSetup(profileSetupId): void {
    this.profileSetupService()
      .find(profileSetupId)
      .then(res => {
        this.profileSetup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
