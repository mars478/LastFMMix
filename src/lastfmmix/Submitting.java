package lastfmmix;

import de.umass.lastfm.scrobble.ScrobbleData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author mars
 *
 */

public class Submitting implements Runnable{

    private static ApplicationFrame sFrame;

    private JPanel sPanel;
    private JProgressBar sBar;
    private JLabel sLabel;
    private JButton sButton;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultPieDataset pieDataset;
    
    private Submitting_adapter sbm_adp;
    private GUI_adapter gui_adp;

    private boolean created=false;

    Submitting(GUI_adapter gui_adp){

        this.gui_adp=gui_adp;
        sFrame=new ApplicationFrame("Scrobbling in process...");
        sbm_adp=new Submitting_adapter(this);
        pieDataset = new DefaultPieDataset();
    }

    private void createGui(){

        sPanel  = new JPanel();
        sBar    = new JProgressBar();
        sLabel  = new JLabel();
        sButton = new JButton();

        sFrame.setPreferredSize(new Dimension(500,600));
        sFrame.setMinimumSize(new Dimension(500,600));

        sPanel.setPreferredSize(new Dimension(480,580));
        sPanel.setMinimumSize(new Dimension(480,580));
        sPanel.setLayout(new BoxLayout(sPanel,BoxLayout.Y_AXIS));

        sBar.setPreferredSize(new Dimension(400,30));
        sBar.setMinimumSize(new Dimension(400,30));
        sBar.setIndeterminate(false);
        sBar.setMinimum(0);
        sBar.setMaximum(sbm_adp.getListSize());
        sBar.setValue(0);

        sLabel.setPreferredSize(new Dimension(100,30));
        sLabel.setMinimumSize(new Dimension(100,30));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setText(0+"/"+0);

        sButton.setEnabled(false);
        sButton.setPreferredSize(new Dimension(80,20));
        sButton.setMinimumSize(new Dimension(80,20));
        sButton.setText("Close");
        sButton.addActionListener(new sButton_Listener());

        chart = ChartFactory.createPieChart("Сhart", pieDataset , true, true, false);
        chartPanel = new ChartPanel(chart);

        sPanel.add(sBar);
        sPanel.add(sLabel);
        sPanel.add(chartPanel);
        sPanel.add(sButton);
        sFrame.getContentPane().add(sPanel);
        sFrame.setLocationRelativeTo(null);
        sFrame.pack();
        sFrame.setVisible(true);
    }

    public void showChart(){
        sbm_adp.getPieDataset(pieDataset);
        sFrame.repaint();
    }

    public Submitting_adapter getSbm_adp(){
        return sbm_adp;
    }

    public void sFrame_repaint(){
        sFrame.repaint();
    }

    public void setSFrameText(String text){
        sFrame.setTitle(text);
    }

    public void setValue(int x){
        sBar.setValue(x);
        sLabel.setText(x+"//"+sbm_adp.getListSize());
        sBar.repaint();
        sLabel.repaint();
    }

    public void setSButtonEnabled(boolean x){
        sButton.setEnabled(x);
    }
    
    public void startScrobbling(){
        sbm_adp.startScrobbling();
    }

    public LinkedList<ScrobbleData> getList(){
        return gui_adp.getList();
    }

    public void run() {
        if (!created){
            createGui();
            created=true;
        }
        synchronized (gui_adp){
            startScrobbling();
            setSFrameText("Done");
            gui_adp.button_submit_step2();
            }
    }

    private static class sButton_Listener implements ActionListener {
        public sButton_Listener() {}

        public void actionPerformed(ActionEvent e) {
            sFrame.dispose();
        }
    }
}