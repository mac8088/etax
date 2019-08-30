import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IStdCodesProp, StdCodesProp } from '@/shared/model/std-codes-prop.model';
import StdCodesPropService from './std-codes-prop.service';

const validations: any = {
  stdCodesProp: {
    groupCode: {
      required,
      maxLength: maxLength(40)
    },
    internalCode: {
      required,
      maxLength: maxLength(40)
    },
    propCode: {
      required,
      maxLength: maxLength(40)
    },
    startDate: {
      required
    },
    endDate: {},
    valueDate: {},
    valueString: {
      maxLength: maxLength(255)
    },
    valueBool: {},
    valueNumber: {}
  }
};

@Component({
  validations
})
export default class StdCodesPropUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesPropService') private stdCodesPropService: () => StdCodesPropService;
  public stdCodesProp: IStdCodesProp = new StdCodesProp();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesPropId) {
        vm.retrieveStdCodesProp(to.params.stdCodesPropId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.stdCodesProp.id) {
      this.stdCodesPropService()
        .update(this.stdCodesProp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesProp.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stdCodesPropService()
        .create(this.stdCodesProp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesProp.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.stdCodesProp[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesProp[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.stdCodesProp[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesProp[field] = null;
    }
  }

  public retrieveStdCodesProp(stdCodesPropId): void {
    this.stdCodesPropService()
      .find(stdCodesPropId)
      .then(res => {
        this.stdCodesProp = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
