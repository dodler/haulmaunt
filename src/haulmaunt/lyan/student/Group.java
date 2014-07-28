/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.student;

import java.util.ArrayList;

/**
 *
 * @author Артем
 */
public abstract class Group {
    protected String faculty;
    protected String groupIndex;
    protected ArrayList<Student> students;
    protected int id;
    
    
    public abstract void addStudents(String name, String sirName, String parentName);
    public abstract void addStudents(String name, String sirName, String parentName, String birthDate);
    public abstract String getGroupIndex();
    public abstract String getFaculty();
    public abstract ArrayList<Student> getStudents();
    
    public abstract void setFaculty(String faculty);
    public abstract void setGroupIndex(String groupIndex);
    public abstract int getId();
    
    public abstract void addStudents(Student student);
}
