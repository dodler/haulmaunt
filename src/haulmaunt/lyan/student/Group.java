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
    protected int number;
    protected ArrayList<Student> students;
    
    
    public abstract void addStudents(String name, String sirName, String parentName);
    public abstract void addStudents(String name, String sirName, String parentName, String birthDate);
    public abstract int getNumber();
    public abstract String getFaculty();
    public abstract ArrayList<Student> getStudents();
    
    public abstract void addStudents(Student student);
}
