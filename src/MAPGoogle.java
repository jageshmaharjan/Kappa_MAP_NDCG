import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

/**
 * Created by jagesh on 03/28/2016.
 * average point precision and 11-point interpolated precision for Google
 */
public class MAPGoogle
{
    public static ArrayList<Long> judgeJ = new ArrayList<Long>();
    public static ArrayList<Long> judgeL = new ArrayList<Long>();
    public static ArrayList<Long> judgeO = new ArrayList<Long>();
    public static ArrayList<Long> judgeF = new ArrayList<Long>();

    public static void main(String[] args) throws IOException
    {
        MAPGoogle mapGoogle = new MAPGoogle();
        mapGoogle.readJsonfile();

        mapGoogle.relevalanceCheck(judgeJ,judgeL,judgeO,judgeF);
    }

    public void readJsonfile() throws IOException
    {
        String path = "E:\\Tsinghua Masters in Adv. Computing\\Web Information Retrival\\Assignment\\Assignment-4\\Question\\task";
        File dir = new File(path);
        if (dir.isDirectory())
        {
            for (File file : dir.listFiles())
            {
                String[] filenamesplit = file.getName().split("_|\\.");
                if (filenamesplit[1].equals("2015280258") && filenamesplit[0].equals("VSM") && filenamesplit[2].equals("1"))
                {
                    System.out.println(file.getName());
                    readfile(path+"\\"+file.getName(),filenamesplit[2]);
                }
            }
        }
    }

    public static void readfile(String fname, String queryid) throws IOException
    {
        File file = new File(fname);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str=null;
        while ((str=br.readLine()) != null)
        {
            parseJson(str);
        }
    }

    private static void parseJson(String str)
    {
        Long f = null;
        Long j=null;
        Long l=null;
        Long o =null;

        try
        {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(str);
            JSONObject jsonobject = (JSONObject) object;

            String engine = (String) jsonobject.get("se");

            JSONObject jo2 = (JSONObject) jsonobject.get("annotations");

            j= (Long) jo2.get("J");
            f = (Long) jo2.get("F");
            l = (Long) jo2.get("L");
            o = (Long) jo2.get("O");

        }
        catch (Exception e)
        {   System.out.println("Err: " + e);       }

        judgeF.add(f);
        judgeJ.add(j);
        judgeL.add(l);
        judgeO.add(o);
    }

    //To calculate the Array of relevance and non-relevance document in the given rank
    public void relevalanceCheck(ArrayList<Long> judgeJ,ArrayList<Long> judgeL,ArrayList<Long> judgeO,ArrayList<Long> judgeF)
    {
        int[] relevalance = new int[judgeJ.size()];

        for (int i=0;i<judgeJ.size();i++)
        {
            float average = (float) ((judgeJ.get(i) + judgeL.get(i) + judgeO.get(i) + judgeF.get(i))/4.0);

            if (average > 1.0)
                relevalance[i] = 1;
            else
                relevalance[i] = 0;
        }

        averagePrecision(relevalance);
    }

    //To evaluate the 11-point interpolated average precision
    public void averagePrecision(int[] relevalance)
    {
        float[] recall = new float[relevalance.length/2];
        float[] precision = new float[relevalance.length/2];
        int count =0;
        float avgPrecision = 0;

        //count the number of relevance document in a Bing
        for (int i=10;i<relevalance.length;i++)
        {
            if (relevalance[i] == 1 )
            {
                count += 1;
            }
        }

        int j = 0;
        //calculate the recall curve, from the relevance documents
        for (int i=10;i<relevalance.length;i++)
        {
            if (i == 10)
            {
                if (relevalance[i] == 1)
                    recall[j] = (float) (1.0/count);
                else
                    recall[j] = 0;
            }
            else
            {
                if (relevalance[i] == 1)
                    recall[j] = (float) (recall[j-1] + (1.0/count));
                else
                    recall[j] = recall[j-1];
            }
            j++;
        }

        int k =0;
        //calculate the precision from the relevance documents
        for (int i =10;i<relevalance.length;i++)
        {
            if (i == 10)
            {
                if (relevalance[i] == 1)
                    precision[k] = 1;
                else
                    precision[k] = 0;
            }
            else
            {
                precision[k] = calcPrecision(i,relevalance);
            }
            k++;
        }

        //calculate the average precision
        float pSum = 0;
        for (int i=0;i<precision.length;i++)
        {
            pSum += precision[i];
        }
        avgPrecision = pSum / precision.length;

        precision11Point(recall,precision,relevalance);
    }

    //calculate the precision at a given point, and a relevalance
    public float calcPrecision(int i,int[] relevalance)
    {
        int relcount = 0;
        int nonrelcount = 0;
        for (int j=0;j<=i;j++)
        {
            if (relevalance[j] == 1)
                relcount++;
            else
                nonrelcount++;
        }
        float precision = (relcount/(float)(relcount+nonrelcount));

        return precision;
    }

    //To evaluate the 11-point interpolated average precision
    public void precision11Point(float[] recall, float[] precision,int[] relevalance)
    {
        float[] recall11 = {(float) 0.0, (float) 0.1, (float) 0.2, (float) 0.3, (float) 0.4, (float) 0.5, (float) 0.6, (float) 0.7, (float) 0.8, (float) 0.9, (float) 1.0};
        float[] precision11T = new float[recall11.length];
        float[] precision11 = new float[recall11.length];
        float avgprecision11 = 0;
        Map<Float,Float> map = new TreeMap<Float, Float>();
        int p=0;

        //map object to hold key-value pair, for those document that are relevalent
        for (int i=10;i<relevalance.length;i++)
        {
            if (relevalance[i] == 1)
            {
                map.put(recall[p],precision[p]);
            }
            p++;
        }

        //calculate 11-point interpolated precision with gaping from the average-precision curve with map object
        Set set = map.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext())
        {
            Map.Entry entry = (Map.Entry) i.next();
            Float val = (Float) entry.getKey();
            for (int j=0;j<recall11.length; j++)
            {
                if (recall11[j] < (Float) entry.getKey() && (Float) entry.getKey()<=recall11[j+1])
                {
                    precision11T[j+1] = (Float) entry.getValue();
                }
            }
        }

        //calculating 11-point average precision with comparison to gap-11-point precision
        for (int k =precision11T.length-1; k>=0 ;k--)
        {
            if (k == precision11T.length-1)
            {
                precision11[k] = precision11T[k];
            }
            else
            {
                if (precision11[k+1] > precision11T[k])
                    precision11[k] = precision11[k+1];
                else
                    precision11[k] = precision11T[k];
            }
        }

        //calculating average precision of 11-point interpolated
        float sumPrecision11 = 0;
        for (int k=0;k<precision11.length; k++)
        {
            sumPrecision11 += precision11[k];
        }
        avgprecision11 = sumPrecision11/(precision11.length);
        System.out.println(avgprecision11);

        try {
            writeToFile(avgprecision11);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(float avgprecision11) throws IOException
    {
        String path = "E:\\Tsinghua Masters in Adv. Computing\\Web Information Retrival\\Assignment\\Assignment-4\\Answer\\output\\output.txt";
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(path,true));
            bw.write("'MAP': ");
            bw.write(String.valueOf(avgprecision11)+ ", ");
            bw.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error copying: " + e);
        }
        finally
        {
            if (bw != null)
                bw.close();
        }
    }
}
