package net.atos.bpm;

import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
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
		createUserIfNotExists("system", "system");
		createUserIfNotExists("anonymoususer", "123456");
		createUserIfNotExists("admin", "admin");
		createUserIfNotExists("user", "user");

		if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_USER").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_USER");
			idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "user");
			idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "system");
		}

		if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_ADMIN").count() == 0) {
			Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_ADMIN");
			idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "admin");
			idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "system");
		}
	}

	protected void createUserIfNotExists(String username, String password) {
		if (idmIdentityService.createUserQuery().userId(username).count() == 0) {
			User user = idmIdentityService.newUser(username);
			user.setPassword(password);
			idmIdentityService.saveUser(user);
		}
	}
}