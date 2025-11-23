package com.d0031n.project.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.d0031n.project.canvas",
        entityManagerFactoryRef = "canvasEntityManager",
        transactionManagerRef = "canvasTransactionManager"
)
public class CanvasDataSourceConfig {

    // Use the default "spring.datasource.*" properties
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties canvasDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "canvasDataSource")
    public DataSource canvasDataSource() {
        return canvasDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "canvasEntityManager")
    public LocalContainerEntityManagerFactoryBean canvasEntityManager(EntityManagerFactoryBuilder builder,
                                                                        @Qualifier("canvasDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.d0031n.project.canvas")
                .persistenceUnit("canvas")
                .build();
    }

    @Primary
    @Bean(name = "canvasTransactionManager")
    public PlatformTransactionManager canvasTransactionManager(
            @Qualifier("canvasEntityManager") LocalContainerEntityManagerFactoryBean canvasEntityManager) {
        return new JpaTransactionManager(canvasEntityManager.getObject());
    }
}