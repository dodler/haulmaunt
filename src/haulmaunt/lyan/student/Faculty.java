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
public class Faculty {
    private String name;
    
    private ArrayList<Student> students;
    private ArrayList<Group> groups;
    
    public Faculty(String name){
        this.name = name;
        this.students = new ArrayList<Student>();
        this.groups = new ArrayList<Group>();
    }
    
    public Faculty(String name, Collection<? extends Student> studList){
        this(name);
        this.students.addAll(studList);
    }
    
    public Faculty(String name, Collection<?extends Student> studList, Collection<? extends Group> groupList){
        this(name, studList);
        this.groups.addAll(groupList);
    }
    
    /**
     * возвращает список студентов из данной группы
     * глупо получиолсь, ведь проще взять из класса группы
     * хотя может будут студенты, которых не добавили в объект группы, но их группа указана
     */
    public ArrayList<Student> filterByGroup(String groupIndex){
        ArrayList<Student> res = new ArrayList<Student>();
        for(Student s:students){
            if (s.getGroupIndex().equals(groupIndex)){
                res.add(s);
            }
        }
        
        return res;
    }
}
