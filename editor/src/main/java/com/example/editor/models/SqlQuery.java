package com.example.editor.models;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.interfaces.Visitable;
import com.example.editor.interfaces.SqlVisitor;

public class SqlQuery implements Visitable {

  private String query;
  private QueriesServiceAbstraction abstractQueryService;
  private String action;

  @Override
  public String accept(SqlVisitor visitor) {
    return visitor.visit(this, abstractQueryService);
  }

  public SqlQuery() {
  }

  public SqlQuery(String query, QueriesServiceAbstraction abstractQueryService, String action) {
    this.query = query;
    this.abstractQueryService = abstractQueryService;
    this.action = action;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public QueriesServiceAbstraction getAbstractQueryService() {
    return abstractQueryService;
  }

  public void setAbstractQueryService(
      QueriesServiceAbstraction abstractQueryService) {
    this.abstractQueryService = abstractQueryService;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
