package com.example.demo.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.Data;

@Data
public abstract class DataSourceConfig implements InitializingBean {

	private String driver;
	private String url;
	private String username;
	private String password;

	private boolean showsql;
	private String ddlauto;
	private String dialect;

	public abstract void setup();

	public String toString() {
		return this.getDriver() + ": " + this.getUrl() + " - " + this.getUsername() + "/" + this.getPassword();
	}

	public DataSource createNewDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(this.getDriver());
		dataSource.setUrl(this.getUrl());
		dataSource.setUsername(this.getUsername());
		dataSource.setPassword(this.getPassword());
		return dataSource;
	}

	public LocalContainerEntityManagerFactoryBean createdEM(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "com.example.demo.entity" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		// set up properties
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", this.getDialect()); // must have
		em.setJpaPropertyMap(properties);

		return em;
	}

}
