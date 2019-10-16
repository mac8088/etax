/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PrivilegeDetailComponent from '@/entities/privilege/privilege-details.vue';
import PrivilegeClass from '@/entities/privilege/privilege-details.component';
import PrivilegeService from '@/entities/privilege/privilege.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Privilege Management Detail Component', () => {
    let wrapper: Wrapper<PrivilegeClass>;
    let comp: PrivilegeClass;
    let privilegeServiceStub: SinonStubbedInstance<PrivilegeService>;

    beforeEach(() => {
      privilegeServiceStub = sinon.createStubInstance<PrivilegeService>(PrivilegeService);

      wrapper = shallowMount<PrivilegeClass>(PrivilegeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { privilegeService: () => privilegeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrivilege = { id: 123 };
        privilegeServiceStub.find.resolves(foundPrivilege);

        // WHEN
        comp.retrievePrivilege(123);
        await comp.$nextTick();

        // THEN
        expect(comp.privilege).toBe(foundPrivilege);
      });
    });
  });
});
