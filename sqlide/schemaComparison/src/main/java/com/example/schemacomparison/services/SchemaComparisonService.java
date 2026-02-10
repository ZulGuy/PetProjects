package com.example.schemacomparison.services;

import com.example.sqlide.models.UserConnection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SchemaComparisonService {

  public String compareSchemas(UserConnection sourceConnection, UserConnection targetConnection) {
      try {
        JdbcTemplate sourceJdbc = new SchemaComparisonConnectionsService(sourceConnection).openConnection();
        JdbcTemplate targetJdbc = new SchemaComparisonConnectionsService(targetConnection).openConnection();
        String sourceSchemaName = extractDatabaseName(sourceConnection.getUrl());
        String targetSchemaName = extractDatabaseName(targetConnection.getUrl());
        List<String> source = sourceJdbc.queryForList(
            "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + sourceSchemaName + "'", String.class);

        List<String> target = targetJdbc.queryForList(
            "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + targetSchemaName + "'", String.class);

        // Порівняння списків таблиць
        if (source.equals(target)) {
          String diffDetails = "Схеми даних ідентичні.\n";
          // Порівняння колонок для кожної таблиці, що міститься у sourceTables
          for (String tableName : source) {
            List<String> sourceColumns = sourceJdbc.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_schema = '" + sourceSchemaName + "' AND table_name = '" + tableName + "'", String.class);

            List<String> targetColumns = targetJdbc.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_schema = '" + targetSchemaName + "' AND table_name = '" + tableName + "'", String.class);

            if (!sourceColumns.equals(targetColumns)) {
              StringBuilder diffDetailsBuilder = new StringBuilder(diffDetails);
              diffDetailsBuilder = new StringBuilder("Схеми даних мають різницю.\n");
              diffDetailsBuilder.append("Таблиця: ").append(tableName).append(" має різні колонки:\n");
              diffDetailsBuilder.append("   Колонки в ").append(sourceSchemaName).append(": ").append(sourceColumns).append("\n");
              diffDetailsBuilder.append("   Колонки в ").append(targetSchemaName).append(": ").append(targetColumns).append("\n");
              diffDetails = diffDetailsBuilder.toString();
            }
          }
          return diffDetails;
        } else {
          return "Схеми даних мають різницю.\n";
        }
      } catch (Exception e) {
        return "Помилка під час порівняння схем даних: " + e.getMessage();
      }
  }

  public String showSchema(UserConnection userConnection) {
    String result = "";
    StringBuilder resultBuilder = new StringBuilder(result);
    String schema = extractDatabaseName(userConnection.getUrl());
    try {
      JdbcTemplate jdbcTemplate = new SchemaComparisonConnectionsService(userConnection).openConnection();
      List<Map<String, Object>> tables = jdbcTemplate.queryForList(
          "SELECT table_name FROM information_schema.tables WHERE table_schema = " + "'" + schema + "'");

      for (Map<String, Object> table : tables) {
        String tableName = (String) table.get("table_name");
        resultBuilder.append("Table: ").append(tableName).append("\n");

        List<Map<String, Object>> columns = jdbcTemplate.queryForList(
            "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?",
            tableName);

        for (Map<String, Object> column : columns) {
          String columnName = (String) column.get("column_name");
          String columnType = (String) column.get("data_type");
          resultBuilder.append("\tColumn Name: ").append(columnName).append(", Column Type: ")
              .append(columnType).append("\n");
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
        for (Map<String, Object> row : rows) {
          row.forEach((key, value) -> resultBuilder.append(key).append(":").append(value)
              .append("\t").append("\n"));
          resultBuilder.append("\n");
        }
      }
      result = resultBuilder.toString();
      result = generateTableFromInput(result);
    } catch (Exception e) {
      return "Помилка під час виведення схеми даних: " + e.getMessage();
    }
    return result;
  }

  public static String generateTableFromInput(String input) {
    StringBuilder htmlTable = new StringBuilder();

    // Розділення введення на окремі таблиці
    String[] tables = input.split("Table:");

    for (String table : tables) {
      if (table.trim().isEmpty()) {
        continue;
      }

      // Отримання імені таблиці та її колонок
      String[] tableDetails = table.split("\n");
      String tableName = tableDetails[0];

      // Додавання інформації про таблицю
      htmlTable.append("<h2>").append(tableName).append("</h2>");
      htmlTable.append("<table border='1'><tr>");

      // Отримання назв колонок та їх типів
      String[] columns = tableDetails[1].split("Column Name: ");
      for (int i = 1; i < columns.length; i++) {
        String[] columnData = columns[i].split("Column Type: ");
        htmlTable.append("<th>").append(columnData[0].trim()).append("</th>");
      }
      htmlTable.append("</tr>");

      // Отримання рядків даних
      for (int i = 2; i < tableDetails.length; i++) {
        String[] rowData = tableDetails[i].split(",");
        htmlTable.append("<tr>");
        for (String data : rowData) {
          htmlTable.append("<td>").append(data.trim()).append("</td>");
        }
        htmlTable.append("</tr>");
      }

      htmlTable.append("</table><br>");
    }

    return htmlTable.toString();
  }

  public static String extractDatabaseName(String jdbcUrl) {
    String regex = "jdbc:[^/]+://[^/]+/([a-zA-Z0-9_-]+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(jdbcUrl);

    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }

}
