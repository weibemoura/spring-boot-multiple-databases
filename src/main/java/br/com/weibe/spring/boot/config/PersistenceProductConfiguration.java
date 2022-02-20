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
		basePackages = "br.com.weibe.spring.boot.product", 
		entityManagerFactoryRef = "productEntityManager", 
		transactionManagerRef = "productTransactionManager"
)
public class PersistenceProductConfiguration {

	@Autowired
	private Environment env;

	@Primary
	@Bean("productEntityManager")
	public LocalContainerEntityManagerFactoryBean productEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("productDb");
		em.setDataSource(productDataSource());
		em.setPackagesToScan(new String[] { "br.com.weibe.spring.boot.product" });

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
	@Bean("productDataSource")
	@ConfigurationProperties(prefix="product-db.datasource")
	public DataSource productDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean("productTransactionManager")
	public PlatformTransactionManager productTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(productEntityManager().getObject());
		return transactionManager;
	}
}
