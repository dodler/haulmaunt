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
    
    public CustomGroup(String faculty, String groupIndex, int id){
        this.faculty = faculty;
        this.groupIndex = groupIndex;
        this.id = id;
        
        students = new ArrayList<Student>();
    }
    
    public CustomGroup(String faculty, String groupIndex, Collection<? extends Student> c, int id){
        this(faculty, groupIndex, id);
        students.addAll(c);
    }
    
    @Override
    public void addStudents(Student student){
        student.faculty = this.faculty;
        student.groupIndex = this.groupIndex;
        students.add(student);
    }
    
    @Override
    public void addStudents(int id, String name, String sirName, String parentName){
        students.add(new CustomStudent(id, name, sirName, parentName, this.groupIndex, faculty));
    }
    
    @Override
    public void addStudents(int id, String name, String sirName, String parentName, String birthDate){
        students.add(new CustomStudent(id, name, sirName, parentName, this.groupIndex, faculty, birthDate));
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
    public String getGroupIndex() {
        return this.groupIndex;
    }

    @Override
    public String getFaculty() {
        return this.faculty;
    }

    @Override
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    @Override
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public void setGroupIndex(String groupIndex) {
        this.groupIndex = groupIndex;
    }
    
    @Override
    public int getId(){
        return this.id;
    }
}
