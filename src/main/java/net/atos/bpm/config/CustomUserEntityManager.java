package net.atos.bpm.config;

import java.util.List;
import java.util.Map;

import org.flowable.common.engine.impl.interceptor.Session;
import org.flowable.idm.api.PasswordEncoder;
import org.flowable.idm.api.PasswordSalt;
import org.flowable.idm.api.Picture;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityManager;

import net.atos.bpm.model.UserBean;
import net.atos.etax.repository.UserRepository;

public class CustomUserEntityManager implements UserEntityManager, Session {

	private UserRepository userDao;

	public CustomUserEntityManager(UserRepository userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserEntity create() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insert(UserEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insert(UserEntity entity, boolean fireCreateEvent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserEntity update(UserEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserEntity update(UserEntity entity, boolean fireUpdateEvent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(UserEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(UserEntity entity, boolean fireDeleteEvent) {
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
	public boolean isNewUser(User arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public User createNewUser(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserQuery createNewUserQuery() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateUser(User arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Picture getUserPicture(User arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserPicture(User arg0, Picture arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deletePicture(User arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean checkPassword(String arg0, String arg1, PasswordEncoder arg2, PasswordSalt arg3) {
		throw new UnsupportedOperationException();
	}

	private UserEntity userToFlowableUser(net.atos.etax.domain.User user) {
		UserEntity flowableUser = new UserBean();
		flowableUser.setId(user.getId().toString());
		flowableUser.setPassword(user.getPassword());
		flowableUser.setFirstName(user.getFirstName());
		flowableUser.setLastName(user.getLastName());
		flowableUser.setEmail(user.getEmail());
		return flowableUser;
	}

	@Override
	public UserEntity findById(String loginName) {
		net.atos.etax.domain.User user = userDao.findByLoginIgnoreCase(loginName);
		return userToFlowableUser(user);
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long findUserCountByNativeQuery(Map<String, Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findUsersByNativeQuery(Map<String, Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findUsersByPrivilegeId(String arg0) {
		throw new UnsupportedOperationException();
	}

}
