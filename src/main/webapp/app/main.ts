// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import AuditsService from './admin/audits/audits.service';
import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import BankAccountService from '@/entities/bank-account/bank-account.service';
import LabelService from '@/entities/label/label.service';
import OperationService from '@/entities/operation/operation.service';
import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';
import StdCodesService from '@/entities/std-codes/std-codes.service';
import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';
import StdCodesGroupPropService from '@/entities/std-codes-group-prop/std-codes-group-prop.service';
import StdCodesPropService from '@/entities/std-codes-prop/std-codes-prop.service';
import ExchangeRateService from '@/entities/exchange-rate/exchange-rate.service';
import PublicHolidayService from '@/entities/public-holiday/public-holiday.service';
import OfficeService from '@/entities/office/office.service';
import OfficeRelationshipService from '@/entities/office-relationship/office-relationship.service';
import OfficeAreaCodeService from '@/entities/office-area-code/office-area-code.service';
import OfficeTaxFuncService from '@/entities/office-tax-func/office-tax-func.service';
import OfficeWeekdayService from '@/entities/office-weekday/office-weekday.service';
import UserInfoService from '@/entities/user-info/user-info.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),
    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    bankAccountService: () => new BankAccountService(),
    labelService: () => new LabelService(),
    operationService: () => new OperationService(),
    stdCodesGroupService: () => new StdCodesGroupService(),
    stdCodesService: () => new StdCodesService(),
    stdCodesDescService: () => new StdCodesDescService(),
    stdCodesGroupPropService: () => new StdCodesGroupPropService(),
    stdCodesPropService: () => new StdCodesPropService(),
    exchangeRateService: () => new ExchangeRateService(),
    publicHolidayService: () => new PublicHolidayService(),
    officeService: () => new OfficeService(),
    officeRelationshipService: () => new OfficeRelationshipService(),
    officeAreaCodeService: () => new OfficeAreaCodeService(),
    officeTaxFuncService: () => new OfficeTaxFuncService(),
    officeWeekdayService: () => new OfficeWeekdayService(),
    userInfoService: () => new UserInfoService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService
  },
  i18n,
  store
});
