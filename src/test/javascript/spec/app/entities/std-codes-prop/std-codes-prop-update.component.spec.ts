/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StdCodesPropUpdateComponent from '@/entities/std-codes-prop/std-codes-prop-update.vue';
import StdCodesPropClass from '@/entities/std-codes-prop/std-codes-prop-update.component';
import StdCodesPropService from '@/entities/std-codes-prop/std-codes-prop.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StdCodesProp Management Update Component', () => {
    let wrapper: Wrapper<StdCodesPropClass>;
    let comp: StdCodesPropClass;
    let stdCodesPropServiceStub: SinonStubbedInstance<StdCodesPropService>;

    beforeEach(() => {
      stdCodesPropServiceStub = sinon.createStubInstance<StdCodesPropService>(StdCodesPropService);

      wrapper = shallowMount<StdCodesPropClass>(StdCodesPropUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stdCodesPropService: () => stdCodesPropServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stdCodesProp = entity;
        stdCodesPropServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesPropServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stdCodesProp = entity;
        stdCodesPropServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdCodesPropServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
