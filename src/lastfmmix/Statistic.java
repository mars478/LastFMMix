package lastfmmix;

import de.umass.lastfm.scrobble.ScrobbleData;
import java.util.LinkedList;
import java.util.HashSet;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author mars
 */

public class Statistic {
    
    private LinkedList<ScrobbleData> Data;
    private HashSet<String> Artists_hash;
    
    Statistic(LinkedList<ScrobbleData> Data){
        Artists_hash=new HashSet();
        this.Data=Data;
    }

    private LinkedList<String[]> getChartList(){

        int Artists_count[];
        String Artists_arr[];
        LinkedList<String[]> chartList=new LinkedList();

        for (int i=0;i<Data.size();i++)
             if (!Artists_hash.contains(Data.get(i).getArtist()))
                 Artists_hash.add(Data.get(i).getArtist());

         Artists_arr= new String[Artists_hash.size()];
         Artists_hash.toArray(Artists_arr);
         Artists_count=new int[Artists_arr.length];

         for (int i=0;i<Artists_arr.length;i++)
             for (int j=0;j<Data.size();j++)
                 if (Artists_arr[i].equals(Data.get(j).getArtist()))
                     Artists_count[i]++;

         if (Artists_arr.length>1){
            for (int i=0;i<Artists_count.length;i++)
                for (int j=i;j<Artists_count.length;j++)
                    if (Artists_count[i]<Artists_count[j]){
                        int temp_i=Artists_count[i];
                        Artists_count[i]=Artists_count[j];
                        Artists_count[j]=temp_i;

                        String temp_s=Artists_arr[i];
                        Artists_arr[i]=Artists_arr[j];
                        Artists_arr[j]=temp_s;
                    }

            if (Artists_count.length>10){
                int other=0;
                for (int i=9;i<Artists_count.length;i++)
                    other+=Artists_count[i];
                Artists_count[9]=other;
            }
         }

         for (int i=0;(i<Artists_count.length) & (i<8);i++)
             chartList.add(new String[]{Artists_arr[i],""+Artists_count[i]});
         if (Artists_count.length==10)
            chartList.add(new String[]{Artists_arr[9],""+Artists_count[9]});
         if (Artists_count.length>10)
            chartList.add(new String[]{"The rest",""+Artists_count[9]});

         return chartList;
    }

    public void getPieDataset(DefaultPieDataset pieDataset){

        LinkedList<String[]> chartList = getChartList();

        for (int i=0;i<chartList.size();i++){
            try{
                String temp[]=chartList.get(i);
                pieDataset.setValue(temp[0], Integer.parseInt(temp[1]));
                }
            catch(NumberFormatException e){
                e.printStackTrace();
                break;
            }
        }
    }

}