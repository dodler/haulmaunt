/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Артем
 */
public class JDBCClient {
    
    private String url;
    private String user;
    private String password;
    
    private Connection connection;
    
    public JDBCClient(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        
        connection = null;
    }
    
    public void init(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            
            connection = DriverManager.getConnection(url, user, password); // подключение к бд
            
            Statement stat = connection.createStatement();
            
            ResultSet responce = stat.executeQuery("select * from dodler.groups");
            while(responce.next()){
                System.out.println("Ответ из бд:" + responce.getString("id"));
            }
            
        }catch(Exception e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
