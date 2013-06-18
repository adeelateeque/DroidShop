package com.droidshop.api;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components
 * like a {@link DataSource}, a {@link EntityManagerFactory} and a
 * {@link PlatformTransactionManager}.
 */
@Configuration
@ComponentScan(includeFilters = @Filter(Service.class), useDefaultFilters = false)
@EnableAsync
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableTransactionManagement
class ApplicationConfig
{
	
}
