/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Артем
 */
public class JDBCClient {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public JDBCClient(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        connection = null;
    }

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

            connection = DriverManager.getConnection(url, user, password); // подключение к бд

            stat = connection.createStatement();

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public ArrayList<Object[]> getGroupData() throws SQLException {
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        ResultSet responce = stat.executeQuery("select * from dodler.groups");
        while (responce.next()) {
            Object[] cont = {responce.getString("id"), responce.getString("faculty"), responce.getString("studnum")};
            data.add(cont);
        }
        
        return data;
    }
    
    public ArrayList<Object[]> getStudentData() throws SQLException{
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        ResultSet responce = stat.executeQuery("select * from dodler.student");
        while (responce.next()) {
            Object[] cont = {responce.getString("id"), 
                responce.getString("name"), 
                responce.getString("sirname"),
                responce.getString("parentname"),
                responce.getString("groupnumber"),
                responce.getString("faculty"),
                responce.getString("birthdate")
            };
            data.add(cont);
        }
        
        return data;
    }
    
    private Statement stat;
    
}
