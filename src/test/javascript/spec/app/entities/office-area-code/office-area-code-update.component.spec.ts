/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeAreaCodeUpdateComponent from '@/entities/office-area-code/office-area-code-update.vue';
import OfficeAreaCodeClass from '@/entities/office-area-code/office-area-code-update.component';
import OfficeAreaCodeService from '@/entities/office-area-code/office-area-code.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('OfficeAreaCode Management Update Component', () => {
    let wrapper: Wrapper<OfficeAreaCodeClass>;
    let comp: OfficeAreaCodeClass;
    let officeAreaCodeServiceStub: SinonStubbedInstance<OfficeAreaCodeService>;

    beforeEach(() => {
      officeAreaCodeServiceStub = sinon.createStubInstance<OfficeAreaCodeService>(OfficeAreaCodeService);

      wrapper = shallowMount<OfficeAreaCodeClass>(OfficeAreaCodeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officeAreaCodeService: () => officeAreaCodeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.officeAreaCode = entity;
        officeAreaCodeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeAreaCodeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.officeAreaCode = entity;
        officeAreaCodeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeAreaCodeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
