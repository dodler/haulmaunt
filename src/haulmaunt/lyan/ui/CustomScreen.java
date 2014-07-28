/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.ui;

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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.xml.parsers.ParserConfigurationException;
import lyan.jdbc.IJDBCClient;
import org.xml.sax.SAXException;

import lyan.jdbc.JDBCClient.Packet;

/**
 *
 * @author Артем
 */
public class CustomScreen extends JFrame {

    private JTable table; // таблица с группами
    private Faculty faculty; // факультет
    private ArrayList<Group> groups; // список групп 
    private ArrayList<Student> students; // список студентов
    private GroupTableModel groupTableModel;
    private StudentTableModel studentTableModel;
    private GroupEditDialog groupEditDialog;
    private GroupAddDialog groupAddDialog;
    private MouseListener addGroupMouseListener = new MouseListener() {
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
    private MouseListener editGroupMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            groupEditDialog.setVisible(true);
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
    private MouseListener removeGroupMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clicked on removeGroup");
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
            if (isGroup) {
                table.setModel(groupTableModel);
            } else {
                table.setModel(studentTableModel);
            }
            table.repaint();
            isGroup = !isGroup;
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
    private boolean isGroup = true; // сейчас отображается студенты
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

        table = new JTable(studentTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;// ведь редактируем через окно
            }
        };
        scrollPane = new JScrollPane(table);

        groupEditDialog = new GroupEditDialog(this, "Редактировать группу", "groupEditDialog");
        groupAddDialog = new GroupAddDialog(this, "Добавить группу", "groupAddDialog");
    }

    public void addStudent(String name, String sirName, String parentName, String groupIndex, String faculty) {
        students.add(new CustomStudent(name, sirName, parentName, groupIndex, faculty)); // TODO могут возникнуть проблемы
    }

    public void addStudent(String name, String sirName, String parentName, String groupIndex, String faculty, String birthDate) {
        CustomStudent cs = new CustomStudent(name, sirName, parentName, birthDate);
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
        ml.mouseListeners.put("addGroupMouseListener", addGroupMouseListener);
        ml.mouseListeners.put("editGroupMouseListener", editGroupMouseListener);
        ml.mouseListeners.put("removeGroupMouseListener", removeGroupMouseListener);

        ml.LoadMarkup("main", pane);
        // вложенные layout пока не поддерживаются

        studentTableModel.setStudents(students);

        table.setModel(studentTableModel);

        pane.add(scrollPane);

        scrollPane.setBounds(100, 100, 500, 100);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        this.groupEditDialog.initUI();
        groupAddDialog.initUI();

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
                if (!isGroup && table.getSelectedColumn() != -1) { // проверка выделенного

                    ArrayList<Packet> data = new ArrayList<Packet>();

                    data.add(new Packet(
                            "faculty",
                            input2.getText(),
                            "id",
                            table.getValueAt(table.getSelectedRow(), 2).toString()));
                    data.add(new Packet(
                            "groupindex",
                            input1.getText(),
                            "id",
                            table.getValueAt(table.getSelectedRow(), 2).toString()));
                    try {
                        dbClient.updateGroupData(data);
                    } catch (SQLException ex) {
                        Logger.getLogger(CustomScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                    dbClient.addGroupData(table.getRowCount(), groupIndex.getText(), faculty.getText());
                } catch (SQLException ex) {
                    // тут сделать вывод окошка
                    Logger.getLogger(CustomScreen.class.getName()).log(Level.SEVERE, null, ex);
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
}
