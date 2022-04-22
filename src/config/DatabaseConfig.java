package config;

public class DatabaseConfig {

    // Local MySQL Configuration
   public static final int PORT = 3306;

   public static final String USERNAME = "root";

   public static final String PASSWORD = "root";

   public static final String DATABASE_NAME = "wolfpubdb";

   public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

   public static String HOST = "localhost";

   public static String DATABASE_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;


}
