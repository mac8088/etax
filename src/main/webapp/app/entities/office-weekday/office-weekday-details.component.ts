import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOfficeWeekday } from '@/shared/model/office-weekday.model';
import OfficeWeekdayService from './office-weekday.service';

@Component
export default class OfficeWeekdayDetails extends Vue {
  @Inject('officeWeekdayService') private officeWeekdayService: () => OfficeWeekdayService;
  public officeWeekday: IOfficeWeekday = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeWeekdayId) {
        vm.retrieveOfficeWeekday(to.params.officeWeekdayId);
      }
    });
  }

  public retrieveOfficeWeekday(officeWeekdayId) {
    this.officeWeekdayService()
      .find(officeWeekdayId)
      .then(res => {
        this.officeWeekday = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
