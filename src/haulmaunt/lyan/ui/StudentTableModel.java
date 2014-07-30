/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.ui;

import haulmaunt.lyan.student.Student;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Артем
 */
public class StudentTableModel extends DefaultTableModel {
    
    private ArrayList<Student> students;
    
    public static final Object[] columns = {"Имя", "Фамилия", "Отчество", "Дата рождения", "Группа"};
    
    public StudentTableModel(ArrayList<Student> students){
        this.students = students;
    }
    
    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Имя";
            case 1:
                return "Фамилия";
            case 2:
                return "Отчество";
            case 3:
                return "Дата рождения";
            case 4:
                return "Группа";
            default:
                return "Ошибка";
        }
    }

    @Override
    public int getRowCount() {
        return (students == null) ? 0 : students.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return students.get(rowIndex).getSirName();
            case 1:
                return students.get(rowIndex).getName();
            case 2:
                return students.get(rowIndex).getParentName();
            case 3:
                return students.get(rowIndex).getBirthDate();
            case 4:
                return students.get(rowIndex).getGroupIndex();
            case 5:
                return students.get(rowIndex).getId();
            default:
                return null;
        }
    }
}
