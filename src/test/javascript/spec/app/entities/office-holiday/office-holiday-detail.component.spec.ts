/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeHolidayDetailComponent from '@/entities/office-holiday/office-holiday-details.vue';
import OfficeHolidayClass from '@/entities/office-holiday/office-holiday-details.component';
import OfficeHolidayService from '@/entities/office-holiday/office-holiday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OfficeHoliday Management Detail Component', () => {
    let wrapper: Wrapper<OfficeHolidayClass>;
    let comp: OfficeHolidayClass;
    let officeHolidayServiceStub: SinonStubbedInstance<OfficeHolidayService>;

    beforeEach(() => {
      officeHolidayServiceStub = sinon.createStubInstance<OfficeHolidayService>(OfficeHolidayService);

      wrapper = shallowMount<OfficeHolidayClass>(OfficeHolidayDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeHolidayService: () => officeHolidayServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOfficeHoliday = { id: 123 };
        officeHolidayServiceStub.find.resolves(foundOfficeHoliday);

        // WHEN
        comp.retrieveOfficeHoliday(123);
        await comp.$nextTick();

        // THEN
        expect(comp.officeHoliday).toBe(foundOfficeHoliday);
      });
    });
  });
});
