import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ResourceService from '../resource/resource.service';
import { IResource } from '@/shared/model/resource.model';

import AlertService from '@/shared/alert/alert.service';
import { IUiapp, Uiapp } from '@/shared/model/uiapp.model';
import UiappService from './uiapp.service';

const validations: any = {
  uiapp: {
    appCode: {
      required,
      maxLength: maxLength(50)
    },
    appName: {
      maxLength: maxLength(200)
    },
    appDesc: {
      maxLength: maxLength(500)
    },
    cstdModule: {
      maxLength: maxLength(40)
    },
    appMessage: {
      maxLength: maxLength(50)
    }
  }
};

@Component({
  validations
})
export default class UiappUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('uiappService') private uiappService: () => UiappService;
  public uiapp: IUiapp = new Uiapp();

  @Inject('resourceService') private resourceService: () => ResourceService;

  public resources: IResource[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.uiappId) {
        vm.retrieveUiapp(to.params.uiappId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.uiapp.resources = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.uiapp.id) {
      this.uiappService()
        .update(this.uiapp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.uiapp.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.uiappService()
        .create(this.uiapp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.uiapp.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUiapp(uiappId): void {
    this.uiappService()
      .find(uiappId)
      .then(res => {
        this.uiapp = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.resourceService()
      .retrieve()
      .then(res => {
        this.resources = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
