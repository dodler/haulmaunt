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
        this.groupIndex = "";
        this.faculty = "";
    }
    
    public CustomStudent(int id, String name, String sirName, String parentName, String groupIndex, String faculty){
        this.id = id;
        this.name = name;
        this.sirName = sirName;
        this.parentName = parentName;
        
        this.groupIndex = groupIndex;
        this.faculty = faculty;
    }
    
    public CustomStudent(int id, String name, String sirName, String parentName, String groupIndex, String faculty, String birthDate){
        this(id, name, sirName, parentName, groupIndex, faculty);
        this.birthDate = birthDate;
    }
    
    public CustomStudent(int id, String name, String sirName, String parentName){
        this(id, name, sirName, parentName, null, "");
    }
    
    public CustomStudent(int id, String name, String sirName, String parentName, String birthDate){
        this(id, name, sirName, parentName, null, "");
        this.birthDate = birthDate;
    }
    
    @Override
    public void setGroupIndex(String groupIndex){
        if (!groupIndex.equals(this.groupIndex)){ // запрет на установку той же самой группы
            this.groupIndex = groupIndex;
        }
    }
    
    @Override
    public void setFaculty(String faculty){
        this.faculty = faculty;
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
    public String getGroupIndex(){
        return this.groupIndex;
    }
    @Override
    public String getFaculty(){
        return this.faculty;
    }
    
    @Override
    public String getBirthDate(){
        return this.birthDate;
    }
    
    @Override
    public int getId(){
        return this.id;
    }
}
