/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesGroupUpdateComponent from '@/entities/std-codes-group/std-codes-group-update.vue';
import StdCodesGroupClass from '@/entities/std-codes-group/std-codes-group-update.component';
import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';

import StdCodesService from '@/entities/std-codes/std-codes.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StdCodesGroup Management Update Component', () => {
    let wrapper: Wrapper<StdCodesGroupClass>;
    let comp: StdCodesGroupClass;
    let stdCodesGroupServiceStub: SinonStubbedInstance<StdCodesGroupService>;

    beforeEach(() => {
      stdCodesGroupServiceStub = sinon.createStubInstance<StdCodesGroupService>(StdCodesGroupService);

      wrapper = shallowMount<StdCodesGroupClass>(StdCodesGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stdCodesGroupService: () => stdCodesGroupServiceStub,

          stdCodesService: () => new StdCodesService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stdCodesGroup = entity;
        stdCodesGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stdCodesGroup = entity;
        stdCodesGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
