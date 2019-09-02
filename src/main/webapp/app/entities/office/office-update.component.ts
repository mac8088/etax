import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IOffice, Office } from '@/shared/model/office.model';
import OfficeService from './office.service';

const validations: any = {
  office: {
    cstdOfficeType: {
      required,
      maxLength: maxLength(40)
    },
    name: {
      required,
      maxLength: maxLength(50)
    },
    cstdClassifierCode: {
      maxLength: maxLength(40)
    },
    effectiveDate: {
      required
    },
    expiryDate: {},
    phone: {
      maxLength: maxLength(50)
    },
    fax: {
      maxLength: maxLength(50)
    },
    stl: {
      maxLength: maxLength(100)
    },
    mngOffice: {},
    physicalAdr: {},
    postalAadr: {},
    pinCode: {
      maxLength: maxLength(15)
    },
    cstdWeekWorkingDay: {
      maxLength: maxLength(40)
    },
    officeCode: {
      maxLength: maxLength(7)
    },
    cstdOfficeSubType: {
      maxLength: maxLength(40)
    },
    cstdOfficeFuncType: {
      maxLength: maxLength(40)
    },
    ccVersion: {}
  }
};

@Component({
  validations
})
export default class OfficeUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeService') private officeService: () => OfficeService;
  public office: IOffice = new Office();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeId) {
        vm.retrieveOffice(to.params.officeId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.office.id) {
      this.officeService()
        .update(this.office)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.office.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeService()
        .create(this.office)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.office.created', { param: param.id });
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
      this.office[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.office[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.office[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.office[field] = null;
    }
  }

  public retrieveOffice(officeId): void {
    this.officeService()
      .find(officeId)
      .then(res => {
        this.office = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
