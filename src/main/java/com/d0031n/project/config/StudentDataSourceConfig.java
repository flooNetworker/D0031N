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
  basePackages = "com.d0031n.project.student_its",
  entityManagerFactoryRef = "studentEntityManager",
  transactionManagerRef = "studentTransactionManager"
)
public class StudentDataSourceConfig {
  @Bean
  @ConfigurationProperties("spring.datasource.student")
  public DataSourceProperties studentDataSourceProperties() { return new DataSourceProperties(); }

  @Bean(name="studentDataSource")
  public DataSource studentDataSource() { return studentDataSourceProperties().initializeDataSourceBuilder().build(); }

  @Bean(name="studentEntityManager")
  public LocalContainerEntityManagerFactoryBean studentEntityManager(
      EntityManagerFactoryBuilder b, 
      @Qualifier("studentDataSource") DataSource studentDataSource // <-- ADD @Qualifier
  ) {
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.hbm2ddl.auto", "create");
    props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

    return b
        .dataSource(studentDataSource)
        .packages("com.d0031n.project.student_its")
        .properties(props)
        .persistenceUnit("student")
        .build();
  }

  @Bean(name="studentTransactionManager")
  public PlatformTransactionManager studentTransactionManager(@Qualifier("studentEntityManager") LocalContainerEntityManagerFactoryBean emf){
    return new JpaTransactionManager(emf.getObject());
  }
}