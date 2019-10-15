package net.atos.bpm;

import static net.atos.bpm.util.FlowableUtil.createGroupIfNotExist;
import static net.atos.bpm.util.FlowableUtil.createUserIfNotExists;

import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author mac8088
 */
@Component
public class UserCreatorCommandLineRunner implements CommandLineRunner {

	protected final IdmIdentityService idmIdentityService;

	public UserCreatorCommandLineRunner(IdmIdentityService idmIdentityService) {
		this.idmIdentityService = idmIdentityService;
	}

	@Override
	public void run(String... args) {

		// some JHipster built-in users
		createUserIfNotExists(idmIdentityService, "system", "system");
		createUserIfNotExists(idmIdentityService, "anonymoususer", "123456");
		createUserIfNotExists(idmIdentityService, "admin", "admin");
		createUserIfNotExists(idmIdentityService, "user", "user");

		// some eTax users
		createUserIfNotExists(idmIdentityService, "sysadmin", "123456");
		createUserIfNotExists(idmIdentityService, "syscontr", "123456");

		// some eTax user-groups
		createGroupIfNotExist(idmIdentityService, "ADM001", "System Admin");
		createGroupIfNotExist(idmIdentityService, "ADM002", "System Supervisor");
		createGroupIfNotExist(idmIdentityService, "TECHSPT", "Technical support");
		
		// Maintain membership between users and groups
		if (idmIdentityService.createUserQuery().memberOfGroup("ADM001").count() == 0) {
			idmIdentityService.createMembership("sysadmin", "ADM001");
		}

		if (idmIdentityService.createUserQuery().memberOfGroup("TECHSPT").count() == 0) {
			idmIdentityService.createMembership("sysadmin", "TECHSPT");
		}

		if (idmIdentityService.createUserQuery().memberOfGroup("ADM002").count() == 0) {
			idmIdentityService.createMembership("syscontr", "ADM002");
		}

		// setup privilege with groups: Maintain Office
		if (idmIdentityService.createPrivilegeQuery().privilegeName("PRIV_ADM001").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("PRIV_ADM001");
			idmIdentityService.addGroupPrivilegeMapping(restPrivilege.getId(), "ADM001");
		}

		// setup privilege with groups: Maintain User
		if (idmIdentityService.createPrivilegeQuery().privilegeName("PRIV_ADM002").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("PRIV_ADM002");
			idmIdentityService.addGroupPrivilegeMapping(restPrivilege.getId(), "ADM001");
		}

		// setup privilege with groups: Approve User Profile
		if (idmIdentityService.createPrivilegeQuery().privilegeName("PRIV_ADM003").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("PRIV_ADM003");
			idmIdentityService.addGroupPrivilegeMapping(restPrivilege.getId(), "ADM002");
		}

		// setup privilege with groups: Maintain Public Holidays
		if (idmIdentityService.createPrivilegeQuery().privilegeName("PRIV_ADM004").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("PRIV_ADM004");
			idmIdentityService.addGroupPrivilegeMapping(restPrivilege.getId(), "ADM001");
		}

		// setup privilege with groups: Maintain Exchange Rates
		if (idmIdentityService.createPrivilegeQuery().privilegeName("PRIV_ADM005").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("PRIV_ADM005");
			idmIdentityService.addGroupPrivilegeMapping(restPrivilege.getId(), "ADM001");
		}
	}

}