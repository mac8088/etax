import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import StdCodesService from '../std-codes/std-codes.service';
import { IStdCodes } from '@/shared/model/std-codes.model';

import AlertService from '@/shared/alert/alert.service';
import { IStdCodesDesc, StdCodesDesc } from '@/shared/model/std-codes-desc.model';
import StdCodesDescService from './std-codes-desc.service';

const validations: any = {
  stdCodesDesc: {
    groupCode: {
      required,
      maxLength: maxLength(40)
    },
    internalCode: {
      required,
      maxLength: maxLength(40)
    },
    langCode: {
      required,
      maxLength: maxLength(10)
    },
    startDate: {
      required
    },
    endDate: {},
    externalCode: {
      maxLength: maxLength(40)
    },
    codeDesc: {
      maxLength: maxLength(500)
    }
  }
};

@Component({
  validations
})
export default class StdCodesDescUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesDescService') private stdCodesDescService: () => StdCodesDescService;
  public stdCodesDesc: IStdCodesDesc = new StdCodesDesc();

  @Inject('stdCodesService') private stdCodesService: () => StdCodesService;

  public stdCodes: IStdCodes[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesDescId) {
        vm.retrieveStdCodesDesc(to.params.stdCodesDescId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.stdCodesDesc.id) {
      this.stdCodesDescService()
        .update(this.stdCodesDesc)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesDesc.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stdCodesDescService()
        .create(this.stdCodesDesc)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesDesc.created', { param: param.id });
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
      this.stdCodesDesc[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesDesc[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.stdCodesDesc[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodesDesc[field] = null;
    }
  }

  public retrieveStdCodesDesc(stdCodesDescId): void {
    this.stdCodesDescService()
      .find(stdCodesDescId)
      .then(res => {
        this.stdCodesDesc = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stdCodesService()
      .retrieve()
      .then(res => {
        this.stdCodes = res.data;
      });
  }
}
