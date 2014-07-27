/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt;

import haulmaunt.lyan.student.CustomGroup;
import haulmaunt.lyan.student.Group;
import haulmaunt.lyan.ui.CustomScreen;
import haulmaunt.lyan.ui.MissingMouseListenerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import lyan.jdbc.JDBCClient;
import org.xml.sax.SAXException;

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
                ArrayList<Object[]> studentData = null;
                ArrayList<Object[]> groupData = null;
                try {
                    studentData = client.getStudentData();
                    groupData = client.getGroupData();
                } catch (SQLException ex) {
                    Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
                }

                HashMap<String, Group> groups = new HashMap<String, Group>();

                CustomScreen screen = new CustomScreen();

                for (Object[] o : studentData) {
                    
                    if (!groups.containsKey((String)o[4])){  // предварительно добавляем группу
                        groups.put((String)o[4], new CustomGroup((String)o[5], Integer.parseInt((String)o[4])));
                    }
                    
                    if (groups.containsKey((String)o[4])){ // группа есть вв списке
                        groups.get((String)o[4]).addStudents((String) o[1], (String) o[2], (String) o[3], (String) o[6]);
                    }
                }
                screen.addGroup(groups.values());
                try {
                    screen.initUI();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MissingMouseListenerException ex) {
                    Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();
    }
}
