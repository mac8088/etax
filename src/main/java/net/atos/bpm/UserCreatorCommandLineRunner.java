package net.atos.bpm;

import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
import org.flowable.idm.api.Group;
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
		createUserIfNotExists("sysadmin", "123456");
		createUserIfNotExists("syscontr", "123456");

		createGroupIfNotExist("ADM001", "System Admin");
		createGroupIfNotExist("ADM002", "System Supervisor");
		createGroupIfNotExist("TECHSPT", "Technical support");

		idmIdentityService.createMembership("sysadmin", "ADM001");
		idmIdentityService.createMembership("sysadmin", "TECHSPT");
		idmIdentityService.createMembership("syscontr", "ADM002");

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

	protected void createGroupIfNotExist(String groupId, String groupName) {
		if (idmIdentityService.createGroupQuery().groupId(groupId).count() == 0) {
			Group group = idmIdentityService.newGroup(groupId);
			group.setName(groupName);
			idmIdentityService.saveGroup(group);
		}
	}

	// 创建用户方法
	static User creatUser(IdmIdentityService identityService, String id, String first, String last, String email,
			String passwd) {
		// 使用newUser方法创建User实例
		User user = identityService.newUser(id);
		// 设置用户的各个属性
		user.setFirstName(first);
		user.setLastName(last);
		user.setEmail(email);
		user.setPassword(passwd);
		
		// 使用saveUser方法保存用户
		identityService.saveUser(user);
		return identityService.createUserQuery().userId(id).singleResult();
	}

	// 将用户组数据保存到数据库中
	static Group createGroup(IdmIdentityService identityService, String id, String name, String type) {
		// 调用newGroup方法创建Group实例
		Group group = identityService.newGroup(id);
		group.setName(name);
		group.setType(type);
		
		// 使用saveGroup方法保存用户
		identityService.saveGroup(group);
		return identityService.createGroupQuery().groupId(id).singleResult();
	}
}