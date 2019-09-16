import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOfficeRelationship } from '@/shared/model/office-relationship.model';
import OfficeRelationshipService from './office-relationship.service';

@Component
export default class OfficeRelationshipDetails extends Vue {
  @Inject('officeRelationshipService') private officeRelationshipService: () => OfficeRelationshipService;
  public officeRelationship: IOfficeRelationship = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeRelationshipId) {
        vm.retrieveOfficeRelationship(to.params.officeRelationshipId);
      }
    });
  }

  public retrieveOfficeRelationship(officeRelationshipId) {
    this.officeRelationshipService()
      .find(officeRelationshipId)
      .then(res => {
        this.officeRelationship = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
