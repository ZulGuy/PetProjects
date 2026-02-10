package com.example.sqlide.services;

import com.example.sqlide.interfaces.DatabaseObserver;
import com.example.sqlide.models.UserConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseConnectionsService implements DatabaseObserver {

  private UserConnection userConnection;
  private static DatabaseConnectionsService instance;

  private DatabaseConnectionsService(UserConnection userConnection) {
    this.userConnection = userConnection;
  }

  public static DatabaseConnectionsService getInstance(UserConnection userConnection) {
    if (instance == null) {
      instance = new DatabaseConnectionsService(userConnection);
    }
    return instance;
  }

  public JdbcTemplate openConnection() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(userConnection.getDriver());
    dataSource.setUrl(userConnection.getUrl());
    dataSource.setUsername(userConnection.getUsername());
    dataSource.setPassword(userConnection.getPassword());

    return new JdbcTemplate(dataSource);
  }

  public static void closeConnection() {
    instance = null;
  }

  @Override
  public void update(boolean state) {
    if(!state) {
      DatabaseConnectionsService.closeConnection();
    }
  }

  public UserConnection getUserConnection() {
    return userConnection;
  }

  public void setUserConnection(UserConnection userConnection) {
    this.userConnection = userConnection;
  }
}

