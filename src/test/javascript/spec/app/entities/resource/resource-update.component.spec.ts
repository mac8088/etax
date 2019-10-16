/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ResourceUpdateComponent from '@/entities/resource/resource-update.vue';
import ResourceClass from '@/entities/resource/resource-update.component';
import ResourceService from '@/entities/resource/resource.service';

import UiappService from '@/entities/uiapp/uiapp.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Resource Management Update Component', () => {
    let wrapper: Wrapper<ResourceClass>;
    let comp: ResourceClass;
    let resourceServiceStub: SinonStubbedInstance<ResourceService>;

    beforeEach(() => {
      resourceServiceStub = sinon.createStubInstance<ResourceService>(ResourceService);

      wrapper = shallowMount<ResourceClass>(ResourceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          resourceService: () => resourceServiceStub,

          uiappService: () => new UiappService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.resource = entity;
        resourceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resource = entity;
        resourceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
