import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jagesh on 03/27/2016.
 * Kappa-2 calculation for Google
 */
public class Kappa2Google
{
    public static ArrayList<Long> judgeJ = new ArrayList<Long>();
    public static ArrayList<Long> judgeL = new ArrayList<Long>();
    public static ArrayList<Long> judgeO = new ArrayList<Long>();
    public static ArrayList<Long> judgeF = new ArrayList<Long>();

    public static void main(String[] args) throws IOException
    {
        Kappa2Google kp2google = new Kappa2Google();

        kp2google.readJsonfile();
        kp2google.binaryArrayconversion(judgeJ,judgeL,judgeO,judgeF);
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

    public void binaryArrayconversion(ArrayList<Long> judgeJ,ArrayList<Long> judgeL,ArrayList<Long> judgeO,ArrayList<Long> judgeF)
    {
        int[] judJ = new int[judgeJ.size()];
        int[] judL = new int[judgeL.size()];
        int[] judO = new int[judgeO.size()];
        int[] judF = new int[judgeF.size()];

        for (int i=0;i<judgeJ.size();i++)
        {
            if (judgeJ.get(i) > 0 )
            {
                judJ[i] = 1;
            }
            else
            {
                judJ[i] = 0;
            }
            if (judgeL.get(i) > 0 )
            {
                judL[i] = 1;
            }
            else
            {
                judL[i] = 0;
            }
            if (judgeO.get(i) > 0 )
            {
                judO[i] = 1;
            }
            else
            {
                judO[i] = 0;
            }
            if (judgeF.get(i) > 0 )
            {
                judF[i] = 1;
            }
            else
            {
                judF[i] = 0;
            }
        }

        float kappa2_JL = evaluate_Kappa2_JL(judJ,judL);
        float kappa2_JO = evaluate_Kappa2_JO(judJ,judL);
        float kappa2_JF = evaluate_Kappa2_JF(judJ,judF);
        float kappa2_LO = evaluate_Kappa2_LO(judL,judO);
        float kappa2_LF = evaluate_Kappa2_LF(judL,judF);
        float kappa2_OF = evaluate_Kappa2_OF(judO,judF);

        evaluate_avgKappa(kappa2_JL,kappa2_JO,kappa2_JF,kappa2_LO,kappa2_LF,kappa2_OF);
    }

    public float evaluate_Kappa2_JL(int[] judJ,int[] judL)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google=0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;

        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == judL[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judJ.length/2);

        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == 0)
            {
                if (judL[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judL[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judJ[i] == 1)
            {
                if (judL[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judL[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judL.length;i++)
        {
            if (judL[i] == 0)
            {
                if (judJ[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judL[i] == 1)
            {
                if (judJ[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }
        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google - PE_Google)/(1 - PE_Google);

        return Kappa2;
    }

    public float evaluate_Kappa2_JO(int[] judJ,int[] judO)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google =0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;
        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == judO[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judJ.length/2);

        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == 0)
            {
                if (judO[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judO[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judJ[i] == 1)
            {
                if (judO[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judO[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judO.length;i++)
        {
            if (judO[i] == 0)
            {
                if (judJ[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judO[i] == 1)
            {
                if (judJ[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }

        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google - PE_Google)/(1-PE_Google);

        return Kappa2;
    }

    public float evaluate_Kappa2_JF(int[] judJ, int[] judF)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google =0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;
        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == judF[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judJ.length/2);

        for (int i=10;i<judJ.length;i++)
        {
            if (judJ[i] == 0)
            {
                if (judF[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judJ[i] == 1)
            {
                if (judF[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judF.length;i++)
        {
            if (judF[i] == 0)
            {
                if (judJ[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judF[i] == 1)
            {
                if (judJ[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judJ[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }
        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google-PE_Google)/(1-PE_Google);

        return Kappa2;
    }

    public float evaluate_Kappa2_LO(int[] judL, int[] judO)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google=0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;
        for (int i=10;i<judL.length;i++)
        {
            if (judL[i] == judO[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judL.length/2);

        for (int i=10;i<judL.length;i++)
        {
            if (judL[i] == 0)
            {
                if (judO[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judO[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judL[i] == 1)
            {
                if (judO[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judO[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judO.length;i++)
        {
            if (judO[i] == 0)
            {
                if (judL[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judL[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judO[i] == 1)
            {
                if (judL[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judL[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }
        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google - PE_Google)/(1-PE_Google);

        return Kappa2;
    }

    public float evaluate_Kappa2_LF(int[] judL,int[] judF)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google=0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;
        for (int i=10;i<judL.length;i++)
        {
            if (judL[i] == judF[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judL.length/2);

        for (int i=10;i<judL.length;i++)
        {
            if (judL[i] == 0)
            {
                if (judF[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judL[i] == 1)
            {
                if (judF[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judF.length;i++)
        {
            if (judF[i] == 0)
            {
                if (judL[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judL[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judF[i] == 1)
            {
                if (judL[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judL[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }
        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google-PE_Google)/(1-PE_Google);

        return Kappa2;
    }

    public float evaluate_Kappa2_OF(int[] judO,int[]judF)
    {
        int PA_Google_calc = 0;
        float PA_Google =0;
        float PE_Google=0;
        float[] Vsum = new float[2];
        float[] Hsum = new float[2];
        float Kappa2 =0;
        for (int i=10;i<judO.length;i++)
        {
            if (judO[i] == judF[i])
            {
                PA_Google_calc += 1;
            }
        }

        PA_Google = PA_Google_calc/((float)judO.length/2);

        for (int i=10;i<judO.length;i++)
        {
            if (judO[i] == 0)
            {
                if (judF[i] == 0)
                {
                    Hsum[0] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[0] += 1;
                }
            }
            if (judO[i] == 1)
            {
                if (judF[i] == 0)
                {
                    Hsum[1] += 1;
                }
                if (judF[i] == 1)
                {
                    Hsum[1] += 1;
                }
            }
        }

        for (int i=10;i<judF.length;i++)
        {
            if (judF[i] == 0)
            {
                if (judO[i] == 0)
                {
                    Vsum[0] += 1;
                }
                if (judO[i] == 1)
                {
                    Vsum[0] += 1;
                }
            }
            if (judF[i] == 1)
            {
                if (judO[i] == 0)
                {
                    Vsum[1] += 1;
                }
                if (judO[i] == 1)
                {
                    Vsum[1] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Google += (Vsum[i] * Hsum[i])/100;
        }
        if (PA_Google == 1)
            Kappa2 = 1;
        else
            Kappa2 = (PA_Google - PE_Google)/(1-PE_Google);

        return Kappa2;
    }

    public void evaluate_avgKappa(float kappa2_JL,float kappa2_JO,float kappa2_JF,float kappa2_LO,float kappa2_LF,float kappa2_OF)
    {
        float avgkappa = (kappa2_JF+kappa2_JL+kappa2_JO+kappa2_LF+kappa2_LO+kappa2_OF)/6;
        System.out.println( "Avg kappa2 is: "+avgkappa);

        try
        {
            writeToFile(avgkappa);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToFile(float avgkappa) throws IOException
    {
        String path = "E:\\Tsinghua Masters in Adv. Computing\\Web Information Retrival\\Assignment\\Assignment-4\\Answer\\output\\output.txt";
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(path,true));
            bw.write("{'evaluation of': 'VSM_GOOGLE', 'Kappa2':");
            bw.write(String.valueOf(avgkappa) + ",");
            //bw.newLine();
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
