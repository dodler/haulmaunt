/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt;

import haulmaunt.lyan.student.CustomGroup;
import haulmaunt.lyan.student.CustomStudent;
import haulmaunt.lyan.student.Group;
import haulmaunt.lyan.ui.CustomScreen;
import lyan.jdbc.JDBCClient;

/**
 *
 * @author Артем
 */
public class Haulmaunt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Thread() {
            @Override
            public void run() {
                JDBCClient client = new JDBCClient("jdbc:derby://localhost:1527/Students", "dodler", "1123");
                client.init();
            }
        }.start();
        
        Group gr6207 = new CustomGroup("information", 6207);
        gr6207.addStudents(new CustomStudent("Lyan", "Artyom", "Игоревич", Double.toString(Math.random())));
        gr6207.addStudents(new CustomStudent("Ушакова", "Наталья", "Сергеевна", Double.toString(Math.random())));
        gr6207.addStudents(new CustomStudent("Сайфутдинов", "Денис", "Сагитович", Double.toString(Math.random())));
        gr6207.addStudents(new CustomStudent("Ивлиев", "Дмитрий", "Алексеевич", Double.toString(Math.random())));
        gr6207.addStudents(new CustomStudent("Сахно", "Полина", "Алексеевна", Double.toString(Math.random())));

        Group gr6208 = new CustomGroup("informa2tion", 6208);
        gr6208.addStudents(new CustomStudent("Ly1an", "Art1yom", "Игорев1ич", Double.toString(Math.random())));
        gr6208.addStudents(new CustomStudent("Уша1кова", "На1талья", "Сер1геевна", Double.toString(Math.random())));
        gr6208.addStudents(new CustomStudent("Сай1футдинов", "Ден1ис", "Са1гитович", Double.toString(Math.random())));
        gr6208.addStudents(new CustomStudent("Ивл1иев", "Дми1трий", "Алекс1еевич", Double.toString(Math.random())));
        gr6208.addStudents(new CustomStudent("Сах1но", "Полин1а", "Алекс1еевна", Double.toString(Math.random())));

        CustomScreen screen = new CustomScreen();

        screen.addGroup(gr6207);
        screen.addGroup(gr6208);

        screen.initUI();
    }
}
