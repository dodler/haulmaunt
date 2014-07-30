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
public class JDBCClient implements IJDBCClient {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    @Override
    public void updateStudentData(ArrayList<Packet> data) throws SQLException {
        StringBuilder request1 = new StringBuilder();
        StringBuilder request2 = new StringBuilder();

        for (Packet d : data) {
            request1.append("update dodler.student set ");
            request1.append(d.fieldToChange);
            request1.append("='");
            request1.append(d.newValue);
            request1.append("' ");

            request2.append(" where ");
            request2.append(d.index);
            request2.append("=");
            request2.append(d.indexValue);

            request1.append(request2);
            stat.executeUpdate(request1.toString());
            request1.setLength(0);
            request2.setLength(0);
        }
    }

    @Override
    public void removeGroup(int groupId) throws SQLException {
        StringBuilder request = new StringBuilder();
        request.append("delete from dodler.groups where id='");
        request.append(Integer.toString(groupId));
        request.append("'");
        stat.executeUpdate(request.toString());
    }

    @Override
    public void removeGroup(String groupIndex) throws SQLException {
        StringBuilder request = new StringBuilder();
        request.append("delete from dodler.groups where groupindex='");
        request.append(groupIndex);
        request.append("'");
        System.out.println(request.toString());
        stat.executeUpdate(request.toString());
    }

    public static class Packet {

        String fieldToChange;
        String newValue;
        String index;
        String indexValue;

        public Packet(String fieldToChange, String newValue, String index, String indexValue) {
            this.fieldToChange = fieldToChange;
            this.newValue = newValue;
            this.index = index;
            this.indexValue = indexValue;
        }
    }

    @Override
    public void addGroupData(int groupNum, String groupIndex, String faculty) throws SQLException {
        StringBuilder request = new StringBuilder("insert into dodler.groups (ID, FACULTY, GROUPINDEX) values('");
        request.append(Integer.toString(groupNum + 1));
        request.append("','");
        request.append(faculty);
        request.append("','");
        request.append(groupIndex);
        request.append("')");

        // insert into dodler.groups (ID, FACULTY, GROUPINDEX) values('1', 'двигателей летательных аппаратов', '2102')
        stat.executeUpdate(request.toString());
    }

    @Override
    public void updateGroupData(ArrayList<Packet> data) throws SQLException {
        StringBuilder request1 = new StringBuilder();
        StringBuilder request2 = new StringBuilder();

        for (Packet d : data) {
            request1.append("update dodler.groups set ");
            request1.append(d.fieldToChange);
            request1.append("='");
            request1.append(d.newValue);
            request1.append("' ");

            request2.append(" where ");
            request2.append(d.index);
            request2.append("='");
            request2.append(d.indexValue);
            request2.append("'");

            request1.append(request2);
            stat.executeUpdate(request1.toString());
            request1.setLength(0);
            request2.setLength(0);
        }
        // update dodler.groups set faculty='asd' where id=6207 - пример запроса

    }

    @Override
    public void addStudentData(int id,
            String name,
            String sirName,
            String parentName,
            String groupNumber,
            String faculty,
            String birthDate) throws SQLException {
        StringBuilder request = new StringBuilder("insert into dodler.student (ID, NAME, SIRNAME, PARENTNAME, GROUPNUMBER, FACULTY, BIRTHDATE) values(");
        request.append(Integer.toString(id));
        request.append(",'");
        request.append(name);
        request.append("','");
        request.append(sirName);
        request.append("','");
        request.append(parentName);
        request.append("','");
        request.append(groupNumber);
        request.append("','");
        request.append(faculty);
        request.append("','");
        request.append(birthDate);
        request.append("')");
        
        stat.executeUpdate(request.toString());
    }

    @Override
    public void removeStudent(int id) {
    }

    public JDBCClient(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        connection = null;
    }

    @Override
    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

            connection = DriverManager.getConnection(url, user, password); // подключение к бд

            stat = connection.createStatement();

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Object[]> getGroupData() throws SQLException {
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        ResultSet responce = stat.executeQuery("select * from dodler.groups");
        while (responce.next()) {
            Object[] cont = {responce.getString("id"), responce.getString("faculty"), responce.getString("groupindex")};
            data.add(cont);
        }

        return data;
    }

    @Override
    public ArrayList<Object[]> getStudentData() throws SQLException {
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
