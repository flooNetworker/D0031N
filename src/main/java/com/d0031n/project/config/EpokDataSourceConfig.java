package com.d0031n.project.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
  basePackages = "com.d0031n.project.epok",
  entityManagerFactoryRef = "epokEntityManager",
  transactionManagerRef = "epokTransactionManager"
)
public class EpokDataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.epok")
  public DataSourceProperties epokDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "epokDataSource")
  public DataSource epokDataSource() {
    return epokDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean(name = "epokEntityManager")
  public LocalContainerEntityManagerFactoryBean epokEntityManager(EntityManagerFactoryBuilder builder,
      @Qualifier("epokDataSource") DataSource ds) {
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.hbm2ddl.auto", "create");
    props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    return builder.dataSource(ds)
                  .packages("com.d0031n.project.epok")
                  .properties(props)
                  .persistenceUnit("epok")
                  .build();
  }

  @Bean(name = "epokTransactionManager")
  public PlatformTransactionManager epokTransactionManager(
      @Qualifier("epokEntityManager") LocalContainerEntityManagerFactoryBean emf) {
    return new JpaTransactionManager(emf.getObject());
  }
}