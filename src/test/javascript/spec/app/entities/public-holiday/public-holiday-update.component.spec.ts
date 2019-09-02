/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PublicHolidayUpdateComponent from '@/entities/public-holiday/public-holiday-update.vue';
import PublicHolidayClass from '@/entities/public-holiday/public-holiday-update.component';
import PublicHolidayService from '@/entities/public-holiday/public-holiday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PublicHoliday Management Update Component', () => {
    let wrapper: Wrapper<PublicHolidayClass>;
    let comp: PublicHolidayClass;
    let publicHolidayServiceStub: SinonStubbedInstance<PublicHolidayService>;

    beforeEach(() => {
      publicHolidayServiceStub = sinon.createStubInstance<PublicHolidayService>(PublicHolidayService);

      wrapper = shallowMount<PublicHolidayClass>(PublicHolidayUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          publicHolidayService: () => publicHolidayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.publicHoliday = entity;
        publicHolidayServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicHolidayServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publicHoliday = entity;
        publicHolidayServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicHolidayServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
