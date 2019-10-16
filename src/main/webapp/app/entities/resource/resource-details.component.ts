import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResource } from '@/shared/model/resource.model';
import ResourceService from './resource.service';

@Component
export default class ResourceDetails extends Vue {
  @Inject('resourceService') private resourceService: () => ResourceService;
  public resource: IResource = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resourceId) {
        vm.retrieveResource(to.params.resourceId);
      }
    });
  }

  public retrieveResource(resourceId) {
    this.resourceService()
      .find(resourceId)
      .then(res => {
        this.resource = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
