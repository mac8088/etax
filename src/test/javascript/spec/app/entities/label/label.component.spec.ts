/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LabelComponent from '@/entities/label/label.vue';
import LabelClass from '@/entities/label/label.component';
import LabelService from '@/entities/label/label.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('Label Management Component', () => {
    let wrapper: Wrapper<LabelClass>;
    let comp: LabelClass;
    let labelServiceStub: SinonStubbedInstance<LabelService>;

    beforeEach(() => {
      labelServiceStub = sinon.createStubInstance<LabelService>(LabelService);
      labelServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LabelClass>(LabelComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          labelService: () => labelServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      labelServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLabels();
      await comp.$nextTick();

      // THEN
      expect(labelServiceStub.retrieve.called).toBeTruthy();
      expect(comp.labels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      labelServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLabel();
      await comp.$nextTick();

      // THEN
      expect(labelServiceStub.delete.called).toBeTruthy();
      expect(labelServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
