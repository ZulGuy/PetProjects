package com.example.editor.utils;

import java.util.ArrayList;
import java.util.List;

public class SqlQueriesParser {

    public static List<String> parseMultipleQueries(String combinedQueries) {
      List<String> queriesList = new ArrayList<>();

      String[] queriesArray = combinedQueries.split(";");

      for (String query : queriesArray) {
        String trimmedQuery = query.trim();
        if (!trimmedQuery.isEmpty()) {
          queriesList.add(trimmedQuery);
        }
      }

      return queriesList;
    }
}
