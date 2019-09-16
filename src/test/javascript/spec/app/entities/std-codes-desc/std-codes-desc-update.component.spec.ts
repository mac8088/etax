/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesDescUpdateComponent from '@/entities/std-codes-desc/std-codes-desc-update.vue';
import StdCodesDescClass from '@/entities/std-codes-desc/std-codes-desc-update.component';
import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';

import StdCodesService from '@/entities/std-codes/std-codes.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StdCodesDesc Management Update Component', () => {
    let wrapper: Wrapper<StdCodesDescClass>;
    let comp: StdCodesDescClass;
    let stdCodesDescServiceStub: SinonStubbedInstance<StdCodesDescService>;

    beforeEach(() => {
      stdCodesDescServiceStub = sinon.createStubInstance<StdCodesDescService>(StdCodesDescService);

      wrapper = shallowMount<StdCodesDescClass>(StdCodesDescUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stdCodesDescService: () => stdCodesDescServiceStub,

          stdCodesService: () => new StdCodesService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stdCodesDesc = entity;
        stdCodesDescServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesDescServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stdCodesDesc = entity;
        stdCodesDescServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesDescServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
