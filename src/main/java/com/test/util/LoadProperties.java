package com.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/config.properties")
public class LoadProperties {

	@Autowired
	Environment environment;

	/**
	 * 通用
	 */
	public String getProperty(String key) {
		return environment.getProperty(key);
	}
	
	@Value("${spring.datasource.url}")
	public String url;

	@Value("${spring.datasource.username}")
	public String username;

	@Value("${spring.datasource.password}")
	public String password;

	@Value("${spring.datasource.driver-class-name}")
	public String driver;
}
