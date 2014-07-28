/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Артем
 */
public interface IJDBCClient {

    ArrayList<Object[]> getGroupData() throws SQLException;

    ArrayList<Object[]> getStudentData() throws SQLException;
    
    public void sendStudentData(ArrayList<JDBCClient.Packet> data);
    
    public void updateGroupData(ArrayList<JDBCClient.Packet> data) throws SQLException;
    public void addGroupData(int groupNum, String groupIndex, String faculty) throws SQLException;
    
    void init();
    
}
