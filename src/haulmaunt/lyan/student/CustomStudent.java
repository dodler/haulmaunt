/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.student;

/**
 *
 * @author Артем
 */
public class CustomStudent extends Student{
    
    private CustomStudent(){
        this.name = "";
        this.sirName = "";
        this.parentName = "";
        this.parentGroup = null;
        this.faculty = "";
    }
    
    public CustomStudent(String name, String sirName, String parentName, Group parentGroup, String faculty){
        this.name = name;
        this.sirName = sirName;
        this.parentName = parentName;
        
        this.parentGroup = parentGroup;
        this.faculty = faculty;
    }
    
    public CustomStudent(String name, String sirName, String parentName, Group parentGroup, String faculty, String birthDate){
        this(name, sirName, parentName, parentGroup, faculty);
        this.birthDate = birthDate;
    }
    
    public CustomStudent(String name, String sirName, String parentName){
        this(name, sirName, parentName, null, "");
    }
    
    public CustomStudent(String name, String sirName, String parentName, String birthDate){
        this(name, sirName, parentName, null, "");
        this.birthDate = birthDate;
    }
    
    @Override
    public void setParentGroup(Group parentGroup){
        if (!parentGroup.equals(this.parentGroup)){ // запрет на установку той же самой группы
            this.parentGroup = parentGroup;
        }
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    @Override
    public String getSirName(){
        return this.sirName;
    }
    @Override
    public String getParentName(){
        return this.parentName;
    }
    @Override
    public Group getParentGroup(){
        return this.parentGroup;
    }
    @Override
    public String getFaculty(){
        return this.faculty;
    }
    
    @Override
    public String getBirthDate(){
        return this.birthDate;
    }
}
