package lastfmmix;

import Support.Icons;
import Support.GUIText;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/*
 *
 * @author mars
 *
 */

public class GUI{

    private static JFrame mainFrame;
    private JButton button_submit; //submit all
    private JButton button_openlog; //open .log
    private JButton button_quit; //quit
    private JButton button_connect; //connect to last.fm
    private JButton button_info; //show info
    private JLabel label_cntTracks; //count of tracks
    private JLabel label_usr; //username`s field
    private JLabel label_psw; //password`s field
    private JTextField textField_usr; //username
    private JPasswordField textField_psw; //password
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private DefaultTableModel TM;
    private JPanel jPanel1;

    private GUI_adapter gui_adp;



    GUI()
    {
        gui_adp=new GUI_adapter(this);

        mainFrame       = new JFrame();
        button_submit   = new JButton();
        button_openlog  = new JButton();
        button_quit     = new JButton();
        button_connect  = new JButton();
        button_info     = new JButton();
        jScrollPane1    = new JScrollPane();
        jTable1         = new JTable();
        label_cntTracks = new JLabel();
        label_usr       = new JLabel();
        label_psw       = new JLabel();
        jPanel1         = new JPanel();
        textField_usr   = new JTextField();
        textField_psw   = new JPasswordField();

        mainFrame.setTitle(GUIText.getTitle());
        mainFrame.setIconImage(Icons.Icon().getImage());
        mainFrame.setPreferredSize(new Dimension(950,480));
        mainFrame.setMinimumSize(new Dimension(950,480));
        mainFrame.setLayout(new BorderLayout());

        mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button_submit.setText(GUIText.getButtons()[0]);
        button_submit.setSize(100,25);
        button_submit.setIcon(Icons.Submit());
        button_submit.setEnabled(false);
        button_submit.addActionListener(new Button_submit_Listener());

        button_openlog.setText(GUIText.getButtons()[1]);
        button_openlog.setSize(100,25);
        button_openlog.setIcon(Icons.Openlog());
        button_openlog.addActionListener(new Button_openlog_Listener());

        button_quit.setText(GUIText.getButtons()[2]);
        button_quit.setSize(100,25);
        button_quit.setIcon(Icons.Quit());
        button_quit.addActionListener(new Button_quit_Listener());

        button_connect.setText(GUIText.getButtons()[3]);
        button_connect.setSize(100,25);
        button_connect.setIcon(Icons.Connect());
        button_connect.addActionListener(new Button_connect_Listener());

        button_info.setText(GUIText.getButtons()[4]);
        button_info.setSize(100,25);
        button_info.setIcon(Icons.Info());
        button_info.addActionListener(new Button_info_Listener());

        label_cntTracks.setText(GUIText.getButtons()[5]);
        label_cntTracks.setHorizontalAlignment(JLabel.CENTER);
        label_cntTracks.setPreferredSize(new Dimension(100, 25));

        label_usr.setText(GUIText.getLogin()[0]);
        label_usr.setSize(80,25);

        label_psw.setText(GUIText.getLogin()[1]);
        label_psw.setSize(80,25);

        textField_usr.setMinimumSize(new Dimension(100, 25));
        textField_usr.setPreferredSize(new Dimension(100, 25));

        textField_psw.setMinimumSize(new Dimension(100, 25));
        textField_psw.setPreferredSize(new Dimension(100, 25));

        Object[] columnNames = GUIText.getTableHeader();
        Object[][] data = {  {"","","","","","",""}
                            ,{"","","","","","",""}};
        TableModel model = new DefaultTableModel(data, columnNames);
        jTable1 = new JTable(model);
        jScrollPane1 = new JScrollPane(jTable1);
        mainFrame.getContentPane().add(jScrollPane1);
        TM=(DefaultTableModel)jTable1.getModel();
        jTable1.addMouseListener(new Table_Listener());
        jTable1.getColumnModel().getColumn(0).setMaxWidth(25);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(6).setMinWidth(140);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(140);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setResizable(false);
        jTable1.getColumnModel().getColumn(5).setResizable(false);
        jTable1.getColumnModel().getColumn(6).setResizable(false);
        
        jPanel1.setLayout(new FlowLayout());
        jPanel1.add(button_info);
        jPanel1.add(label_usr);
        jPanel1.add(textField_usr);
        jPanel1.add(label_psw);
        jPanel1.add(textField_psw);
        jPanel1.add(button_connect);
        jPanel1.add(label_cntTracks);
        jPanel1.add(button_submit);
        jPanel1.add(button_openlog);
        jPanel1.add(button_quit);
        
        mainFrame.getContentPane().add(jPanel1,BorderLayout.NORTH);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    class Button_openlog_Listener implements ActionListener {
        Button_openlog_Listener() {} //open .log button

        public void actionPerformed(ActionEvent e) {
                gui_adp.button_openlog(mainFrame);
            }
        }

    class Button_connect_Listener implements ActionListener {
        Button_connect_Listener() {} //connect button

        public void actionPerformed(ActionEvent e) {
                gui_adp.button_connect();
            }
        }

    class Button_submit_Listener implements ActionListener {
        Button_submit_Listener() {} //submit button
        
        public void actionPerformed(ActionEvent e) {
            gui_adp.button_submit();
            }
        }

    class Button_quit_Listener implements ActionListener {
        Button_quit_Listener() {} // quit button

        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            System.exit(0);
            }
        }

    class Button_info_Listener implements ActionListener {
        Button_info_Listener() {} // info button

        public void actionPerformed(ActionEvent e) {
            gui_adp.button_info();
            }
        }

    class Table_Listener implements MouseListener {

        private Table_Menu tm;

        Table_Listener() {} // on-table clicking

        public void actionPerformed(ActionEvent e) {}

        public void mouseClicked(MouseEvent e){
            if (tm!=null) tm.setVisible(false);
            if (jTable1.getSelectedRows().length==0) return;
            if (e.getButton()==e.BUTTON3)
                tm=new Table_Menu(jTable1.getSelectedRows(), gui_adp, e.getLocationOnScreen());
        }

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
        }

    public String[] getTextfieldsText(){
        return (new String[]{textField_usr.getText(),textField_psw.getText()});
    }

    public void setAllbuttonsEnable(boolean x){
        button_submit.setEnabled(x);
        button_openlog.setEnabled(x);
        button_quit.setEnabled(x);
        button_connect.setEnabled(x);
    }

    public void setLabel_cntTracks_Text(String text){
        label_cntTracks.setText(text);
    }
    
    public void setButton_connect_Text(String text){
        button_connect.setText(text);
    }

    public void setButton_connect_Icon(int x){
        switch (x){
            case 1: button_connect.setIcon(Icons.Connect());    break;
            case 2: button_connect.setIcon(Icons.Disconnect()); break;
                }
    }

    public void setButton_submit(boolean x){
        button_submit.setEnabled(x);
    }

    public void clearTable(){
        TM.setRowCount(0);
    }

    public void resetTable(){
        clearTable();
        TM.addRow(new String[]{"","","","","","",""});
        TM.addRow(new String[]{"","","","","","",""});
    }

    public void fillTable(int size, String[][] data){
        for (int i=0;i<size;i++)
            TM.addRow(data[i]);
    }

    public void revalidateTable(){
        jTable1.revalidate();
        jTable1.repaint();
        mainFrame.repaint();
    }

    public void repaintJPanel(){
        jPanel1.repaint();
    }

    public void setTextboxEditable(boolean x){
        textField_psw.setEditable(x);
        textField_usr.setEditable(x);
    }
}