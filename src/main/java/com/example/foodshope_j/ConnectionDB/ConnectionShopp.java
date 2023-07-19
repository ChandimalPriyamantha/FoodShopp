package com.example.foodshope_j.ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;




public class ConnectionShopp {


    private String url = "jdbc:mysql://localhost:3306/student";

    private String user = "root";

    private String pw = "1234";


    private Connection myCon = null;

    public static Connection ConnectionDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection contact = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafej","root","1234");

            return contact;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
