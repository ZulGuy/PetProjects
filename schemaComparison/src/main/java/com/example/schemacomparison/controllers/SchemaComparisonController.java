package com.example.schemacomparison.controllers;

import com.example.schemacomparison.commands.SchemaComparisonCommand;
import com.example.schemacomparison.commands.ShowSchemaCommand;
import com.example.editor.interfaces.Command;
import com.example.schemacomparison.models.CompareModel;
import com.example.schemacomparison.models.ShowModel;
import com.example.sqlide.models.UserConnection;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/schema")
public class SchemaComparisonController {

  @PostMapping("schema/compare/new")
  public String compareSchemas(@RequestBody @Valid CompareModel compareModel) {
    UserConnection sourceUserConnection = compareModel.getSourceUserConnection();
    UserConnection targetUserConnection = compareModel.getTargetUserConnection();
    Command command = new SchemaComparisonCommand(sourceUserConnection,
        targetUserConnection);
    return command.executeCommand(compareModel.getAction());
  }

  @PostMapping("schema/show/new")
  public String showSchema(@RequestBody @Valid ShowModel showModel) {
    Command command = new ShowSchemaCommand(showModel.getUserConnection());
    return command.executeCommand(showModel.getAction());
  }
}
