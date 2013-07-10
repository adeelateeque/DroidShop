package com.droidshop.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components
 * like a {@link DataSource}, a {@link EntityManagerFactory} and a
 * {@link PlatformTransactionManager}.
 */
@Configuration
@ComponentScan
@EnableAsync
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ApplicationConfig
{
	/**
	 * Sets up a {@link EntityManagerFactory} to use Hibernate. Activates picking
	 * up entities from the project's base package.
	 * 
	 * @return
	 */
	@Bean
	@Autowired
	public EntityManagerFactory entityManagerFactory(DataSource dataSource)
	{
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		vendorAdapter.setDatabase(Database.MYSQL);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.droidshop.api");
		factory.setDataSource(dataSource);

		Properties properties = new Properties();
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");

		factory.setJpaProperties(properties);

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public DataSource dataSource()
	{
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl(getDatabaseUrl());
		dataSource.setIdleConnectionTestPeriodInMinutes(60);
		dataSource.setIdleMaxAgeInMinutes(420);
		dataSource.setMaxConnectionsPerPartition(10);
		dataSource.setMinConnectionsPerPartition(5);
		dataSource.setPartitionCount(3);
		dataSource.setAcquireIncrement(5);
		dataSource.setStatementsCacheSize(100);
		dataSource.setReleaseHelperThreads(3);

		return dataSource;
	}

	@Bean
	public PropertyPlaceholderConfigurer propertyConfigurer()
	{
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource("application.properties"));

		return configurer;
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
	{
		JpaTransactionManager txManager = new JpaTransactionManager();
		JpaDialect jpaDialect = new HibernateJpaDialect();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setJpaDialect(jpaDialect);
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator()
	{
		return new HibernateExceptionTranslator();
	}
	
	private String getDatabaseUrl()
	{
		String dbName = System.getProperty("RDS_DB_NAME"); 
		String userName = System.getProperty("RDS_USERNAME"); 
		String password = System.getProperty("RDS_PASSWORD"); 
		String hostname = System.getProperty("RDS_HOSTNAME");
		String port = System.getProperty("RDS_PORT");
		
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		System.out.println("JDBC URL : "+url);
		
		return url;
	}
}