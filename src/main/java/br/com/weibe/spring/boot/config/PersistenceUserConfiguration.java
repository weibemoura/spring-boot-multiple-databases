package br.com.weibe.spring.boot.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages = "br.com.weibe.spring.boot.user", 
		entityManagerFactoryRef = "userEntityManager", 
		transactionManagerRef = "userTransactionManager"
)
public class PersistenceUserConfiguration {

	@Autowired
	private Environment env;

	@Primary
	@Bean("userEntityManager")
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("userDb");
		em.setDataSource(userDataSource());
		em.setPackagesToScan(new String[] { "br.com.weibe.spring.boot.user" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql", "false"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
		properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean("userDataSource")
	@ConfigurationProperties(prefix="user-db.datasource")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean("userTransactionManager")
	public PlatformTransactionManager userTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
		return transactionManager;
	}
}
