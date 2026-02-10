package com.example.editor.controllers;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.interfaces.IQueriesService;
import com.example.editor.interfaces.SqlVisitor;
import com.example.editor.models.EditorModel;
import com.example.editor.models.SqlQuery;
import com.example.editor.services.SqlQueriesService;
import com.example.editor.visitors.SqlVisitorImp;
import com.example.sqlide.models.UserConnection;
import com.example.sqlide.services.DatabaseConnectionsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditorController {

  @PostMapping("editor/new")
  public String executeQuery(@RequestBody @Valid EditorModel editorModel) {
    UserConnection userConnection = new UserConnection();
    userConnection.setUsername(editorModel.getUsername());
    userConnection.setPassword(editorModel.getPassword());
    userConnection.setUrl(editorModel.getUrl());
    userConnection.setDriver(editorModel.getDriver());
    DatabaseConnectionsService dbConnect = DatabaseConnectionsService.getInstance(userConnection);
    IQueriesService implementation = new SqlQueriesService(dbConnect);
    QueriesServiceAbstraction abstractQueryService = new QueriesServiceAbstraction(implementation);
    SqlVisitor sqlVisitor = new SqlVisitorImp();
    SqlQuery sqlQuery = new SqlQuery(editorModel.getQuery(), abstractQueryService, editorModel.getAction());
    return sqlQuery.accept(sqlVisitor);
  }

}
