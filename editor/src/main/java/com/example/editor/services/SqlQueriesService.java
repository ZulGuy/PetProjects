package com.example.editor.services;

import com.example.editor.interfaces.IQueriesService;
import com.example.editor.utils.ResultBuilder;
import com.example.editor.utils.SqlQueriesParser;
import com.example.sqlide.services.DatabaseConnectionsService;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

public class SqlQueriesService implements IQueriesService {

  private DatabaseConnectionsService databaseConnection;
  private JdbcTemplate template;

  public SqlQueriesService(DatabaseConnectionsService databaseConnection) {
    this.databaseConnection = databaseConnection;
    template = this.databaseConnection.openConnection();
  }

  public String executeQuery(String query) {
    List<String> queryList = SqlQueriesParser.parseMultipleQueries(query);
    StringBuilder resultBuilder = new StringBuilder();
    for (String singleQuery : queryList) {
      try {
        List<Map<String, Object>> list = template.queryForList(singleQuery);
        resultBuilder.append(ResultBuilder.buildResultList(list));
      } catch (TransientDataAccessResourceException e) {
        template.update(singleQuery);
        resultBuilder.append("<p>Query have been successfully executed</p>");
      } catch (BadSqlGrammarException r) {
        resultBuilder.append("<p>There is no query or it has been written with a Syntax Error</p>");
      }
    }
    return resultBuilder.toString();
  }

  public DatabaseConnectionsService getDatabaseConnection() {
    return databaseConnection;
  }

  public void setDatabaseConnection(
      DatabaseConnectionsService databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public JdbcTemplate getTemplate() {
    return template;
  }

  public void setTemplate(JdbcTemplate template) {
    this.template = template;
  }
}
