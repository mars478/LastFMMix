package lastfmmix;

import Support.Variables;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Image;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Tag;
import java.util.ArrayList;

/**
 *
 * @author mars
 */

public class LFMSrv {

    static public String[] getSimiliar(String artist){
        Artist[] arr;
        String[] str;

        ArrayList<Artist> list=(ArrayList<Artist>) Artist.getSimilar(artist, Variables.getAPIKey());
        if (list.size()<10)
            arr=new Artist[list.size()];
        else
            arr=new Artist[10];
        for (int i=0;i<arr.length;i++)
            arr[i]=list.get(i);

        str=new String[arr.length];

        int i=0;
        for (Artist temp:arr)
            str[i++]=temp.getName();

        return str;
    }

    static public String[] getTags(String artist){
        Tag[] arr;
        String[] str;

        ArrayList<Tag> list=(ArrayList<Tag>) Artist.getTopTags(artist,Variables.getAPIKey());
        if (list.size()<10)
            arr=new Tag[list.size()];
        else
            arr=new Tag[10];
        for (int i=0;i<arr.length;i++)
            arr[i]=list.get(i);
        str=new String[arr.length];

        int i=0;
        for (Tag temp:arr)
            str[i++]=temp.getName();

        return str;
    }

    static public String[] getInfo(String artist){
        String str;
        String[] arr;

        str=Artist.getInfo(artist, Variables.getAPIKey()).getWikiSummary();//.getWikiText();

        arr= new String[(str.length()+(50-1))/50];
        if (str.length()>50){
            int j=0;
            for (int i=0;i<str.length();i++)
                if (i>50*(j+1))
                    arr[j++]=str.substring(0,i);
        }
        return arr;
    }

    static public String getPicURL(String artist){
        ArrayList<Image> list=(ArrayList<Image>)Artist.getImages(artist, Variables.getAPIKey()).getPageResults();
        return list.get(0).getImageURL(ImageSize.EXTRALARGE);//.getUrl();
    }
}