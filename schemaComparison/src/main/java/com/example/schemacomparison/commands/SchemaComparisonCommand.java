package com.example.schemacomparison.commands;

import com.example.editor.interfaces.Command;
import com.example.schemacomparison.services.SchemaComparisonService;
import com.example.sqlide.models.UserConnection;
import java.util.Objects;

public class SchemaComparisonCommand implements Command {

  private UserConnection sourceUserConnection;
  private UserConnection targetUserConnection;

  public SchemaComparisonCommand(
      UserConnection sourceUserConnection,
      UserConnection targetUserConnection) {
    this.sourceUserConnection = sourceUserConnection;
    this.targetUserConnection = targetUserConnection;
  }

  @Override
  public String executeCommand(String command) {
    if(Objects.equals(command, "Compare schemas")) {
      SchemaComparisonService schemaComparisonService = new SchemaComparisonService();
      return schemaComparisonService.compareSchemas(sourceUserConnection, targetUserConnection);
    }
    return null;
  }
}
