package com.libook.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
        private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        private static final String DB_URL = "jdbc:mysql://localhost:3306/libook";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "";

        private static Connection connection;

        private DbConnection(){}

        public static Connection getConnection(){
                if (connection == null){
                        try {
                                Class.forName(JDBC_DRIVER);
                                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        }catch (ClassNotFoundException | SQLException e){
                                e.printStackTrace();
                        }
                }
                return connection;
        }

        public static void closeConnection() {
                if (connection != null) {
                        try {
                                connection.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                }
        }
}
