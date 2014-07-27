/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.student;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Артем
 */
public class CustomGroup extends Group{
    
    public CustomGroup(String faculty, int number){
        this.faculty = faculty;
        this.number = number;
        
        students = new ArrayList<Student>();
    }
    
    public CustomGroup(String faculty, int number, Collection<? extends Student> c){
        this(faculty, number);
        students.addAll(c);
    }
    
    public void addStudents(Student student){
        student.faculty = this.faculty;
        student.parentGroup = this;
        students.add(student);
    }
    
    public void addStudents(String name, String sirName, String parentName){
        students.add(new CustomStudent(name, sirName, parentName, this, faculty));
    }
    
    @Override
    public void addStudents(String name, String sirName, String parentName, String birthDate){
        students.add(new CustomStudent(name, sirName, parentName, this, faculty, birthDate));
    }
    
    public void removeStudent(Student student){
        students.remove(student);
    }
    
    public void removeStudent(int index){
        students.remove(index);
    }
    // далее методы для редактирования студентов
    public void setStudentName(int index, String name){
        students.get(index).name = name;
    }
    
    public void setStudentSirName(int index, String sirName){
        students.get(index).sirName = sirName;
    }
    
    public void setStudentParentName(int index, String parentName){
        students.get(index).parentName = parentName;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public String getFaculty() {
        return this.faculty;
    }

    @Override
    public ArrayList<Student> getStudents() {
        return this.students;
    }
}
