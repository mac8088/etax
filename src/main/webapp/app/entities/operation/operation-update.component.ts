import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import LabelService from '../label/label.service';
import { ILabel } from '@/shared/model/label.model';

import BankAccountService from '../bank-account/bank-account.service';
import { IBankAccount } from '@/shared/model/bank-account.model';

import AlertService from '@/shared/alert/alert.service';
import { IOperation, Operation } from '@/shared/model/operation.model';
import OperationService from './operation.service';

const validations: any = {
  operation: {
    date: {
      required
    },
    description: {},
    amount: {
      required,
      numeric
    }
  }
};

@Component({
  validations
})
export default class OperationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('operationService') private operationService: () => OperationService;
  public operation: IOperation = new Operation();

  @Inject('labelService') private labelService: () => LabelService;

  public labels: ILabel[] = [];

  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;

  public bankAccounts: IBankAccount[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operationId) {
        vm.retrieveOperation(to.params.operationId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.operation.labels = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.operation.id) {
      this.operationService()
        .update(this.operation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.operation.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.operationService()
        .create(this.operation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.operation.created', { param: param.id });
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
      this.operation[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.operation[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.operation[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.operation[field] = null;
    }
  }

  public retrieveOperation(operationId): void {
    this.operationService()
      .find(operationId)
      .then(res => {
        this.operation = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.labelService()
      .retrieve()
      .then(res => {
        this.labels = res.data;
      });
    this.bankAccountService()
      .retrieve()
      .then(res => {
        this.bankAccounts = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
