/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ResourceDetailComponent from '@/entities/resource/resource-details.vue';
import ResourceClass from '@/entities/resource/resource-details.component';
import ResourceService from '@/entities/resource/resource.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Resource Management Detail Component', () => {
    let wrapper: Wrapper<ResourceClass>;
    let comp: ResourceClass;
    let resourceServiceStub: SinonStubbedInstance<ResourceService>;

    beforeEach(() => {
      resourceServiceStub = sinon.createStubInstance<ResourceService>(ResourceService);

      wrapper = shallowMount<ResourceClass>(ResourceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { resourceService: () => resourceServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundResource = { id: 123 };
        resourceServiceStub.find.resolves(foundResource);

        // WHEN
        comp.retrieveResource(123);
        await comp.$nextTick();

        // THEN
        expect(comp.resource).toBe(foundResource);
      });
    });
  });
});
