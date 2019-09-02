/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeWeekdayDetailComponent from '@/entities/office-weekday/office-weekday-details.vue';
import OfficeWeekdayClass from '@/entities/office-weekday/office-weekday-details.component';
import OfficeWeekdayService from '@/entities/office-weekday/office-weekday.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OfficeWeekday Management Detail Component', () => {
    let wrapper: Wrapper<OfficeWeekdayClass>;
    let comp: OfficeWeekdayClass;
    let officeWeekdayServiceStub: SinonStubbedInstance<OfficeWeekdayService>;

    beforeEach(() => {
      officeWeekdayServiceStub = sinon.createStubInstance<OfficeWeekdayService>(OfficeWeekdayService);

      wrapper = shallowMount<OfficeWeekdayClass>(OfficeWeekdayDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeWeekdayService: () => officeWeekdayServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOfficeWeekday = { id: 123 };
        officeWeekdayServiceStub.find.resolves(foundOfficeWeekday);

        // WHEN
        comp.retrieveOfficeWeekday(123);
        await comp.$nextTick();

        // THEN
        expect(comp.officeWeekday).toBe(foundOfficeWeekday);
      });
    });
  });
});
