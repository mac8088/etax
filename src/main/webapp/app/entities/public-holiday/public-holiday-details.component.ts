import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublicHoliday } from '@/shared/model/public-holiday.model';
import PublicHolidayService from './public-holiday.service';

@Component
export default class PublicHolidayDetails extends Vue {
  @Inject('publicHolidayService') private publicHolidayService: () => PublicHolidayService;
  public publicHoliday: IPublicHoliday = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicHolidayId) {
        vm.retrievePublicHoliday(to.params.publicHolidayId);
      }
    });
  }

  public retrievePublicHoliday(publicHolidayId) {
    this.publicHolidayService()
      .find(publicHolidayId)
      .then(res => {
        this.publicHoliday = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
