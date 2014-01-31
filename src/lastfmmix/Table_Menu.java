package lastfmmix;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author mars
 *
 */

public class Table_Menu extends JPopupMenu{
    
    private static JPopupMenu tPMenu;
    private static GUI_adapter gui_adp;

    private static int[] i_arr;

    Table_Menu(int[] i_arr, GUI_adapter gui_adp, Point pnt){
        super();

        tPMenu = this;
        this.i_arr=i_arr;
        this.gui_adp=gui_adp;

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new Delete_Listener());
        add(deleteItem);

        JMenuItem infoItem = new JMenuItem("Get info");
        infoItem.addActionListener(new Info_Listener());
        add(infoItem);

        //addMouseListener(new tFrame_Listener());

        setVisible(true);
        pack();
        this.setLocation(pnt);
        setVisible(true);
    }

    private static class Delete_Listener implements ActionListener {

        public Delete_Listener() {}

        public void actionPerformed(ActionEvent e) {
            tPMenu.setVisible(false);
            if (gui_adp.getList() != null)
                gui_adp.table_remove_row(i_arr);
        }
    }

    private static class Info_Listener implements ActionListener {

        public Info_Listener() {}

        public void actionPerformed(ActionEvent e) {
            
            tPMenu.setVisible(false);
            if ((gui_adp.getList() != null) && (!gui_adp.getList().get(i_arr[0]).getArtist().equals("")))
                new Info(gui_adp.getList().get(i_arr[0]).getArtist());
        }
    }
}