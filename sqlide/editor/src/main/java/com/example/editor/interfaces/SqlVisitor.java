package com.example.editor.interfaces;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.models.SqlQuery;

public interface SqlVisitor {

  String visit(SqlQuery sqlQuery, QueriesServiceAbstraction abstractQueryService);

}
