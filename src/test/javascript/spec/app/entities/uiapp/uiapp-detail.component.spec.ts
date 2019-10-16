/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UiappDetailComponent from '@/entities/uiapp/uiapp-details.vue';
import UiappClass from '@/entities/uiapp/uiapp-details.component';
import UiappService from '@/entities/uiapp/uiapp.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Uiapp Management Detail Component', () => {
    let wrapper: Wrapper<UiappClass>;
    let comp: UiappClass;
    let uiappServiceStub: SinonStubbedInstance<UiappService>;

    beforeEach(() => {
      uiappServiceStub = sinon.createStubInstance<UiappService>(UiappService);

      wrapper = shallowMount<UiappClass>(UiappDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { uiappService: () => uiappServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUiapp = { id: 123 };
        uiappServiceStub.find.resolves(foundUiapp);

        // WHEN
        comp.retrieveUiapp(123);
        await comp.$nextTick();

        // THEN
        expect(comp.uiapp).toBe(foundUiapp);
      });
    });
  });
});
