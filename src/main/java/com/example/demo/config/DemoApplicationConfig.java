package com.example.demo.config;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
//@Configuration
@ConfigurationProperties(prefix = "test")
@PropertySource("classpath:test_config.properties")
public class DemoApplicationConfig implements InitializingBean {
	// @Value("${test.age}")
	private String username;
	private String age;
	private ArrayList<Integer> scores;

	@Autowired
	private Environment env; // env chua toan bo tat ca cac file .properties

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		/*
		 * System.out.println("DemoApplicationConfig bean created: " +
		 * this.getUsername() + this.getAge() + env.getProperty("test.scores") +
		 * env.getProperty("server.port"));
		 * 
		 * dataSourceConfig.setup(); System.out.println(dataSourceConfig.toString());
		 */
	}
}
