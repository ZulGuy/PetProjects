package com.studying.backendservice.configurations;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {

  @Autowired
  private final DataSource dataSource;

  @Autowired
  public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    Connection connection = dataSource.getConnection();
    connection.setSchema(tenantIdentifier);
    return connection;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  @UnknownKeyFor
  @NonNull
  @Initialized
  public boolean isUnwrappableAs(
      @UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized ?> aClass) {
    return false;
  }

  @Override
  public <T> T unwrap(
      @UnknownKeyFor @NonNull @Initialized Class<T> aClass) {
    return null;
  }
}
