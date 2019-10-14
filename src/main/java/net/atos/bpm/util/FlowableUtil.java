package net.atos.bpm.util;

import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.User;

public class FlowableUtil {

	public static void createUserIfNotExists(IdmIdentityService identityService, String username, String password) {
		if (identityService.createUserQuery().userId(username).count() == 0) {
			User user = identityService.newUser(username);
			user.setPassword(password);
			identityService.saveUser(user);
		}
	}

	public static void createGroupIfNotExist(IdmIdentityService identityService, String groupId, String groupName) {
		if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
			Group group = identityService.newGroup(groupId);
			group.setName(groupName);
			identityService.saveGroup(group);
		}
	}

	// 创建用户方法
	public static User creatUser(IdmIdentityService identityService, String id, String first, String last, String email,
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
	public static Group createGroup(IdmIdentityService identityService, String id, String name, String type) {
		// 调用newGroup方法创建Group实例
		Group group = identityService.newGroup(id);
		group.setName(name);
		group.setType(type);

		// 使用saveGroup方法保存用户
		identityService.saveGroup(group);
		return identityService.createGroupQuery().groupId(id).singleResult();
	}
}
