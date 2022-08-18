package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("dev")
@ConfigurationProperties(prefix = "db.datasource")
@EnableJpaRepositories(basePackages = "com.example.demo.dao", entityManagerFactoryRef = "devEntityManager", transactionManagerRef = "devTransactionManager")
public class DevDataSourceConfig extends DataSourceConfig implements InitializingBean {
	// @Autowired private Environment env; //env chua toan bo file .properties

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("dev profile: " + this.toString());
	}

	@Override
	public void setup() {
		System.out.println("set up Dev profile");
	}

	// must have 3 bean to configure database connection
	@Bean
	LocalContainerEntityManagerFactoryBean devEntityManager() {
		LocalContainerEntityManagerFactoryBean em = this.createdEM(this.devDataSource());
		// set properties, configure base on profile....

		return em;
	}

	@Bean
	DataSource devDataSource() {
		System.out.println(this.toString());
		return this.createNewDataSource();
	}

	@Bean
	PlatformTransactionManager devTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(devEntityManager().getObject());
		return transactionManager;
	}

}
