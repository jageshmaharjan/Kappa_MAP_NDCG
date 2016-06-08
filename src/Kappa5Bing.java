import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jagesh on 03/24/2016.
 * Kappa 5 calculation for Bing
 */
public class Kappa5Bing
{
    public static ArrayList<Long> judgeJ = new ArrayList<Long>();
    public static ArrayList<Long> judgeL = new ArrayList<Long>();
    public static ArrayList<Long> judgeO = new ArrayList<Long>();
    public static ArrayList<Long> judgeF = new ArrayList<Long>();

    public static void main(String[] args) throws IOException
    {
        Kappa5Bing kp5bing = new Kappa5Bing();
        kp5bing.readJsonfile();

        float kappa5_JL = kp5bing.evaluate_kappa_JL(judgeJ,judgeL);

        float kappa5_JO = kp5bing.evaluate_kappa_JO(judgeJ,judgeO);

        float kappa5_JF = kp5bing.evaluate_kappa_JF(judgeJ,judgeF);

        float kappa5_LO = kp5bing.evaluate_kappa_LO(judgeL,judgeO);

        float kappa5_LF = kp5bing.evaluate_kappa_LF(judgeL,judgeF);

        float kappa5_OF = kp5bing.evaluate_kappa_OF(judgeO,judgeF);

        kp5bing.avg_kappa5(kappa5_JF,kappa5_JL,kappa5_JO,kappa5_LF,kappa5_LO,kappa5_OF);
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

    private float evaluate_kappa_JL(ArrayList<Long> judgeJ, ArrayList<Long> judgeL)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeJ.size())/2;i++)
        {
            if (judgeJ.get(i) == judgeL.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeJ.size()/2.0));

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeJ.get(i) == -1)
            {
                if (judgeL.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeJ.get(i) == 0)
            {
                if (judgeL.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeJ.get(i) == 1)
            {
                if (judgeL.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeJ.get(i) == 2)
            {
                if (judgeL.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeJ.get(i) == 3)
            {
                if (judgeL.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeL.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeL.get(i) == -1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeL.get(i) == 0)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeL.get(i) == 1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeL.get(i) == 2)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeL.get(i) == 3)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;
    }

    private float evaluate_kappa_JO(ArrayList<Long> judgeJ, ArrayList<Long> judgeO)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeJ.size())/2;i++)
        {
            if (judgeJ.get(i) == judgeO.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeJ.size()/2.0));

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeJ.get(i) == -1)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeJ.get(i) == 0)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeJ.get(i) == 1)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeJ.get(i) == 2)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeJ.get(i) == 3)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeO.get(i) == -1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeO.get(i) == 0)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeO.get(i) == 1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeO.get(i) == 2)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeO.get(i) == 3)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;
    }

    public float evaluate_kappa_JF(ArrayList<Long> judgeJ, ArrayList<Long> judgeF)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeJ.size())/2;i++)
        {
            if (judgeJ.get(i) == judgeF.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeJ.size()/2.0));

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeJ.get(i) == -1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeJ.get(i) == 0)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeJ.get(i) == 1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeJ.get(i) == 2)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeJ.get(i) == 3)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeF.get(i) == -1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeF.get(i) == 0)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeF.get(i) == 1)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeF.get(i) == 2)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeJ.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeF.get(i) == 3)
            {
                if (judgeJ.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeJ.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;
    }

    public float evaluate_kappa_LO(ArrayList<Long> judgeL, ArrayList<Long> judgeO)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeL.size())/2;i++)
        {
            if (judgeL.get(i) == judgeO.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeL.size()/2.0));

        for (int i =0;i<judgeL.size()/2; i++)
        {
            if (judgeL.get(i) == -1)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeL.get(i) == 0)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeL.get(i) == 1)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeL.get(i) == 2)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeL.get(i) == 3)
            {
                if (judgeO.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeO.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeJ.size()/2; i++)
        {
            if (judgeO.get(i) == -1)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeO.get(i) == 0)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeO.get(i) == 1)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeO.get(i) == 2)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeO.get(i) == 3)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;
    }

    public float evaluate_kappa_LF(ArrayList<Long> judgeL,ArrayList<Long> judgeF)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeL.size())/2;i++)
        {
            if (judgeL.get(i) == judgeF.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeL.size()/2.0));

        for (int i =0;i<judgeL.size()/2; i++)
        {
            if (judgeL.get(i) == -1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeL.get(i) == 0)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeL.get(i) == 1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeL.get(i) == 2)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeL.get(i) == 3)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeF.size()/2; i++)
        {
            if (judgeF.get(i) == -1)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeF.get(i) == 0)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeF.get(i) == 1)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeF.get(i) == 2)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeL.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeF.get(i) == 3)
            {
                if (judgeL.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeL.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;
    }

    public float evaluate_kappa_OF(ArrayList<Long> judgeO, ArrayList<Long> judgeF)
    {
        float PA_Bing = (float) 0.0;
        int PA_Bing_calc = 0;
        int PA_Google =0;
        float PE_Bing = (float) 0.0;
        float PE_Google = (float) 0.0;
        float kappa5 = (float) 0.0;
        int[] Hsum = new int[5];
        int[] Vsum = new int[5];

        for (int i = 0;i<(judgeO.size())/2;i++)
        {
            if (judgeO.get(i) == judgeF.get(i))
                PA_Bing_calc += 1;
        }
        PA_Bing = (float) (PA_Bing_calc/(judgeO.size()/2.0));

        for (int i =0;i<judgeO.size()/2; i++)
        {
            if (judgeO.get(i) == -1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[0] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[0] += 1;
                }
            }
            if (judgeO.get(i) == 0)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[1] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[1] += 1;
                }
            }
            if (judgeO.get(i) == 1)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[2] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[2] += 1;
                }
            }
            if (judgeO.get(i) == 2)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[3] += 1;
                }
                if (judgeF.get(i) == 3)
                {
                    Hsum[3] += 1;
                }
            }
            if (judgeO.get(i) == 3)
            {
                if (judgeF.get(i) == -1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 0)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 1)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i) == 2)
                {
                    Hsum[4] += 1;
                }
                if (judgeF.get(i)== 3)
                {
                    Hsum[4] += 1;
                }
            }
        }

        for (int i =0;i<judgeF.size()/2; i++)
        {
            if (judgeF.get(i) == -1)
            {
                if (judgeO.get(i) == -1)
                {
                    Vsum[0] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Vsum[0] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Vsum[0] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Vsum[0] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Vsum[0] += 1;
                }
            }
            if (judgeF.get(i) == 0)
            {
                if (judgeO.get(i) == -1)
                {
                    Vsum[1] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Vsum[1] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Vsum[1] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Vsum[1] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Vsum[1] += 1;
                }
            }
            if (judgeF.get(i) == 1)
            {
                if (judgeO.get(i) == -1)
                {
                    Vsum[2] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Vsum[2] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Vsum[2] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Vsum[2] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Vsum[2] += 1;
                }
            }
            if (judgeF.get(i) == 2)
            {
                if (judgeO.get(i) == -1)
                {
                    Vsum[3] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Vsum[3] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Vsum[3] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Vsum[3] += 1;
                }
                if (judgeO.get(i) == 3)
                {
                    Vsum[3] += 1;
                }
            }
            if (judgeF.get(i) == 3)
            {
                if (judgeO.get(i) == -1)
                {
                    Vsum[4] += 1;
                }
                if (judgeO.get(i) == 0)
                {
                    Vsum[4] += 1;
                }
                if (judgeO.get(i) == 1)
                {
                    Vsum[4] += 1;
                }
                if (judgeO.get(i) == 2)
                {
                    Vsum[4] += 1;
                }
                if (judgeO.get(i)== 3)
                {
                    Vsum[4] += 1;
                }
            }
        }

        for (int i=0;i<Vsum.length;i++)
        {
            PE_Bing += (Vsum[i]*Hsum[i])/100.0;
        }

        kappa5 = (PA_Bing - PE_Bing)/(1 - PE_Bing);

        return kappa5;

    }

    public void avg_kappa5(float kappa5_JF,float kappa5_JL,float kappa5_JO,float kappa5_LF,float kappa5_LO,float kappa5_OF) throws IOException
    {
        float avgKappa5;
        avgKappa5 = (kappa5_JF + kappa5_JL + kappa5_JO + kappa5_LF + kappa5_LO + kappa5_OF)/6;
        System.out.println("Avg kappa5 is: " + avgKappa5);

        writeToFile(avgKappa5);
    }

    public void writeToFile(float avgkappa) throws IOException
    {
        String path = "E:\\Tsinghua Masters in Adv. Computing\\Web Information Retrival\\Assignment\\Assignment-4\\Answer\\output\\output.txt";
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(path,true));
            bw.write("'kappa5': ");
            bw.write(String.valueOf(avgkappa)+", ");
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
