/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PublicHolidayDetailComponent from '@/entities/public-holiday/public-holiday-details.vue';
import PublicHolidayClass from '@/entities/public-holiday/public-holiday-details.component';
import PublicHolidayService from '@/entities/public-holiday/public-holiday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PublicHoliday Management Detail Component', () => {
    let wrapper: Wrapper<PublicHolidayClass>;
    let comp: PublicHolidayClass;
    let publicHolidayServiceStub: SinonStubbedInstance<PublicHolidayService>;

    beforeEach(() => {
      publicHolidayServiceStub = sinon.createStubInstance<PublicHolidayService>(PublicHolidayService);

      wrapper = shallowMount<PublicHolidayClass>(PublicHolidayDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { publicHolidayService: () => publicHolidayServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublicHoliday = { id: 123 };
        publicHolidayServiceStub.find.resolves(foundPublicHoliday);

        // WHEN
        comp.retrievePublicHoliday(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publicHoliday).toBe(foundPublicHoliday);
      });
    });
  });
});
