package net.atos.bpm.config;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.Session;
import org.flowable.common.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.atos.etax.repository.UserRepository;

@Component(value = "customUserManagerFactory")
public class CustomUserManagerFactory implements SessionFactory {

	@Autowired
	private UserRepository userDao;

	@Override
	public Class<?> getSessionType() {
		return CustomUserEntityManager.class;
	}

	@Override
	public Session openSession(CommandContext commandContext) {
		return new CustomUserEntityManager(userDao);
	}

}
