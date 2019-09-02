/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeTaxFuncUpdateComponent from '@/entities/office-tax-func/office-tax-func-update.vue';
import OfficeTaxFuncClass from '@/entities/office-tax-func/office-tax-func-update.component';
import OfficeTaxFuncService from '@/entities/office-tax-func/office-tax-func.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('OfficeTaxFunc Management Update Component', () => {
    let wrapper: Wrapper<OfficeTaxFuncClass>;
    let comp: OfficeTaxFuncClass;
    let officeTaxFuncServiceStub: SinonStubbedInstance<OfficeTaxFuncService>;

    beforeEach(() => {
      officeTaxFuncServiceStub = sinon.createStubInstance<OfficeTaxFuncService>(OfficeTaxFuncService);

      wrapper = shallowMount<OfficeTaxFuncClass>(OfficeTaxFuncUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officeTaxFuncService: () => officeTaxFuncServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.officeTaxFunc = entity;
        officeTaxFuncServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeTaxFuncServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.officeTaxFunc = entity;
        officeTaxFuncServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officeTaxFuncServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
