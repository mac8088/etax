import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import StdCodesService from '../std-codes/std-codes.service';
import { IStdCodes } from '@/shared/model/std-codes.model';

import AlertService from '@/shared/alert/alert.service';
import { IStdCodesGroup, StdCodesGroup } from '@/shared/model/std-codes-group.model';
import StdCodesGroupService from './std-codes-group.service';

const validations: any = {
  stdCodesGroup: {
    groupCode: {
      required,
      maxLength: maxLength(40)
    },
    groupDesc: {
      required,
      maxLength: maxLength(255)
    },
    systemInd: {
      required
    },
    secLevelRequired: {
      required
    },
    valueRequired: {
      required
    },
    valueType: {},
    descriptionRequired: {
      required
    },
    externalCodeRequired: {},
    externalCodeLength: {},
    parentGroupRequired: {
      required
    },
    parentGroupCode: {},
    touppercase: {}
  }
};

@Component({
  validations
})
export default class StdCodesGroupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stdCodesGroupService') private stdCodesGroupService: () => StdCodesGroupService;
  public stdCodesGroup: IStdCodesGroup = new StdCodesGroup();

  @Inject('stdCodesService') private stdCodesService: () => StdCodesService;

  public stdCodes: IStdCodes[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stdCodesGroupId) {
        vm.retrieveStdCodesGroup(to.params.stdCodesGroupId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.stdCodesGroup.id) {
      this.stdCodesGroupService()
        .update(this.stdCodesGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesGroup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stdCodesGroupService()
        .create(this.stdCodesGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('etaxApp.stdCodesGroup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStdCodesGroup(stdCodesGroupId): void {
    this.stdCodesGroupService()
      .find(stdCodesGroupId)
      .then(res => {
        this.stdCodesGroup = res;
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
