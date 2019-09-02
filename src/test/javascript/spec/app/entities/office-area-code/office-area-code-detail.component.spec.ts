/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeAreaCodeDetailComponent from '@/entities/office-area-code/office-area-code-details.vue';
import OfficeAreaCodeClass from '@/entities/office-area-code/office-area-code-details.component';
import OfficeAreaCodeService from '@/entities/office-area-code/office-area-code.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OfficeAreaCode Management Detail Component', () => {
    let wrapper: Wrapper<OfficeAreaCodeClass>;
    let comp: OfficeAreaCodeClass;
    let officeAreaCodeServiceStub: SinonStubbedInstance<OfficeAreaCodeService>;

    beforeEach(() => {
      officeAreaCodeServiceStub = sinon.createStubInstance<OfficeAreaCodeService>(OfficeAreaCodeService);

      wrapper = shallowMount<OfficeAreaCodeClass>(OfficeAreaCodeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeAreaCodeService: () => officeAreaCodeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOfficeAreaCode = { id: 123 };
        officeAreaCodeServiceStub.find.resolves(foundOfficeAreaCode);

        // WHEN
        comp.retrieveOfficeAreaCode(123);
        await comp.$nextTick();

        // THEN
        expect(comp.officeAreaCode).toBe(foundOfficeAreaCode);
      });
    });
  });
});
