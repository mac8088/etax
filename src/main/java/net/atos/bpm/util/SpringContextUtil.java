package net.atos.bpm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Utility class for getting bean from context.
 */
public class SpringContextUtil {
	
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
}
