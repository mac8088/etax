/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ExchangeRateDetailComponent from '@/entities/exchange-rate/exchange-rate-details.vue';
import ExchangeRateClass from '@/entities/exchange-rate/exchange-rate-details.component';
import ExchangeRateService from '@/entities/exchange-rate/exchange-rate.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ExchangeRate Management Detail Component', () => {
    let wrapper: Wrapper<ExchangeRateClass>;
    let comp: ExchangeRateClass;
    let exchangeRateServiceStub: SinonStubbedInstance<ExchangeRateService>;

    beforeEach(() => {
      exchangeRateServiceStub = sinon.createStubInstance<ExchangeRateService>(ExchangeRateService);

      wrapper = shallowMount<ExchangeRateClass>(ExchangeRateDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { exchangeRateService: () => exchangeRateServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExchangeRate = { id: 123 };
        exchangeRateServiceStub.find.resolves(foundExchangeRate);

        // WHEN
        comp.retrieveExchangeRate(123);
        await comp.$nextTick();

        // THEN
        expect(comp.exchangeRate).toBe(foundExchangeRate);
      });
    });
  });
});
