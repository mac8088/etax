/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeWeekdayUpdateComponent from '@/entities/office-weekday/office-weekday-update.vue';
import OfficeWeekdayClass from '@/entities/office-weekday/office-weekday-update.component';
import OfficeWeekdayService from '@/entities/office-weekday/office-weekday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('OfficeWeekday Management Update Component', () => {
    let wrapper: Wrapper<OfficeWeekdayClass>;
    let comp: OfficeWeekdayClass;
    let officeWeekdayServiceStub: SinonStubbedInstance<OfficeWeekdayService>;

    beforeEach(() => {
      officeWeekdayServiceStub = sinon.createStubInstance<OfficeWeekdayService>(OfficeWeekdayService);

      wrapper = shallowMount<OfficeWeekdayClass>(OfficeWeekdayUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officeWeekdayService: () => officeWeekdayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.officeWeekday = entity;
        officeWeekdayServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeWeekdayServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.officeWeekday = entity;
        officeWeekdayServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeWeekdayServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
