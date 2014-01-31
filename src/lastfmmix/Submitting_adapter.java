package lastfmmix;

import de.umass.lastfm.scrobble.ScrobbleData;
import javax.swing.JOptionPane;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author mars
 */

public class Submitting_adapter {

    private Submitting sbm;
    private Statistic stat;
    private boolean scrobbling=false;

    Submitting_adapter(Submitting sbm){
        this.sbm=sbm;
        this.stat=new Statistic(sbm.getList());
    }

    public void startScrobbling(){
        sbm.sFrame_repaint();
        for (int i=0;i<sbm.getList().size();i++)
                {
                ScrobbleData temp=sbm.getList().get(i);
                if (Scrob.getInstance().getCheck()==false)
                    break;
                Scrob.getInstance().doScrob(temp, i+1==sbm.getList().size());
                scrobble(i+1);
                if (Scrob.getInstance().getScrobble()==false)
                    break;
                }
    }

    private void scrobble(int x){
        if (scrobbling==false)
            { //there is a singletone pattern realization lol
            sbm.showChart();
            scrobbling=true;
            }
        sbm.setValue(x);
        if (((x==sbm.getList().size()) || (x<0)) || (!Scrob.getInstance().getCheck())){
            if (!Scrob.getInstance().getCheck())
            {
                String errorText = "Failed to submit";
                if (Scrob.getInstance().getError()!=null) errorText="Failed to submit:\n" + Scrob.getInstance().getError();
                JOptionPane.showMessageDialog(null, errorText , "Error" ,JOptionPane.ERROR_MESSAGE);
            }
            sbm.setSButtonEnabled(true);
            }
        sbm.sFrame_repaint();
    }

    public void getPieDataset(DefaultPieDataset pieDataset){
        stat.getPieDataset(pieDataset);
    }

    public int getListSize(){
        return sbm.getList().size();
    }
}