package lastfmmix;

/*
 *
 * @author mars
 *
 */

import de.umass.lastfm.scrobble.ScrobbleData;
import java.util.LinkedList;
import java.util.Date;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Log_parse {
    
    private LinkedList<ScrobbleData> Data;
    private File F;

    final private boolean DEBUG=false;

    Log_parse(){
        Data=new LinkedList();
        F=null;
    }

    public LinkedList<ScrobbleData> getData(){
        if (Data.isEmpty()) return null;
        return Data;
    }

    public int getDataSize(){
        return Data.size();
    }

    public void clearData(){
        Data.clear();
    }

    private ScrobbleData parseString(String inp){
        if (DEBUG)  System.out.println("inp string:"+inp);
        if (inp.startsWith("#")) return null;
        String[] arr=inp.split("\t");
        if (arr.length!=7) return null;
        if (DEBUG)  for (int i=0;i<7;i++)     System.out.println("arr["+i+"]="+arr[i]);
        //artist+, track+, int timestamp+, int duration+, album+, albumArtist!, musicBrainzId!, int trackNumber, streamId!
        if (arr[3].equals("")) arr[3]="0";
        ScrobbleData temp=new ScrobbleData(arr[0],arr[2],Integer.parseInt(arr[6]),Integer.parseInt(arr[4]),arr[1],"","",Integer.parseInt(arr[3]),"");

        return temp;
    }

    public void parse(File file){
        try {
            F=file;
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(F)));

            while (dis.available() != 0) {
                ScrobbleData temp=parseString(dis.readLine());
                if (temp!=null){
                    Data.add(temp);
                    if (DEBUG)  System.out.println("parse temp:"+temp.toString());
                }
            }

            for (int i=Data.size();i<Data.size()-4;i--)
                Data.remove(i);

            dis.close();
            }

            catch (FileNotFoundException e) {
                e.printStackTrace();
                }
            catch (IOException e) {
                e.printStackTrace();
                }
    }

    public void deleteFile(){
        F.delete();
    }

    public void duplicateRemove(){

        long curTime=-1;
        int curDuration=-1;
        String curTrack="";

        long prevTime =       Data.getFirst().getTimestamp();
        int prevDuration =   Data.getFirst().getDuration();
        String prevTrack =      Data.getFirst().getTrack();

        for (int i=1;i<Data.size();i++){
            curTime = Data.get(i).getTimestamp();
            curDuration = Data.get(i).getDuration();
            curTrack =Data.get(i).getTrack();

            prevTime =       Data.get(i-1).getTimestamp();;
            prevDuration =   Data.get(i-1).getDuration();
            prevTrack =      Data.get(i-1).getTrack();

            if (!curTrack.equals(prevTrack)) continue;

            if ((curDuration==prevDuration) && (Math.abs(curTime-prevTime)<curDuration*0.5))
                Data.remove(i--);
        }
    }

    public void deleteRow(int[] arr){
        
        for (int i:arr)
            Data.get(i).setArtist("#deleteme#");

        for (int i=0; i< Data.size();i++)
            if (Data.get(i).getArtist().equals("#deleteme#"))
                Data.remove(i--);
    }

    public String[][] makeTable(){
        String[][] tableData=new String[Data.size()][7];

        for (int i=0;i<Data.size();i++)
            tableData[i]=makeArr(Data,i);

        //date converting
        Date d = new Date();
        for (int i=0;i<Data.size();i++){
            d.setTime(Long.parseLong(tableData[i][6])*1000);
            tableData[i][6]=d.toGMTString();
        }

        if (DEBUG){
            for (int i=0;i<tableData.length;i++) System.out.println(i+"=\t"+tableData[i][1]+"\t"+tableData[i][2]+"\n");
            System.out.println("size:"+Data.size());
            for (int i=0;i<Data.size();i++) System.out.println(i+":\t"+Data.get(i).getArtist()+"\t"+Data.get(i).getTrack());
        }

        return tableData;
    }

    private String[] makeArr(LinkedList<ScrobbleData> LL, int i){
        String[] temp= new String[7];
        ScrobbleData data=LL.get(i);
        temp[0]=""+(i+1);
        temp[1]=data.getArtist();
        temp[2]=data.getAlbum();
        temp[3]=data.getTrack();
        temp[4]=""+data.getTrackNumber();
        temp[5]=""+data.getDuration();
        temp[6]=""+data.getTimestamp();
        return temp;
    }
}