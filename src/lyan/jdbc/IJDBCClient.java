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

    public ArrayList<Object[]> getGroupData() throws SQLException;

    public ArrayList<Object[]> getStudentData() throws SQLException;
    
    public void sendStudentData(ArrayList<JDBCClient.Packet> data)throws SQLException;
    public void updateStudentData(int id, String name, String sirName, String parentName, String groupIndex, String faculty, String birthDate);
    
    public void updateGroupData(ArrayList<JDBCClient.Packet> data) throws SQLException;
    public void addGroupData(int groupNum, String groupIndex, String faculty) throws SQLException;
    
    /**
     * метод удаления группы по ид
     * не рекомендуется к пользованию можно задеть случайно не то
     * @param groupId - номер записи группы в бд
     */
    public void removeGroup(int groupId)throws SQLException;
    
    /**
     * метод удаления группы по номеру группы
     * @param groupIndex - номер группы, одно из полей в бд, так проще и точнее удалить группы
     */
    public void removeGroup(String groupIndex)throws SQLException;
    
    /**
     * удаление студентов по ид
     * @param id 
     */
    public void removeStudent(int id) throws SQLException;
    public void init();
    
}
