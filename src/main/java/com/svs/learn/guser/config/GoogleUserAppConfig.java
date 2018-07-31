package com.svs.learn.guser.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class GoogleUserAppConfig {

	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();
		hjva.setDatabasePlatform("org.hibernate.dialect.PostgreSQL94Dialect");
		emf.setJpaVendorAdapter(hjva);
		emf.setPackagesToScan("com.svs.learn.guser.dao");
		emf.setPersistenceUnitName("default");
		emf.getJpaPropertyMap().put("hibernate.jdbc.lob.non_contextual_creation", "true");

		emf.afterPropertiesSet();
		return emf.getObject();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jpaTransMgr = new JpaTransactionManager(emf);
		return jpaTransMgr;
	}

	@Configuration
	public static class MongoConfig extends AbstractMongoConfiguration {

		@Autowired
		private Environment env;

		@Override
		protected String getDatabaseName() {
			return env.getProperty("spring.data.mongodb.database");
		}

		@Override
		public MongoClient mongoClient() {

			MongoClientOptions clientOpts = MongoClientOptions.builder().build();

			MongoClient mongoClient = new MongoClient(
					new ServerAddress(env.getProperty("spring.data.mongodb.host"),
							Integer.parseInt(env.getProperty("spring.data.mongodb.port"))),
					MongoCredential.createCredential(env.getProperty("spring.data.mongodb.username"),
							env.getProperty("spring.data.mongodb.authentication-database"),
							env.getProperty("spring.data.mongodb.password").toCharArray()),
					clientOpts);

			return mongoClient;
		}

	}
}
