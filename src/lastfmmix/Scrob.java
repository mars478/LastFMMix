package lastfmmix;

import Support.Variables;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Result.Status;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleData;
import de.umass.lastfm.scrobble.ScrobbleResult;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mars
 */
public class Scrob {

    private static Scrob instance=null;

    private Session session;
    private List<ScrobbleResult> resultList;
    private LinkedList<ScrobbleData> scrobbleList;

    private boolean check=true;
    private boolean connect=false;
    private boolean scrobbled=false;
    private String error = null;
    
    private boolean DEBUG=true;
    
    private Scrob(String username, String password){
        resultList=new LinkedList();
        scrobbleList=new LinkedList();
        session=Authenticator.getMobileSession(username, password,Variables.getAPIKey(), Variables.getSecret());
        try{
            if (DEBUG) System.out.println("session key:"+session.getKey());
            new String(session.getKey()+"");
            connect=true;
            }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Failed to connect","Check your connection and user data.\n" + e.getMessage() ,JOptionPane.ERROR_MESSAGE);
            connect=false;
        }

    }
    
    public static Scrob getInstance(String username, String password){
        if (instance==null) instance= new Scrob(username, password);
        return instance;
    }

    public static Scrob getInstance(){
        return instance;
    }

    public static void setNullInstance(){
        instance=null;
    }

    public void doScrob(ScrobbleData sd,boolean last){
        scrobbled=false;
        scrobbleList.add(sd);
        if ((scrobbleList.size()>40) || (last)){
                 resultList=Track.scrobble(scrobbleList, session);
                 ScrobbleResult[] debug=new ScrobbleResult[resultList.size()];
                 resultList.toArray(debug);
                 int i=0;
                 for (ScrobbleResult sd_deb:debug){
                     Status stts=sd_deb.getStatus();
                     if ((stts.toString().equals(stts.FAILED))||( sd_deb.getIgnoredMessage()!=null)){
                         if (DEBUG) System.out.println("Error:"+stts.toString()+"\n"+ sd_deb.getIgnoredMessage()+"\n");
                         if (sd_deb.getIgnoredMessage()!=null) error = sd_deb.getIgnoredMessage();
                         check=false;
                         return;
                         }
                     if (DEBUG) System.out.println("Scrob:i="+(i++)+":\tIgnoredMessage:"+sd_deb.getIgnoredMessage()+"\t|\tStatus:"+stts.toString());
                 }
             if (!last) reset();
             }
        scrobbled=true;
    }

    public boolean getConnect(){
        return connect;
    }
    
    public boolean getCheck(){
        return check;
    }

    public boolean getScrobble(){
         return scrobbled;
    }

    public String getError()
    {
        return error;
    }

    public void reset(){
        scrobbled=false;
        check=true;
        resultList.clear();
        scrobbleList.clear();
    }
}