package net.atos.bpm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import net.atos.bpm.model.GroupBean;

import org.flowable.common.engine.impl.interceptor.Session;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManager;

import net.atos.etax.domain.Authority;
import net.atos.etax.domain.User;
import net.atos.etax.repository.AuthorityRepository;
import net.atos.etax.repository.UserRepository;

public class CustomGroupEntityManager implements GroupEntityManager, Session {

	private AuthorityRepository profileDao;

	private UserRepository userDao;

	public CustomGroupEntityManager(AuthorityRepository profileDao, UserRepository userDao) {
		this.profileDao = profileDao;
		this.userDao = userDao;
	}

	@Override
	public GroupEntity create() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insert(GroupEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insert(GroupEntity entity, boolean fireCreateEvent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GroupEntity update(GroupEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(GroupEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(GroupEntity entity, boolean fireDeleteEvent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void flush() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNewGroup(Group arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Group createNewGroup(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GroupQuery createNewGroupQuery() {
		throw new UnsupportedOperationException();
	}

	private GroupEntity profileToFlowableGroup(net.atos.etax.domain.Authority profile) {
		GroupBean group = new GroupBean();
		group.setId(profile.getName());
		group.setName(profile.getDescription());
		group.setType(profile.getCstd_module());
		return group;
	}

	@Override
	public GroupEntity findById(String profileName) {
		net.atos.etax.domain.Authority profile = this.profileDao.getOne(profileName);
		return profileToFlowableGroup(profile);
	}

	@Override
	public List<Group> findGroupsByUser(String loginName) {
		Optional<User> opt = userDao.findOneWithAuthoritiesByLogin(loginName);
		List<Group> groupList = new ArrayList<>();
		if (opt.get() != null) {
			User user = opt.get();
			Set<Authority> authSet = user.getAuthorities();
			for (Authority tmp : authSet) {
				groupList.add(profileToFlowableGroup(tmp));
			}
		}
		return groupList;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Group> findGroupsByPrivilegeId(String arg0) {
		throw new UnsupportedOperationException();
	}

}
