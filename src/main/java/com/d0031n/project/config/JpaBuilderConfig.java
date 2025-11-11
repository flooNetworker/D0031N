package com.d0031n.project.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Collections;
import java.util.Map;

@Configuration
public class JpaBuilderConfig {

    @Bean
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter(JpaProperties jpaProperties) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            HibernateJpaVendorAdapter jpaVendorAdapter,
            JpaProperties jpaProperties) {
        
        Map<String, String> properties = Collections.emptyMap();

        return new EntityManagerFactoryBuilder(
            jpaVendorAdapter, 
            properties, 
            null 
        );
    }
}