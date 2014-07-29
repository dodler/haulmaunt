/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.ui;

import haulmaunt.Haulmaunt;
import haulmaunt.lyan.student.CustomGroup;
import haulmaunt.lyan.student.CustomStudent;
import haulmaunt.lyan.student.Faculty;
import haulmaunt.lyan.student.Group;
import haulmaunt.lyan.student.Student;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import lyan.jdbc.IJDBCClient;
import org.xml.sax.SAXException;

import lyan.jdbc.JDBCClient.Packet;

/**
 *
 * @author Артем
 */
public class CustomScreen extends JFrame {

    private CustomTable table; // таблица с группами
    private Faculty faculty; // факультет
    private ArrayList<Group> groups; // список групп 
    private ArrayList<Student> students; // список студентов
    private GroupTableModel groupTableModel;
    private StudentTableModel studentTableModel;
    private GroupEditDialog groupEditDialog; // редактирование группы
    private GroupAddDialog groupAddDialog; // добавление группы
    private StudentAddDialog studentAddDialog; // добавление студента
    private StudentEditDialog studentEditDialog;  // редактирование студента
    private MouseListener addMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            groupAddDialog.setVisible(true);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private MouseListener editMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (table.getSelectedColumn() == -1) { // если в таблице ничего не вбырано
                JOptionPane.showMessageDialog(
                        getParent(),
                        "Выберите строку в таблице");
                return;
            }
            if (isGroup) {
                groupEditDialog.setVisible(true);
            } else {
                studentEditDialog.setVisible(true);
            }



        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private MouseListener removeMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (table.getSelectedColumn() == -1) {
                JOptionPane.showMessageDialog(
                        getParent(),
                        "Выберите строку в таблице");
                return;
            }
            try {
                if (isGroup) {
                    dbClient.removeGroup(groups.get(table.getSelectedRow()).getId()); // удаляем по номеру группы
                } else {
                    dbClient.removeStudent(students.get(table.getSelectedRow()).getId());
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(e.getComponent().getParent().getParent(), "Ошибка при удалении записи в бд");
            }
            table.reloadTable();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private MouseListener switchStudentGroupMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!isGroup) {
                table.setModel(groupTableModel);
                isGroup = true;
            } else {
                table.setModel(studentTableModel);
                isGroup = false;
            }
            table.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private WindowListener myWindowListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0); // TODO может понадобится какая то обработка
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    };
    private JScrollPane scrollPane;
    private boolean isGroup = true; // сейчас отображается группы
    private IJDBCClient dbClient;

    public void setDBCLient(IJDBCClient dbCLient) {
        this.dbClient = dbCLient;
    }

    public CustomScreen() {
        super("Приложение для работы со студентами");
        this.setName("main");
        groups = new ArrayList<Group>();
        students = new ArrayList<Student>();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(800, 600);

        this.addWindowListener(myWindowListener);

        groupTableModel = new GroupTableModel(groups);
        studentTableModel = new StudentTableModel(students);

        table = new CustomTable(studentTableModel);
        scrollPane = new JScrollPane(table);

        groupEditDialog = new GroupEditDialog(this, "Редактировать группу", "groupEditDialog");
        groupAddDialog = new GroupAddDialog(this, "Добавить группу", "groupAddDialog");

        //studentEditDialog  = new StudentEditDialog(this, "Редактировать студента","studentEditDialog");
        //studentAddDialog = new StudentAddDialog(this, "Добавить студента", "studentAddDialog");
    }

    public void addStudent(int id, String name, String sirName, String parentName, String groupIndex, String faculty) {
        students.add(new CustomStudent(id, name, sirName, parentName, groupIndex, faculty)); // TODO могут возникнуть проблемы
    }

    public void addStudent(int id, String name, String sirName, String parentName, String groupIndex, String faculty, String birthDate) {
        CustomStudent cs = new CustomStudent(id, name, sirName, parentName, birthDate);
        cs.setGroupIndex(groupIndex);
        cs.setFaculty(faculty);
        students.add(cs);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * добавляет группы в список отображения
     *
     * @param group - группа, которую нужно добавить
     */
    public void addGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
        }
    }

    public void addGroup(String faculty, String groupIndex, int id) {
        Group group = new CustomGroup(faculty, groupIndex, id);
        addGroup(group);
    }

    public void addGroup(Collection<Group> groups) {
        for (Group g : groups) {
            addGroup(g);
        }
    }

    public void initUI() throws ParserConfigurationException, SAXException, IOException, MissingMouseListenerException {
        this.setVisible(true);
        Container pane = getContentPane();
        pane.setLayout(null);

        MarkupLoader ml = MarkupLoader.getMarkupLoaderInstance();

        ml.mouseListeners.put("switchStudentGroup", switchStudentGroupMouseListener);
        ml.mouseListeners.put("addGroupMouseListener", addMouseListener);
        ml.mouseListeners.put("editGroupMouseListener", editMouseListener);
        ml.mouseListeners.put("removeGroupMouseListener", removeMouseListener);

        ml.LoadMarkup("main", pane);
        // вложенные layout пока не поддерживаются

        studentTableModel.setStudents(students);

        table.setModel(groupTableModel);

        pane.add(scrollPane);

        scrollPane.setBounds(100, 100, 500, 100);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        this.groupEditDialog.initUI();
        groupAddDialog.initUI();
        //studentEditDialog.initUI();
        //studentAddDialog.initUI();

        pane.repaint();
    }

    /**
     * класс редактирования групп студентов
     *
     * @author Артем
     */
    class GroupEditDialog extends JDialog {

        private MouseListener acceptML = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO здесь надо делать запрос в базу
                if (isGroup) {
                    ArrayList<Packet> data = new ArrayList<Packet>();

                    data.add(new Packet(
                            "faculty",
                            input2.getText(),
                            "id",
                            Integer.toString(groups.get(table.getSelectedRow()).getId())));
                    data.add(new Packet(
                            "groupindex",
                            input1.getText(),
                            "id",
                            Integer.toString(groups.get(table.getSelectedRow()).getId())));
                    try {
                        dbClient.updateGroupData(data);
                    } catch (SQLException ex) {
                        Logger.getLogger(CustomScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                }
                table.reloadTable();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        private MouseListener denyML = new MouseListener() {
            //public haulmaunt.lyan.ui.GroupEditDialog source;
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        JButton accept, deny;
        JTextField input1, input2;
        JLabel label1, label2;

        public GroupEditDialog(JFrame parent, String title) {
            super(parent, title);

            setLayout(null);
            setModal(true);
            setResizable(false);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setSize(300, 400);

            input1 = new JTextField();
            input2 = new JTextField();

            label1 = new JLabel("Номер группы");
            label2 = new JLabel("Факультет");

            accept = new JButton("Сохранить изменения");
            deny = new JButton("Отменить изменения");
        }

        public GroupEditDialog(JFrame parent, String title, String name) {
            this(parent, title);
            this.setName(name);
        }

        public void initUI() {
            Container pane = getContentPane();

            pane.add(input1);
            pane.add(input2);
            pane.add(label1);
            pane.add(label2);

            pane.add(accept);
            pane.add(deny);

            accept.addMouseListener(acceptML);
            deny.addMouseListener(denyML);

            input1.setBounds(10, 130, 100, 30);
            input2.setBounds(150, 130, 100, 30);

            label1.setBounds(10, 90, 100, 30);
            label2.setBounds(150, 90, 100, 30);

            accept.setBounds(10, 160, 100, 30);
            deny.setBounds(150, 160, 100, 30);

            repaint();
        }
    }

    /**
     * класс добавления студентов
     *
     * @author Артем
     */
    class GroupAddDialog extends JDialog {

        JButton accept;
        JTextField groupIndex, faculty;
        JLabel l1, l2;
        private MouseListener acceptMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    // insert into dodler.groups (ID, FACULTY, GROUPINDEX) values('1', 'двигателей летательных аппаратов', '2102')
                    dbClient.addGroupData(
                            CustomScreen.getDateHash(),
                            groupIndex.getText(),
                            faculty.getText());
                } catch (SQLException ex) {
                    // тут сделать вывод окошка
                    Logger.getLogger(CustomScreen.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(
                            getParent(),
                            "Произошла ошибка при запросе в БД. Проверьте введенные данные");
                }
                table.reloadTable();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        public GroupAddDialog(JFrame parent, String title, String name) {
            super(parent, title);
            setName(name);

            this.setDefaultCloseOperation(HIDE_ON_CLOSE);

            setLayout(null);
            setModal(true);
            setResizable(false);

            accept = new JButton("Добавить группу");
            groupIndex = new JTextField();
            faculty = new JTextField();

            l1 = new JLabel("Номер группы");
            l2 = new JLabel("Факультет");
            setSize(300, 400);

            accept.setBounds(50, 200, 100, 30);
            groupIndex.setBounds(50, 150, 100, 30);
            faculty.setBounds(100, 150, 100, 30);
            l1.setBounds(50, 50, 100, 30);
            l2.setBounds(150, 50, 100, 30);

            accept.addMouseListener(acceptMouseListener);
        }

        public void initUI() {
            Container pane = getContentPane();

            pane.add(l1);
            pane.add(l2);
            pane.add(groupIndex);
            pane.add(faculty);
            pane.add(accept);

            repaint();

        }
    }

    class StudentAddDialog extends JDialog {

        public void initUI() {
            throw new UnsupportedOperationException();
        }
    }

    class StudentEditDialog extends JDialog {

        public void initUI() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * таблица, которая отображается программе запрет на редактирование ячеек и
     * перезагрузка данных из бд
     */
    class CustomTable extends JTable {

        public CustomTable(DefaultTableModel model) {
            super(model);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;// ведь редактируем через окно
        }

        public void reloadTable() {
            ArrayList<Object[]> studentData = null;
            ArrayList<Object[]> groupData = null;
            try {
                studentData = dbClient.getStudentData();
                groupData = dbClient.getGroupData();
            } catch (SQLException ex) {
                Logger.getLogger(Haulmaunt.class.getName()).log(Level.SEVERE, null, ex);
            }

            HashMap<String, Group> groupMap = new HashMap<String, Group>();

            students.clear();
            groups.clear();
            for (int i = 0; i < studentTableModel.getRowCount(); i++) {
                studentTableModel.removeRow(0);
            }

            for (int i = 0; i < groupTableModel.getRowCount(); i++) {
                groupTableModel.removeRow(0);
            }

            for (Object[] o : studentData) {
                addStudent(Integer.parseInt((String) o[0]), (String) o[1], (String) o[2], (String) o[3], (String) o[4], (String) o[5], (String) o[6]);
            }

            for (Object[] o : groupData) {
                addGroup((String) o[1], (String) o[2], Integer.parseInt((String) o[0]));
            }
            studentTableModel.setStudents(students);
            groupTableModel.setGroups(groups);
            
            if (isGroup){
                setModel(groupTableModel);
            }else{
                setModel(studentTableModel);
            }

            repaint();
        }
    }

    public static int getDateHash() {
        Calendar c = Calendar.getInstance();
        StringBuilder hashDateBuilder = new StringBuilder(); // делаем хеш из даты
        hashDateBuilder.append(c.get(Calendar.YEAR));
        hashDateBuilder.append(".");
        hashDateBuilder.append(c.get(Calendar.DAY_OF_YEAR));
        hashDateBuilder.append(".");
        hashDateBuilder.append(c.get(Calendar.HOUR));
        hashDateBuilder.append(".");
        hashDateBuilder.append(c.get(Calendar.MINUTE));
        hashDateBuilder.append(".");
        hashDateBuilder.append(c.get(Calendar.SECOND));
        hashDateBuilder.append(".");
        hashDateBuilder.append(c.get(Calendar.MILLISECOND));
        System.out.println(hashDateBuilder.toString());
        return hashDateBuilder.hashCode();
    }
}
