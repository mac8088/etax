import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IStdCodesGroupProp, StdCodesGroupProp } from '@/shared/model/std-codes-group-prop.model';
import StdCodesGroupPropService from './std-codes-group-prop.service';

const validations: any = {
  stdCodesGroupProp: {
    groupCode: {
      required,
      maxLength: maxLength(40)
    },
    propCode: {
      required,
      maxLength: maxLength(40)
    },
    propDesc: {
      required,
      maxLength: maxLength(255)
    },
    startDate: {
      required
    },
    endDate: {},
    propType: {},
    propMdtr: {},
    dfltValueDate: {},
    dfltValueString: {
      maxLength: maxLength(255)
    },
    dfltValueBool: {},
    dfltValueNumber: {}
  }
};

@Component({
  validations
})
export default class StdCodesGroupPropUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesGroupPropService') private stdCodesGroupPropService: () => StdCodesGroupPropService;
  public stdCodesGroupProp: IStdCodesGroupProp = new StdCodesGroupProp();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesGroupPropId) {
        vm.retrieveStdCodesGroupProp(to.params.stdCodesGroupPropId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.stdCodesGroupProp.id) {
      this.stdCodesGroupPropService()
        .update(this.stdCodesGroupProp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesGroupProp.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stdCodesGroupPropService()
        .create(this.stdCodesGroupProp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesGroupProp.created', { param: param.id });
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
      this.stdCodesGroupProp[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesGroupProp[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.stdCodesGroupProp[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesGroupProp[field] = null;
    }
  }

  public retrieveStdCodesGroupProp(stdCodesGroupPropId): void {
    this.stdCodesGroupPropService()
      .find(stdCodesGroupPropId)
      .then(res => {
        this.stdCodesGroupProp = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
