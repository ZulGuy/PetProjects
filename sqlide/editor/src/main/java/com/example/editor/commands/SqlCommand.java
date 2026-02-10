package com.example.editor.commands;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.interfaces.Command;
import java.util.Objects;

public class SqlCommand implements Command {

  private String query;

  private QueriesServiceAbstraction abstractQueryService;



  public SqlCommand(String query, QueriesServiceAbstraction abstractQueryService) {
    this.query = query;
    this.abstractQueryService = abstractQueryService;
  }

  @Override
  public String executeCommand(String command) {
    if(Objects.equals(command, "Execute query")) {
      return abstractQueryService.executeQuery(query);
    } else if(Objects.equals(command, "Save SQL script")){

      return null;
    }
    return null;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String command) {
    this.query = command;
  }
}
