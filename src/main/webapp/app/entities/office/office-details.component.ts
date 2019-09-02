import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IOffice } from '@/shared/model/office.model';
import OfficeService from './office.service';

@Component
export default class OfficeDetails extends mixins(JhiDataUtils) {
  @Inject('officeService') private officeService: () => OfficeService;
  public office: IOffice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeId) {
        vm.retrieveOffice(to.params.officeId);
      }
    });
  }

  public retrieveOffice(officeId) {
    this.officeService()
      .find(officeId)
      .then(res => {
        this.office = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
