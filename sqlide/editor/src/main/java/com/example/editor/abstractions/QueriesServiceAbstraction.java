package com.example.editor.abstractions;


import com.example.editor.interfaces.IQueriesService;

public class QueriesServiceAbstraction {

  IQueriesService bridgeImplementation;

  public QueriesServiceAbstraction(IQueriesService bridgeImplementation) {
    this.bridgeImplementation = bridgeImplementation;
  }

  public String executeQuery(String query) {
    return bridgeImplementation.executeQuery(query);
  }

}
