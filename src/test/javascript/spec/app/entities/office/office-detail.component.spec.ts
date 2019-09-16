/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeDetailComponent from '@/entities/office/office-details.vue';
import OfficeClass from '@/entities/office/office-details.component';
import OfficeService from '@/entities/office/office.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Office Management Detail Component', () => {
    let wrapper: Wrapper<OfficeClass>;
    let comp: OfficeClass;
    let officeServiceStub: SinonStubbedInstance<OfficeService>;

    beforeEach(() => {
      officeServiceStub = sinon.createStubInstance<OfficeService>(OfficeService);

      wrapper = shallowMount<OfficeClass>(OfficeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeService: () => officeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOffice = { id: 123 };
        officeServiceStub.find.resolves(foundOffice);

        // WHEN
        comp.retrieveOffice(123);
        await comp.$nextTick();

        // THEN
        expect(comp.office).toBe(foundOffice);
      });
    });
  });
});
