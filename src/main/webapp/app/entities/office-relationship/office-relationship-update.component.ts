import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IOfficeRelationship, OfficeRelationship } from '@/shared/model/office-relationship.model';
import OfficeRelationshipService from './office-relationship.service';

const validations: any = {
  officeRelationship: {
    parentId: {
      required,
      numeric
    },
    chileId: {
      required,
      numeric
    },
    startDate: {
      required
    },
    endDate: {},
    ccVersion: {}
  }
};

@Component({
  validations
})
export default class OfficeRelationshipUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officeRelationshipService') private officeRelationshipService: () => OfficeRelationshipService;
  public officeRelationship: IOfficeRelationship = new OfficeRelationship();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officeRelationshipId) {
        vm.retrieveOfficeRelationship(to.params.officeRelationshipId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.officeRelationship.id) {
      this.officeRelationshipService()
        .update(this.officeRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeRelationship.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officeRelationshipService()
        .create(this.officeRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.officeRelationship.created', { param: param.id });
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
      this.officeRelationship[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.officeRelationship[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.officeRelationship[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.officeRelationship[field] = null;
    }
  }

  public retrieveOfficeRelationship(officeRelationshipId): void {
    this.officeRelationshipService()
      .find(officeRelationshipId)
      .then(res => {
        this.officeRelationship = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
