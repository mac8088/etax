/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UiappUpdateComponent from '@/entities/uiapp/uiapp-update.vue';
import UiappClass from '@/entities/uiapp/uiapp-update.component';
import UiappService from '@/entities/uiapp/uiapp.service';

import ResourceService from '@/entities/resource/resource.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Uiapp Management Update Component', () => {
    let wrapper: Wrapper<UiappClass>;
    let comp: UiappClass;
    let uiappServiceStub: SinonStubbedInstance<UiappService>;

    beforeEach(() => {
      uiappServiceStub = sinon.createStubInstance<UiappService>(UiappService);

      wrapper = shallowMount<UiappClass>(UiappUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          uiappService: () => uiappServiceStub,

          resourceService: () => new ResourceService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.uiapp = entity;
        uiappServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(uiappServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.uiapp = entity;
        uiappServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(uiappServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
