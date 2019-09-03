import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import StdCodesDescService from '../std-codes-desc/std-codes-desc.service';
import { IStdCodesDesc } from '@/shared/model/std-codes-desc.model';

import StdCodesGroupService from '../std-codes-group/std-codes-group.service';
import { IStdCodesGroup } from '@/shared/model/std-codes-group.model';

import AlertService from '@/shared/alert/alert.service';
import { IStdCodes, StdCodes } from '@/shared/model/std-codes.model';
import StdCodesService from './std-codes.service';

const validations: any = {
  stdCodes: {
    groupCode: {
      required,
      maxLength: maxLength(40)
    },
    internalCode: {
      required,
      maxLength: maxLength(40)
    },
    startDate: {
      required
    },
    endDate: {},
    parentInternalCode: {
      maxLength: maxLength(40)
    },
    comments: {
      maxLength: maxLength(255)
    },
    secLevel: {
      required,
      numeric
    },
    codeValueDate: {},
    codeValueString: {
      maxLength: maxLength(255)
    },
    codeValueBool: {},
    codeValueNumber: {}
  }
};

@Component({
  validations
})
export default class StdCodesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesService') private stdCodesService: () => StdCodesService;
  public stdCodes: IStdCodes = new StdCodes();

  @Inject('stdCodesDescService') private stdCodesDescService: () => StdCodesDescService;

  public stdCodesDescs: IStdCodesDesc[] = [];

  @Inject('stdCodesGroupService') private stdCodesGroupService: () => StdCodesGroupService;

  public stdCodesGroups: IStdCodesGroup[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesId) {
        vm.retrieveStdCodes(to.params.stdCodesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.stdCodes.id) {
      this.stdCodesService()
        .update(this.stdCodes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodes.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stdCodesService()
        .create(this.stdCodes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodes.created', { param: param.id });
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
      this.stdCodes[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodes[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.stdCodes[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.stdCodes[field] = null;
    }
  }

  public retrieveStdCodes(stdCodesId): void {
    this.stdCodesService()
      .find(stdCodesId)
      .then(res => {
        this.stdCodes = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stdCodesDescService()
      .retrieve()
      .then(res => {
        this.stdCodesDescs = res.data;
      });
    this.stdCodesGroupService()
      .retrieve()
      .then(res => {
        this.stdCodesGroups = res.data;
      });
  }
}
