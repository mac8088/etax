/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeRelationshipUpdateComponent from '@/entities/office-relationship/office-relationship-update.vue';
import OfficeRelationshipClass from '@/entities/office-relationship/office-relationship-update.component';
import OfficeRelationshipService from '@/entities/office-relationship/office-relationship.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('OfficeRelationship Management Update Component', () => {
    let wrapper: Wrapper<OfficeRelationshipClass>;
    let comp: OfficeRelationshipClass;
    let officeRelationshipServiceStub: SinonStubbedInstance<OfficeRelationshipService>;

    beforeEach(() => {
      officeRelationshipServiceStub = sinon.createStubInstance<OfficeRelationshipService>(OfficeRelationshipService);

      wrapper = shallowMount<OfficeRelationshipClass>(OfficeRelationshipUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officeRelationshipService: () => officeRelationshipServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.officeRelationship = entity;
        officeRelationshipServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeRelationshipServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.officeRelationship = entity;
        officeRelationshipServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeRelationshipServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
