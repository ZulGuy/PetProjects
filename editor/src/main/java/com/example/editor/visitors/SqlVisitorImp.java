package com.example.editor.visitors;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.commands.SqlCommand;
import com.example.editor.interfaces.Command;
import com.example.editor.interfaces.SqlVisitor;
import com.example.editor.models.SqlQuery;

public class SqlVisitorImp implements SqlVisitor {

  @Override
  public String visit(SqlQuery sqlQuery, QueriesServiceAbstraction abstractQueryService) {
    Command command = new SqlCommand(sqlQuery.getQuery(), abstractQueryService);
    return command.executeCommand(sqlQuery.getAction());
  }
}
