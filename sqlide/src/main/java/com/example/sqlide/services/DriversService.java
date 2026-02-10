package com.example.sqlide.services;

public class DriversService {

  public static String getDriver(String db) {

    return switch (db) {
      case "Microsoft SQL server" -> "com.microsoft.sqlserver.jdbc.SQLServerDriver";
      case "Oracle" -> "oracle.jdbc.OracleDriver";
      case "IBM DB2" -> "com.ibm.db2.jcc.DB2Driver";
      case "MySQL" -> "com.mysql.cj.jdbc.Driver";
      case "PostgreSQL" -> "org.postgresql.Driver";
      default -> null;
    };
  }

}
