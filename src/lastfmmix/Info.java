package lastfmmix;

import Support.Icons;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author mars
 */

public class Info {

    private JFrame iFrame;
    private JLabel infoLabel;

    private String[] tags;
    private String[] similiar;
    private String[] info;
    private String picURL;

    private String text;

    Info(String artist){
        iFrame=new JFrame(artist);
        iFrame.setIconImage(Icons.Icon().getImage());
        
        tags=LFMSrv.getTags(artist);
        similiar=LFMSrv.getSimiliar(artist);
        info=LFMSrv.getInfo(artist);
        picURL=LFMSrv.getPicURL(artist);

        text="<html><body><div align=center><h3><b>"+artist+"</b></h3><br>"+
             "<img src="+picURL+"></div><br>"+
             "<b>Wiki</b>: "+nParse(info)+"<br>"+
             "<b>Tags</b>: "+nParse(tags)+"<br>"+
             "<b>Similiar</b>: "+nParse(similiar)+
             "</body></html>";

        infoLabel= new JLabel(text);

        iFrame.add(infoLabel);

        iFrame.pack();
        iFrame.setLocationRelativeTo(null);
        iFrame.setVisible(true);
    }

    private String nParse(String[] arr){
        if (arr.length==0) return "";
        String str="";
        for (int i=0;i<arr.length-1;i++)
            str+=arr[i]+", ";//+"<br>";
        str+=arr[arr.length-1];
        return str;
    }

}