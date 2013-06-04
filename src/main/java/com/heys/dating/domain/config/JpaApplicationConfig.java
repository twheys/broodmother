package com.heys.dating.domain.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.heys.dating.domain")
// @EnableJpaRepositories("com.heys.dating.domain.user")
class JpaApplicationConfig // extends JpaRepositoryConfigExtension
{

	// @Bean
	// public AbstractEntityManagerFactoryBean entityManagerFactory() {
	// final LocalEntityManagerFactoryBean factory = new
	// LocalEntityManagerFactoryBean();
	// factory.setPersistenceUnitName("transactions-optional");
	// return factory;
	// }
	//
	// @Bean
	// public PlatformTransactionManager transactionManager() {
	// final JpaTransactionManager txManager = new JpaTransactionManager();
	// txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	// return txManager;
	// }
}
