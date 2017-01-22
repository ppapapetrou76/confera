package com.softconf.confera;

public class Constants {
  // CRLF
  public static final String CRLF = "\r\n";

  /**
   * Prefixes for REST API URLs
   */
  public static final String URI_API = "/api";

  /**
   * The minimum length of a password
   */
  public static final int MIN_PASSWORD_SIZE = 8;

  /**
   * The JDBC driver name
   */
  public static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

      
  private Constants() {
    // private Constructor to avoid object creation
  }
}
