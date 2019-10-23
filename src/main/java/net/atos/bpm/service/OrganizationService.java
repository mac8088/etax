package net.atos.bpm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.atos.etax.domain.Authority;
import net.atos.etax.domain.User;
import net.atos.etax.repository.AuthorityRepository;
import net.atos.etax.repository.UserRepository;

@Component
public class OrganizationService {

	private final Logger log = LoggerFactory.getLogger(OrganizationService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	public List<User> getUserByRole(String name) {
		//TODO
		return null;
	}

	public User getUserByUserName(String loginName) {
		return this.userRepository.findByLoginIgnoreCase(loginName);
	}

	public User findAssignorOrOwner(Long assignorId) {
		return userRepository.getOne(assignorId);
	}

	public List<User> getCandidateUsers(TaskEntity taskEntity) {
		List<IdentityLinkEntity> links = taskEntity.getIdentityLinks();
		List<User> users = new ArrayList<User>();
		for (IdentityLinkEntity link : links) {
			String type = link.getType();
			String groupId = link.getGroupId();
			String userId = link.getUserId();
			log.info(type + "-" + userId + "-" + groupId + "-" + link.getTaskId());

			if (StringUtils.isNotEmpty(groupId)) {
				List<User> groupUsers = getUserByRole(groupId);
				if (groupUsers != null) {
					users.addAll(groupUsers);
				}
			}

			if (StringUtils.isNotEmpty(userId)) {
				User user = getUserByUserName(userId);
				if (user != null && !users.contains(user)) {
					boolean flag = true;
					for (User u : users) {
						if (u.getId().equals(user.getId())) {
							flag = false;
						}
					}
					if (flag) {
						users.add(user);
					}
				}
			}
		}
		return users;
	}
	
	public List<Authority> getCandidateProfiles(String login) {
		return authorityRepository.findAuthorityByLoginName(login);
	}

}
