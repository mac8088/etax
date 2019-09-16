package net.atos.bpm.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 为解决flowable图片中的中文乱码
 *
 * @author Mac
 * @date 2019/09/02
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	@Override
	public void configure(SpringProcessEngineConfiguration engineConfiguration) {
		engineConfiguration.setActivityFontName("宋体");
		engineConfiguration.setLabelFontName("宋体");
		engineConfiguration.setAnnotationFontName("宋体");

		// engineConfiguration.setDisableIdmEngine(true);
		// engineConfiguration.setDatabaseSchemaUpdate("true");
	}
}
