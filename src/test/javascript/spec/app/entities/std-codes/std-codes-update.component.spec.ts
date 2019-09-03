/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesUpdateComponent from '@/entities/std-codes/std-codes-update.vue';
import StdCodesClass from '@/entities/std-codes/std-codes-update.component';
import StdCodesService from '@/entities/std-codes/std-codes.service';

import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';

import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StdCodes Management Update Component', () => {
    let wrapper: Wrapper<StdCodesClass>;
    let comp: StdCodesClass;
    let stdCodesServiceStub: SinonStubbedInstance<StdCodesService>;

    beforeEach(() => {
      stdCodesServiceStub = sinon.createStubInstance<StdCodesService>(StdCodesService);

      wrapper = shallowMount<StdCodesClass>(StdCodesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stdCodesService: () => stdCodesServiceStub,

          stdCodesDescService: () => new StdCodesDescService(),

          stdCodesGroupService: () => new StdCodesGroupService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stdCodes = entity;
        stdCodesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stdCodes = entity;
        stdCodesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
