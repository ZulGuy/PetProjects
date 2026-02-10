package com.example.schemacomparison.commands;

import com.example.editor.interfaces.Command;
import com.example.schemacomparison.services.SchemaComparisonService;
import com.example.sqlide.models.UserConnection;
import java.util.Objects;

public class ShowSchemaCommand implements Command {

  private UserConnection userConnection;

  public ShowSchemaCommand(
      UserConnection userConnection) {
    this.userConnection = userConnection;
  }

  @Override
  public String executeCommand(String command) {
    if(Objects.equals(command, "Show schema")) {
      SchemaComparisonService schemaComparisonService = new SchemaComparisonService();
      return schemaComparisonService.showSchema(userConnection);
    }
    return null;
  }
}
