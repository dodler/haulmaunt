/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.ui;

import haulmaunt.lyan.student.Group;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Артем
 */
public class GroupTableModel extends DefaultTableModel{

    private ArrayList<Group> groups;
    
    //public static final Object[] columns = {"Номер", "Факультет", "Число студентов"};
    
    public GroupTableModel(ArrayList<Group> groups){
        this.groups = groups;
    }
    
    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Номер";
            case 1:
                return "Факультет";
            case 2:
                return "Число студентов";
            default:
                return "Ошибка";
        }
    }

    @Override
    public int getRowCount() {
        return (groups == null) ? 0 : groups.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public void setGroups(ArrayList<Group> groups){
        this.groups = groups;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return groups.get(rowIndex).getGroupIndex();
            case 1:
                return groups.get(rowIndex).getFaculty();
            case 2:
                return groups.get(rowIndex).getStudents().size();
            case 3:
                return Integer.toString(groups.get(rowIndex).getId());
            default:
                return null;
        }
    }
    
    @Override 
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        
    }
}
