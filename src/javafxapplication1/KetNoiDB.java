/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author My_Love_Is_My
 */
public class KetNoiDB {
    public static Connection ketNoi(){
        Connection connect = null;
        String uRL = "jdbc:sqlserver://;databaseName=QLTS";
        String user = "sa";
        String password = "123456";
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(uRL, user, password);
        }catch(ClassNotFoundException | SQLException e){
            System.err.println(e);
        } 
        return connect;
    }
}
