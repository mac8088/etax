/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficeHolidayComponent from '@/entities/office-holiday/office-holiday.vue';
import OfficeHolidayClass from '@/entities/office-holiday/office-holiday.component';
import OfficeHolidayService from '@/entities/office-holiday/office-holiday.service';

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
  describe('OfficeHoliday Management Component', () => {
    let wrapper: Wrapper<OfficeHolidayClass>;
    let comp: OfficeHolidayClass;
    let officeHolidayServiceStub: SinonStubbedInstance<OfficeHolidayService>;

    beforeEach(() => {
      officeHolidayServiceStub = sinon.createStubInstance<OfficeHolidayService>(OfficeHolidayService);
      officeHolidayServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OfficeHolidayClass>(OfficeHolidayComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          officeHolidayService: () => officeHolidayServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      officeHolidayServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOfficeHolidays();
      await comp.$nextTick();

      // THEN
      expect(officeHolidayServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      officeHolidayServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(officeHolidayServiceStub.retrieve.called).toBeTruthy();
      expect(comp.officeHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      officeHolidayServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(officeHolidayServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      officeHolidayServiceStub.retrieve.reset();
      officeHolidayServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(officeHolidayServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.officeHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      officeHolidayServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOfficeHoliday();
      await comp.$nextTick();

      // THEN
      expect(officeHolidayServiceStub.delete.called).toBeTruthy();
      expect(officeHolidayServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
