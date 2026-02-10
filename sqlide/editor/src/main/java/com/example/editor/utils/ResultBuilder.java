package com.example.editor.utils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ResultBuilder {

  public static String buildResultList(List<Map<String, Object>> list) {
    String htmlTable = "";
    StringBuilder htmlTableBuilder = new StringBuilder(htmlTable);
    htmlTableBuilder.append("<table border='1'>");
    htmlTableBuilder.append("<tr>");
    if (!list.isEmpty()) {
      Map<String, Object> firstRow = list.get(0);
      for (String key : firstRow.keySet()) {
        htmlTableBuilder.append("<th>").append(key).append("</th>");
      }
      htmlTableBuilder.append("</tr>");

      // Додавання рядків даних у таблицю
      for (Map<String, Object> row : list) {
        htmlTableBuilder.append("<tr>");
        for (Object value : row.values()) {
          htmlTableBuilder.append("<td>").append(value).append("</td>");
        }
        htmlTableBuilder.append("</tr>");
      }
    }

    htmlTableBuilder.append("</table>");
    htmlTable = htmlTableBuilder.toString();
    return htmlTable;
  }

  public static StringBuilder buildResultRowSet(SqlRowSet sqlRowSet) {
    StringBuilder htmlTable = new StringBuilder();
    htmlTable.append("<table border='1'>");

    ResultSetMetaData metaData = (ResultSetMetaData) sqlRowSet.getMetaData();
    int columnCount = 0;
    try {
      columnCount = metaData.getColumnCount();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    htmlTable.append("<tr>");
    for (int i = 1; i <= columnCount; i++) {
      try {
        htmlTable.append("<th>").append(metaData.getColumnName(i)).append("</th>");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    htmlTable.append("</tr>");
    while (sqlRowSet.next()) {
      htmlTable.append("<tr>");
      for (int i = 1; i <= columnCount; i++) {
        htmlTable.append("<td>").append(sqlRowSet.getString(i)).append("</td>");
      }
      htmlTable.append("</tr>");
    }

    htmlTable.append("</table>");
    return htmlTable;
  }

}
