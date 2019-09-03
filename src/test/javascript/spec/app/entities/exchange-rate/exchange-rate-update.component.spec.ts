/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ExchangeRateUpdateComponent from '@/entities/exchange-rate/exchange-rate-update.vue';
import ExchangeRateClass from '@/entities/exchange-rate/exchange-rate-update.component';
import ExchangeRateService from '@/entities/exchange-rate/exchange-rate.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ExchangeRate Management Update Component', () => {
    let wrapper: Wrapper<ExchangeRateClass>;
    let comp: ExchangeRateClass;
    let exchangeRateServiceStub: SinonStubbedInstance<ExchangeRateService>;

    beforeEach(() => {
      exchangeRateServiceStub = sinon.createStubInstance<ExchangeRateService>(ExchangeRateService);

      wrapper = shallowMount<ExchangeRateClass>(ExchangeRateUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          exchangeRateService: () => exchangeRateServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.exchangeRate = entity;
        exchangeRateServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(exchangeRateServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.exchangeRate = entity;
        exchangeRateServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(exchangeRateServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
