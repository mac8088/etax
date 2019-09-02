/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ExchangeRateComponent from '@/entities/exchange-rate/exchange-rate.vue';
import ExchangeRateClass from '@/entities/exchange-rate/exchange-rate.component';
import ExchangeRateService from '@/entities/exchange-rate/exchange-rate.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('ExchangeRate Management Component', () => {
    let wrapper: Wrapper<ExchangeRateClass>;
    let comp: ExchangeRateClass;
    let exchangeRateServiceStub: SinonStubbedInstance<ExchangeRateService>;

    beforeEach(() => {
      exchangeRateServiceStub = sinon.createStubInstance<ExchangeRateService>(ExchangeRateService);
      exchangeRateServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ExchangeRateClass>(ExchangeRateComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          exchangeRateService: () => exchangeRateServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      exchangeRateServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllExchangeRates();
      await comp.$nextTick();

      // THEN
      expect(exchangeRateServiceStub.retrieve.called).toBeTruthy();
      expect(comp.exchangeRates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      exchangeRateServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeExchangeRate();
      await comp.$nextTick();

      // THEN
      expect(exchangeRateServiceStub.delete.called).toBeTruthy();
      expect(exchangeRateServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
