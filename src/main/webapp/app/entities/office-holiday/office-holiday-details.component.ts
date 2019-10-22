import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOfficeHoliday } from '@/shared/model/office-holiday.model';
import OfficeHolidayService from './office-holiday.service';

@Component
export default class OfficeHolidayDetails extends Vue {
  @Inject('officeHolidayService') private officeHolidayService: () => OfficeHolidayService;
  public officeHoliday: IOfficeHoliday = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeHolidayId) {
        vm.retrieveOfficeHoliday(to.params.officeHolidayId);
      }
    });
  }

  public retrieveOfficeHoliday(officeHolidayId) {
    this.officeHolidayService()
      .find(officeHolidayId)
      .then(res => {
        this.officeHoliday = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
