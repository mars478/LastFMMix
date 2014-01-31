package lastfmmix;

import de.umass.lastfm.scrobble.ScrobbleData;
import javax.swing.*;
import java.util.LinkedList;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author mars
 */

public class GUI_adapter {

    private GUI gui;
    private Log_parse lps;
    private Scrob scr;

    private boolean connect_status=true;

    private JFileChooser FC;

    GUI_adapter(GUI gui){

        LookAndFeelInfo[] lArr=UIManager.getInstalledLookAndFeels();
        try{
            for (LookAndFeelInfo laf:lArr)
                UIManager.setLookAndFeel(laf.getClassName());
            }

        catch(UnsupportedLookAndFeelException e){
            JOptionPane.showMessageDialog(null,"Failed to set LookAndFeel UIManager","GUI error: UnsupportedLookAndFeelException",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"Failed to set LookAndFeel UIManager","GUI error: ClassNotFoundException",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        catch (InstantiationException e) {
                JOptionPane.showMessageDialog(null,"Failed to set LookAndFeel UIManager","GUI error: InstantiationException",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        
        catch (IllegalAccessException e) {
                JOptionPane.showMessageDialog(null,"Failed to set LookAndFeel UIManager","GUI error: IllegalAccessException",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        this.lps=new Log_parse();
        this.gui=gui;
    }

    public synchronized void button_submit(){
        scr.reset();
            if (scr.getCheck()==false){
                gui.setLabel_cntTracks_Text("The scrobbler error");
                return;
                }

            if (lps.getData()==null){
                gui.setLabel_cntTracks_Text("The list is empty");
                return;
                }

            //start scrobbling
            Runnable Sbm=new Submitting(this);
            Thread t = new Thread(Sbm);

            gui.setAllbuttonsEnable(false);

            t.start();
    }

    public void button_submit_step2(){
        if (scr.getCheck()!=false)
                gui.setLabel_cntTracks_Text("Success");

        if (scr.getScrobble()==true)
                lps.deleteFile();

        gui.setAllbuttonsEnable(true);
    }

    public void button_openlog(JFrame mFrame)
    {
        lps.clearData();
        FC = new JFileChooser();
        FC.setMultiSelectionEnabled(false);
        int returnVal = FC.showOpenDialog(mFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            lps.parse(FC.getSelectedFile());
            if (lps.getData()!=null)
            {
                gui.resetTable();
                lps.duplicateRemove();
                gui.setLabel_cntTracks_Text("Total tracks:"+lps.getData().size());
                gui.clearTable();
                gui.fillTable(lps.getDataSize(),lps.makeTable());
                if ((scr!=null)&&((scr.getConnect()==true) && (lps.getData().size()!=0)))
                    gui.setButton_submit(true);
                else
                    gui.setButton_submit(false);
                gui.revalidateTable();
             }
         }

    }

    public void button_connect(){
        scr.setNullInstance();
        if (connect_status==true){
            gui.setButton_connect_Icon(1);
            scr=Scrob.getInstance(gui.getTextfieldsText()[0], gui.getTextfieldsText()[1]);
            if ((scr.getConnect()==true) && (lps.getData()!=null)){
                gui.setButton_submit(true);
                gui.setLabel_cntTracks_Text("Ready for scrobbling");
                connect_status=false;
                connected();
                gui.setButton_connect_Icon(2);
                }
            else {
                gui.setButton_submit(false);
                if (lps.getData()==null){
                    gui.setLabel_cntTracks_Text("Choose the log");
                    connect_status=false;
                    connected();
                    gui.setButton_connect_Icon(2);
                    }
                if (scr.getConnect()==false){
                    gui.setLabel_cntTracks_Text("Failed to login");
                    connect_status=true;
                    disconnected();
                    gui.setButton_connect_Icon(1);
                    }
                }
            gui.repaintJPanel();
            }
        else{
            gui.setLabel_cntTracks_Text("Ready");
            gui.setButton_submit(false);
            connect_status=true;
            disconnected();
            gui.setButton_connect_Icon(1);
        }
    }

    public void button_info(){
        new Copyright();
    }

    private void connected(){
        gui.setTextboxEditable(false);
        gui.setButton_connect_Text("Disconnect");
    }

    private void disconnected(){
        gui.setTextboxEditable(true);
        gui.setButton_connect_Text("Connect");
    }

    public Scrob getScrob(){
        return scr;
    }

    public LinkedList<ScrobbleData> getList(){
        return lps.getData();
    }

    public boolean table_remove_row(int[] i_arr){
        if (i_arr.length==0) return false;
        lps.deleteRow(i_arr);
        gui.clearTable();
        gui.fillTable(lps.getDataSize(),lps.makeTable());
        return true;
    }
}