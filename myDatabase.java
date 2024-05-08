package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myDatabase {

    final String URL="jdbc:mysql://localhost:3306/project";

    final String USERNAME="root";

    final String PASSWORD="";

    Connection connection;

    static myDatabase instance;

    private myDatabase(){
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connection successfull");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public static myDatabase getInstance() {
        if(instance==null){
            instance = new myDatabase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
