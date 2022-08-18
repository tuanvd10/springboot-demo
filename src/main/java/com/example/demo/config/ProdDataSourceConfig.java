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
@Profile("production")
@ConfigurationProperties(prefix = "db.datasource")
@EnableJpaRepositories(basePackages = "com.example.demo.dao", entityManagerFactoryRef = "productionEntityManager", transactionManagerRef = "productionTransactionManager")
public class ProdDataSourceConfig extends DataSourceConfig implements InitializingBean {
	// @Autowired private Environment env; //env chua toan bo file .properties

	@Override
	public void setup() {
		System.out.println("prod profile: " + this.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	// must have 3 bean to configure database connection
	@Bean
	LocalContainerEntityManagerFactoryBean productionEntityManager() {
		LocalContainerEntityManagerFactoryBean em = this.createdEM(this.productionDataSource());
		// set properties, configure base on profile....
		return em;
	}

	@Bean
	DataSource productionDataSource() {
		System.out.println();
		return this.createNewDataSource();
	}

	@Bean
	PlatformTransactionManager productionTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(productionEntityManager().getObject());
		return transactionManager;
	}

}
