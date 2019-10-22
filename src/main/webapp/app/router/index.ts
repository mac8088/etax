import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const BankAccount = () => import('../entities/bank-account/bank-account.vue');
// prettier-ignore
const BankAccountUpdate = () => import('../entities/bank-account/bank-account-update.vue');
// prettier-ignore
const BankAccountDetails = () => import('../entities/bank-account/bank-account-details.vue');
// prettier-ignore
const Label = () => import('../entities/label/label.vue');
// prettier-ignore
const LabelUpdate = () => import('../entities/label/label-update.vue');
// prettier-ignore
const LabelDetails = () => import('../entities/label/label-details.vue');
// prettier-ignore
const Operation = () => import('../entities/operation/operation.vue');
// prettier-ignore
const OperationUpdate = () => import('../entities/operation/operation-update.vue');
// prettier-ignore
const OperationDetails = () => import('../entities/operation/operation-details.vue');
// prettier-ignore
const StdCodesGroup = () => import('../entities/std-codes-group/std-codes-group.vue');
// prettier-ignore
const StdCodesGroupUpdate = () => import('../entities/std-codes-group/std-codes-group-update.vue');
// prettier-ignore
const StdCodesGroupDetails = () => import('../entities/std-codes-group/std-codes-group-details.vue');
// prettier-ignore
const StdCodes = () => import('../entities/std-codes/std-codes.vue');
// prettier-ignore
const StdCodesUpdate = () => import('../entities/std-codes/std-codes-update.vue');
// prettier-ignore
const StdCodesDetails = () => import('../entities/std-codes/std-codes-details.vue');
// prettier-ignore
const StdCodesDesc = () => import('../entities/std-codes-desc/std-codes-desc.vue');
// prettier-ignore
const StdCodesDescUpdate = () => import('../entities/std-codes-desc/std-codes-desc-update.vue');
// prettier-ignore
const StdCodesDescDetails = () => import('../entities/std-codes-desc/std-codes-desc-details.vue');
// prettier-ignore
const StdCodesGroupProp = () => import('../entities/std-codes-group-prop/std-codes-group-prop.vue');
// prettier-ignore
const StdCodesGroupPropUpdate = () => import('../entities/std-codes-group-prop/std-codes-group-prop-update.vue');
// prettier-ignore
const StdCodesGroupPropDetails = () => import('../entities/std-codes-group-prop/std-codes-group-prop-details.vue');
// prettier-ignore
const StdCodesProp = () => import('../entities/std-codes-prop/std-codes-prop.vue');
// prettier-ignore
const StdCodesPropUpdate = () => import('../entities/std-codes-prop/std-codes-prop-update.vue');
// prettier-ignore
const StdCodesPropDetails = () => import('../entities/std-codes-prop/std-codes-prop-details.vue');
// prettier-ignore
const ExchangeRate = () => import('../entities/exchange-rate/exchange-rate.vue');
// prettier-ignore
const ExchangeRateUpdate = () => import('../entities/exchange-rate/exchange-rate-update.vue');
// prettier-ignore
const ExchangeRateDetails = () => import('../entities/exchange-rate/exchange-rate-details.vue');
// prettier-ignore
const PublicHoliday = () => import('../entities/public-holiday/public-holiday.vue');
// prettier-ignore
const PublicHolidayUpdate = () => import('../entities/public-holiday/public-holiday-update.vue');
// prettier-ignore
const PublicHolidayDetails = () => import('../entities/public-holiday/public-holiday-details.vue');
// prettier-ignore
const Office = () => import('../entities/office/office.vue');
// prettier-ignore
const OfficeUpdate = () => import('../entities/office/office-update.vue');
// prettier-ignore
const OfficeDetails = () => import('../entities/office/office-details.vue');
// prettier-ignore
const OfficeRelationship = () => import('../entities/office-relationship/office-relationship.vue');
// prettier-ignore
const OfficeRelationshipUpdate = () => import('../entities/office-relationship/office-relationship-update.vue');
// prettier-ignore
const OfficeRelationshipDetails = () => import('../entities/office-relationship/office-relationship-details.vue');
// prettier-ignore
const OfficeAreaCode = () => import('../entities/office-area-code/office-area-code.vue');
// prettier-ignore
const OfficeAreaCodeUpdate = () => import('../entities/office-area-code/office-area-code-update.vue');
// prettier-ignore
const OfficeAreaCodeDetails = () => import('../entities/office-area-code/office-area-code-details.vue');
// prettier-ignore
const OfficeTaxFunc = () => import('../entities/office-tax-func/office-tax-func.vue');
// prettier-ignore
const OfficeTaxFuncUpdate = () => import('../entities/office-tax-func/office-tax-func-update.vue');
// prettier-ignore
const OfficeTaxFuncDetails = () => import('../entities/office-tax-func/office-tax-func-details.vue');
// prettier-ignore
const OfficeHoliday = () => import('../entities/office-holiday/office-holiday.vue');
// prettier-ignore
const OfficeHolidayUpdate = () => import('../entities/office-holiday/office-holiday-update.vue');
// prettier-ignore
const OfficeHolidayDetails = () => import('../entities/office-holiday/office-holiday-details.vue');
// prettier-ignore
const OfficeWeekday = () => import('../entities/office-weekday/office-weekday.vue');
// prettier-ignore
const OfficeWeekdayUpdate = () => import('../entities/office-weekday/office-weekday-update.vue');
// prettier-ignore
const OfficeWeekdayDetails = () => import('../entities/office-weekday/office-weekday-details.vue');
// prettier-ignore
const ProfileSetup = () => import('../entities/profile-setup/profile-setup.vue');
// prettier-ignore
const ProfileSetupUpdate = () => import('../entities/profile-setup/profile-setup-update.vue');
// prettier-ignore
const ProfileSetupDetails = () => import('../entities/profile-setup/profile-setup-details.vue');
// prettier-ignore
const UserInfo = () => import('../entities/user-info/user-info.vue');
// prettier-ignore
const UserInfoUpdate = () => import('../entities/user-info/user-info-update.vue');
// prettier-ignore
const UserInfoDetails = () => import('../entities/user-info/user-info-details.vue');
// prettier-ignore
const Uiapp = () => import('../entities/uiapp/uiapp.vue');
// prettier-ignore
const UiappUpdate = () => import('../entities/uiapp/uiapp-update.vue');
// prettier-ignore
const UiappDetails = () => import('../entities/uiapp/uiapp-details.vue');
// prettier-ignore
const Resource = () => import('../entities/resource/resource.vue');
// prettier-ignore
const ResourceUpdate = () => import('../entities/resource/resource-update.vue');
// prettier-ignore
const ResourceDetails = () => import('../entities/resource/resource-details.vue');
// prettier-ignore
const Privilege = () => import('../entities/privilege/privilege.vue');
// prettier-ignore
const PrivilegeUpdate = () => import('../entities/privilege/privilege-update.vue');
// prettier-ignore
const PrivilegeDetails = () => import('../entities/privilege/privilege-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/bank-account',
      name: 'BankAccount',
      component: BankAccount,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/bank-account/new',
      name: 'BankAccountCreate',
      component: BankAccountUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/bank-account/:bankAccountId/edit',
      name: 'BankAccountEdit',
      component: BankAccountUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/bank-account/:bankAccountId/view',
      name: 'BankAccountView',
      component: BankAccountDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/label',
      name: 'Label',
      component: Label,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/label/new',
      name: 'LabelCreate',
      component: LabelUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/label/:labelId/edit',
      name: 'LabelEdit',
      component: LabelUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/label/:labelId/view',
      name: 'LabelView',
      component: LabelDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/operation',
      name: 'Operation',
      component: Operation,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/operation/new',
      name: 'OperationCreate',
      component: OperationUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/operation/:operationId/edit',
      name: 'OperationEdit',
      component: OperationUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/operation/:operationId/view',
      name: 'OperationView',
      component: OperationDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/std-codes-group',
      name: 'StdCodesGroup',
      component: StdCodesGroup,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group/new',
      name: 'StdCodesGroupCreate',
      component: StdCodesGroupUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group/:stdCodesGroupId/edit',
      name: 'StdCodesGroupEdit',
      component: StdCodesGroupUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group/:stdCodesGroupId/view',
      name: 'StdCodesGroupView',
      component: StdCodesGroupDetails,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes',
      name: 'StdCodes',
      component: StdCodes,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes/new',
      name: 'StdCodesCreate',
      component: StdCodesUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes/:stdCodesId/edit',
      name: 'StdCodesEdit',
      component: StdCodesUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes/:stdCodesId/view',
      name: 'StdCodesView',
      component: StdCodesDetails,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-desc',
      name: 'StdCodesDesc',
      component: StdCodesDesc,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-desc/new',
      name: 'StdCodesDescCreate',
      component: StdCodesDescUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-desc/:stdCodesDescId/edit',
      name: 'StdCodesDescEdit',
      component: StdCodesDescUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-desc/:stdCodesDescId/view',
      name: 'StdCodesDescView',
      component: StdCodesDescDetails,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group-prop',
      name: 'StdCodesGroupProp',
      component: StdCodesGroupProp,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group-prop/new',
      name: 'StdCodesGroupPropCreate',
      component: StdCodesGroupPropUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group-prop/:stdCodesGroupPropId/edit',
      name: 'StdCodesGroupPropEdit',
      component: StdCodesGroupPropUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-group-prop/:stdCodesGroupPropId/view',
      name: 'StdCodesGroupPropView',
      component: StdCodesGroupPropDetails,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-prop',
      name: 'StdCodesProp',
      component: StdCodesProp,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-prop/new',
      name: 'StdCodesPropCreate',
      component: StdCodesPropUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-prop/:stdCodesPropId/edit',
      name: 'StdCodesPropEdit',
      component: StdCodesPropUpdate,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/std-codes-prop/:stdCodesPropId/view',
      name: 'StdCodesPropView',
      component: StdCodesPropDetails,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/entity/exchange-rate',
      name: 'ExchangeRate',
      component: ExchangeRate,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/exchange-rate/new',
      name: 'ExchangeRateCreate',
      component: ExchangeRateUpdate,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/exchange-rate/:exchangeRateId/edit',
      name: 'ExchangeRateEdit',
      component: ExchangeRateUpdate,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/exchange-rate/:exchangeRateId/view',
      name: 'ExchangeRateView',
      component: ExchangeRateDetails,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/public-holiday',
      name: 'PublicHoliday',
      component: PublicHoliday,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/public-holiday/new',
      name: 'PublicHolidayCreate',
      component: PublicHolidayUpdate,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/public-holiday/:publicHolidayId/edit',
      name: 'PublicHolidayEdit',
      component: PublicHolidayUpdate,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/public-holiday/:publicHolidayId/view',
      name: 'PublicHolidayView',
      component: PublicHolidayDetails,
      meta: { authorities: ['ADM002'] }
    },
    {
      path: '/entity/office',
      name: 'Office',
      component: Office,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office/new',
      name: 'OfficeCreate',
      component: OfficeUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office/:officeId/edit',
      name: 'OfficeEdit',
      component: OfficeUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office/:officeId/view',
      name: 'OfficeView',
      component: OfficeDetails,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-relationship',
      name: 'OfficeRelationship',
      component: OfficeRelationship,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-relationship/new',
      name: 'OfficeRelationshipCreate',
      component: OfficeRelationshipUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-relationship/:officeRelationshipId/edit',
      name: 'OfficeRelationshipEdit',
      component: OfficeRelationshipUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-relationship/:officeRelationshipId/view',
      name: 'OfficeRelationshipView',
      component: OfficeRelationshipDetails,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-area-code',
      name: 'OfficeAreaCode',
      component: OfficeAreaCode,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/office-area-code/new',
      name: 'OfficeAreaCodeCreate',
      component: OfficeAreaCodeUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-area-code/:officeAreaCodeId/edit',
      name: 'OfficeAreaCodeEdit',
      component: OfficeAreaCodeUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-area-code/:officeAreaCodeId/view',
      name: 'OfficeAreaCodeView',
      component: OfficeAreaCodeDetails,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-tax-func',
      name: 'OfficeTaxFunc',
      component: OfficeTaxFunc,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-tax-func/new',
      name: 'OfficeTaxFuncCreate',
      component: OfficeTaxFuncUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-tax-func/:officeTaxFuncId/edit',
      name: 'OfficeTaxFuncEdit',
      component: OfficeTaxFuncUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-tax-func/:officeTaxFuncId/view',
      name: 'OfficeTaxFuncView',
      component: OfficeTaxFuncDetails,
      meta: { authorities: ['ADM001'] }
    }
    ,
    {
      path: '/entity/office-weekday',
      name: 'OfficeWeekday',
      component: OfficeWeekday,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-weekday/new',
      name: 'OfficeWeekdayCreate',
      component: OfficeWeekdayUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-weekday/:officeWeekdayId/edit',
      name: 'OfficeWeekdayEdit',
      component: OfficeWeekdayUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-weekday/:officeWeekdayId/view',
      name: 'OfficeWeekdayView',
      component: OfficeWeekdayDetails,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/office-holiday',
      name: 'OfficeHoliday',
      component: OfficeHoliday,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/office-holiday/new',
      name: 'OfficeHolidayCreate',
      component: OfficeHolidayUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/office-holiday/:officeHolidayId/edit',
      name: 'OfficeHolidayEdit',
      component: OfficeHolidayUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/office-holiday/:officeHolidayId/view',
      name: 'OfficeHolidayView',
      component: OfficeHolidayDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/profile-setup',
      name: 'ProfileSetup',
      component: ProfileSetup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/profile-setup/new',
      name: 'ProfileSetupCreate',
      component: ProfileSetupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/profile-setup/:profileSetupId/edit',
      name: 'ProfileSetupEdit',
      component: ProfileSetupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/profile-setup/:profileSetupId/view',
      name: 'ProfileSetupView',
      component: ProfileSetupDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/user-info',
      name: 'UserInfo',
      component: UserInfo,
      meta: { authorities: ['ADM001', 'ADM002'] }
    },
    {
      path: '/entity/user-info/new',
      name: 'UserInfoCreate',
      component: UserInfoUpdate,
      meta: { authorities: ['ADM001'] }
    },
    {
      path: '/entity/user-info/:userInfoId/edit',
      name: 'UserInfoEdit',
      component: UserInfoUpdate,
      meta: { authorities: ['ADM001', 'ADM002'] }
    },
    {
      path: '/entity/user-info/:userInfoId/view',
      name: 'UserInfoView',
      component: UserInfoDetails,
      meta: { authorities: ['ADM001', 'ADM002'] }
    },
    {
      path: '/entity/uiapp',
      name: 'Uiapp',
      component: Uiapp,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/uiapp/new',
      name: 'UiappCreate',
      component: UiappUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/uiapp/:uiappId/edit',
      name: 'UiappEdit',
      component: UiappUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/uiapp/:uiappId/view',
      name: 'UiappView',
      component: UiappDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/resource',
      name: 'Resource',
      component: Resource,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/resource/new',
      name: 'ResourceCreate',
      component: ResourceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/resource/:resourceId/edit',
      name: 'ResourceEdit',
      component: ResourceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/resource/:resourceId/view',
      name: 'ResourceView',
      component: ResourceDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/privilege',
      name: 'Privilege',
      component: Privilege,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/privilege/new',
      name: 'PrivilegeCreate',
      component: PrivilegeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/privilege/:privilegeId/edit',
      name: 'PrivilegeEdit',
      component: PrivilegeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/privilege/:privilegeId/view',
      name: 'PrivilegeView',
      component: PrivilegeDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
