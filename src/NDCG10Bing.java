import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by jagesh on 03/30/2016.
 * Discounted cumulative Gain (DCG) and Normalized Discounted cumulative Gain (NDCG@10) for Bing
 */
public class NDCG10Bing
{
    public static ArrayList<Long> judgeJ = new ArrayList<Long>();
    public static ArrayList<Long> judgeL = new ArrayList<Long>();
    public static ArrayList<Long> judgeO = new ArrayList<Long>();
    public static ArrayList<Long> judgeF = new ArrayList<Long>();

    public static void main(String[] args) throws IOException
    {
        NDCG10Bing ndcg10Bing = new NDCG10Bing();

        ndcg10Bing.readJsonfile();

        ndcg10Bing.thresholdMapping(judgeJ,judgeL,judgeO,judgeF);
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

    //To calculate the threshold mapping from Annotators
    public void thresholdMapping(ArrayList<Long> judgeJ,ArrayList<Long> judgeL,ArrayList<Long> judgeO,ArrayList<Long> judgeF) throws IOException
    {
        int[] threshold = new int[judgeJ.size()];

        for (int i=0;i<judgeJ.size();i++)
        {
            float average = (float) ((judgeJ.get(i) + judgeL.get(i) + judgeO.get(i) + judgeF.get(i))/4.0);

            if (average >= 2.0)
                threshold[i] = 3;
            else if (average > 0.5 && average < 2.0)
                threshold[i] = 2;
            else if (average > 0.0 && average <= 0.50)
                threshold[i] = 1;
            else if (average < 0.5 )
                threshold[i] = 0;
        }

        float[] dcg = DCG(threshold);
        float[] idealdcg = idealNDCG(threshold);

        NDCG(dcg,idealdcg);
    }

    public float[] DCG(int[] threshold)
    {
        float[] discountedgain = new float[threshold.length/2];
        float[] dcg = new float[threshold.length/2];
        for (int i = 0; i< threshold.length/2; i++)
        {
            if (i == 0)
                discountedgain[i] = (float) threshold[i];
            else
                discountedgain[i] = (float) (threshold[i]/((float)Math.log(i+1)/Math.log(2)));
        }

        for (int i=0;i<threshold.length/2;i++)
        {
            if (i == 0)
                dcg[i] = discountedgain[i];
            else
                dcg[i] = dcg[i-1] + discountedgain[i];
        }

        return dcg;
    }

    public float[] idealNDCG(int[] threshold)
    {
        Integer[] perfectranking = new Integer[threshold.length/2];
        float[] discountedgain = new float[threshold.length/2];
        float[] idealdcg = new float[threshold.length/2];

        for (int i=0;i<threshold.length/2;i++)
        {
            perfectranking[i] = threshold[i];
        }
        Arrays.sort(perfectranking, Collections.reverseOrder());

        for (int i = 0; i< perfectranking.length; i++)
        {
            if (i == 0)
                discountedgain[i] = (float) perfectranking[i];
            else
                discountedgain[i] = (float) (perfectranking[i]/((float)Math.log(i+1)/Math.log(2)));
        }

        for (int i=0;i<threshold.length/2;i++)
        {
            if (i == 0)
                idealdcg[i] = discountedgain[i];
            else
                idealdcg[i] = idealdcg[i-1] + discountedgain[i];
        }

        return idealdcg;
    }

    public void NDCG(float[] dcg,float[] idealdcg) throws IOException
    {
        float[] ndcg = new float[dcg.length];

        for (int i=0; i<dcg.length; i++ )
        {
            ndcg[i] = dcg[i]/idealdcg[i];
        }
        //System.out.println(Arrays.toString(ndcg));
        evaluateAvgNDGC10(ndcg);
    }

    public void evaluateAvgNDGC10(float[] ndcg) throws IOException
    {
        float avgndcg=0;
        float sum=0;
        for (int i=0;i<ndcg.length;i++)
        {
            sum += ndcg[i];
        }
        avgndcg = (sum/ndcg.length);
//        System.out.println(avgndcg);
        writeToFile(avgndcg);
    }


    public void writeToFile(float avgkappa) throws IOException
    {
        String path = "E:\\Tsinghua Masters in Adv. Computing\\Web Information Retrival\\Assignment\\Assignment-4\\Answer\\output\\output.txt";
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(path,true));
            bw.write("'NDCG10': ");
            bw.write(String.valueOf(avgkappa)+ ", ");
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
