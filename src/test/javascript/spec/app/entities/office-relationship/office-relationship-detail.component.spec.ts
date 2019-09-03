/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficeRelationshipDetailComponent from '@/entities/office-relationship/office-relationship-details.vue';
import OfficeRelationshipClass from '@/entities/office-relationship/office-relationship-details.component';
import OfficeRelationshipService from '@/entities/office-relationship/office-relationship.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OfficeRelationship Management Detail Component', () => {
    let wrapper: Wrapper<OfficeRelationshipClass>;
    let comp: OfficeRelationshipClass;
    let officeRelationshipServiceStub: SinonStubbedInstance<OfficeRelationshipService>;

    beforeEach(() => {
      officeRelationshipServiceStub = sinon.createStubInstance<OfficeRelationshipService>(OfficeRelationshipService);

      wrapper = shallowMount<OfficeRelationshipClass>(OfficeRelationshipDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officeRelationshipService: () => officeRelationshipServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOfficeRelationship = { id: 123 };
        officeRelationshipServiceStub.find.resolves(foundOfficeRelationship);

        // WHEN
        comp.retrieveOfficeRelationship(123);
        await comp.$nextTick();

        // THEN
        expect(comp.officeRelationship).toBe(foundOfficeRelationship);
      });
    });
  });
});
