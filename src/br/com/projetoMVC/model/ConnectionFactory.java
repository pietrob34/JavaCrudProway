package br.com.projetoMVC.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

    public class ConnectionFactory {
        public static final String PATH = "jdbc:postgresql://localhost:5432/turmajava";
        public static final String USER = "postgres";
        public static final String PASSWORD = "root";

        public static Connection getConnection() throws Exception {
            try{
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection(PATH, USER, PASSWORD);

            }catch(Exception exception){
                throw new Exception(exception);
            }
        }

        public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws Exception {
            close(connection,statement,resultSet);
        }

        public static void closeConnection(Connection connection, Statement statement) throws Exception {
            close(connection,statement,null);
        }

        public static void closeConnection(Connection connection) throws Exception {
            close(connection,null,null);
        }

        private static void close(Connection connection, Statement statement, ResultSet resultSet) throws Exception{
            try {
                if (connection != null){
                    connection.close();
                }
                if(statement != null){
                    statement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }

            }catch (Exception exception){
                throw new Exception(exception);
            }
        }
    }

