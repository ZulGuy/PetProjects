package com.studying.backendservice.configurations;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@EnableJpaRepositories(
    basePackages = "com.studying.backendservice.repositories",
    entityManagerFactoryRef = "entityManagerFactory"
)
@Configuration
public class JpaMultiTenantConfig {

  private final DataSource dataSource;
  private final MultiTenantConnectionProvider connectionProvider;
  private final CurrentTenantIdentifierResolver tenantIdentifierResolver;

  @Autowired
  public JpaMultiTenantConfig(DataSource dataSource,
      MultiTenantConnectionProvider connectionProvider,
      CurrentTenantIdentifierResolver tenantIdentifierResolver) {
    this.dataSource = dataSource;
    this.connectionProvider = connectionProvider;
    this.tenantIdentifierResolver = tenantIdentifierResolver;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.multi_tenant_connection_provider", connectionProvider);
    props.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);
    props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    props.put("hibernate.hbm2ddl.auto", "none");
    props.put("hibernate.show_sql", true);
    props.put("hibernate.format_sql", true);

    return builder
        .dataSource(dataSource)
        .packages("com.studying.backendservice.models")
        .persistenceUnit("default")
        .properties(props)
        .build();
  }
}
