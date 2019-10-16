import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UiappService from '../uiapp/uiapp.service';
import { IUiapp } from '@/shared/model/uiapp.model';

import AlertService from '@/shared/alert/alert.service';
import { IResource, Resource } from '@/shared/model/resource.model';
import ResourceService from './resource.service';

const validations: any = {
  resource: {
    resCode: {
      required,
      maxLength: maxLength(100)
    },
    resName: {
      maxLength: maxLength(200)
    },
    resType: {},
    appDesc: {
      maxLength: maxLength(500)
    },
    cstdModule: {
      maxLength: maxLength(40)
    },
    resContent: {
      maxLength: maxLength(500)
    }
  }
};

@Component({
  validations
})
export default class ResourceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('resourceService') private resourceService: () => ResourceService;
  public resource: IResource = new Resource();

  @Inject('uiappService') private uiappService: () => UiappService;

  public uiapps: IUiapp[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resourceId) {
        vm.retrieveResource(to.params.resourceId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.resource.id) {
      this.resourceService()
        .update(this.resource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.resource.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.resourceService()
        .create(this.resource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.resource.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveResource(resourceId): void {
    this.resourceService()
      .find(resourceId)
      .then(res => {
        this.resource = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.uiappService()
      .retrieve()
      .then(res => {
        this.uiapps = res.data;
      });
  }
}
