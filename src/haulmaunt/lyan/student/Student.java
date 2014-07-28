/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.student;

/**
 *
 * @author Артем
 */
public abstract class Student {
    protected String name;
    protected String sirName;
    protected String parentName;
    
    protected String groupIndex;
    protected String faculty;
    
    protected String birthDate; 
    
    public Student(){
        
    }
    
    public Student(String name, String sirName, String parentName, Group parentGroup, String faculty){
        
    }
    
    public abstract String getName();
    public abstract String getSirName();
    public abstract String getParentName();
    public abstract String getGroupIndex();
    public abstract String getFaculty();
    public abstract void setGroupIndex(String groupIndex);
    public abstract void setFaculty(String faculty);
    public abstract String getBirthDate();
    
    
}
