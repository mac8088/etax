/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesGroupPropUpdateComponent from '@/entities/std-codes-group-prop/std-codes-group-prop-update.vue';
import StdCodesGroupPropClass from '@/entities/std-codes-group-prop/std-codes-group-prop-update.component';
import StdCodesGroupPropService from '@/entities/std-codes-group-prop/std-codes-group-prop.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StdCodesGroupProp Management Update Component', () => {
    let wrapper: Wrapper<StdCodesGroupPropClass>;
    let comp: StdCodesGroupPropClass;
    let stdCodesGroupPropServiceStub: SinonStubbedInstance<StdCodesGroupPropService>;

    beforeEach(() => {
      stdCodesGroupPropServiceStub = sinon.createStubInstance<StdCodesGroupPropService>(StdCodesGroupPropService);

      wrapper = shallowMount<StdCodesGroupPropClass>(StdCodesGroupPropUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stdCodesGroupPropService: () => stdCodesGroupPropServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stdCodesGroupProp = entity;
        stdCodesGroupPropServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesGroupPropServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stdCodesGroupProp = entity;
        stdCodesGroupPropServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesGroupPropServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
