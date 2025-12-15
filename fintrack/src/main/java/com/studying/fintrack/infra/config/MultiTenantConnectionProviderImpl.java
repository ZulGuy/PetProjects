package com.studying.fintrack.infra.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl extends
    AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private static final Logger logger = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);
  private final DataSource dataSource;

  public MultiTenantConnectionProviderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected DataSource selectAnyDataSource() {
    return dataSource;
  }

  @Override
  protected DataSource selectDataSource(Object tenantIdentifier) {
    return dataSource;
  }

  @Override
  public Connection getConnection(Object tenantIdentifier) throws SQLException {
    String tenantId = tenantIdentifier != null ? tenantIdentifier.toString() : "public";
    logger.info("Acquiring connection for tenant: {}", tenantId);
    Connection connection = getAnyConnection();
    try(Statement statement = connection.createStatement()) {
      statement.execute(String.format("SET search_path TO %s;", tenantId));
    }
    return connection;
  }

  @Override
  public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
    logger.info("Releasing connection for tenant: {}", tenantIdentifier);
    try (Statement statement = connection.createStatement()) {
      statement.execute("SET search_path TO public;");
    }
    releaseAnyConnection(connection);
  }
}
