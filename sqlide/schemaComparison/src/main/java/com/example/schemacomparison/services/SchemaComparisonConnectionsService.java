package com.example.schemacomparison.services;

import com.example.sqlide.models.UserConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SchemaComparisonConnectionsService {
  private UserConnection userConnection;

  public SchemaComparisonConnectionsService(UserConnection userConnection) {
    this.userConnection = userConnection;
  }


  public JdbcTemplate openConnection() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(userConnection.getDriver());
    dataSource.setUrl(userConnection.getUrl());
    dataSource.setUsername(userConnection.getUsername());
    dataSource.setPassword(userConnection.getPassword());

    return new JdbcTemplate(dataSource);
  }

}
