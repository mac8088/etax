/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeHolidayUpdateComponent from '@/entities/office-holiday/office-holiday-update.vue';
import OfficeHolidayClass from '@/entities/office-holiday/office-holiday-update.component';
import OfficeHolidayService from '@/entities/office-holiday/office-holiday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('OfficeHoliday Management Update Component', () => {
    let wrapper: Wrapper<OfficeHolidayClass>;
    let comp: OfficeHolidayClass;
    let officeHolidayServiceStub: SinonStubbedInstance<OfficeHolidayService>;

    beforeEach(() => {
      officeHolidayServiceStub = sinon.createStubInstance<OfficeHolidayService>(OfficeHolidayService);

      wrapper = shallowMount<OfficeHolidayClass>(OfficeHolidayUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officeHolidayService: () => officeHolidayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.officeHoliday = entity;
        officeHolidayServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeHolidayServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.officeHoliday = entity;
        officeHolidayServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeHolidayServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
