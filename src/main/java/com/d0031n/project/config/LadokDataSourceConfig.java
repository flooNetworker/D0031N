package com.d0031n.project.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
  basePackages = "com.d0031n.project.ladok",
  entityManagerFactoryRef = "ladokEntityManager",
  transactionManagerRef = "ladokTransactionManager"
)
public class LadokDataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.ladok")
  public DataSourceProperties ladokDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "ladokDataSource")
  public DataSource ladokDataSource() {
    return ladokDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean(name = "ladokEntityManager")
  public LocalContainerEntityManagerFactoryBean ladokEntityManager(
      EntityManagerFactoryBuilder builder,
      @Qualifier("ladokDataSource") DataSource ds
  ) {
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.hbm2ddl.auto", "create");
    props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    return builder.dataSource(ds)
                  .packages("com.d0031n.project.ladok")
                  .properties(props)
                  .persistenceUnit("ladok")
                  .build();
  }

  @Bean(name = "ladokTransactionManager")
  public PlatformTransactionManager ladokTransactionManager(
      @Qualifier("ladokEntityManager") LocalContainerEntityManagerFactoryBean emf) {
    return new JpaTransactionManager(emf.getObject());
  }
}