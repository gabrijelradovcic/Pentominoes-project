/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class PentominoDatabase
{
    
    public static int[][][][] data = loadData("pentominos.csv");

    
    private static int[][][][] loadData(String fileName)
    {
        
        ArrayList<ArrayList<int[][]>> dynamicList =  new ArrayList<>();
        File file = new File(fileName);

        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) 
            {
                String[] values = scanner.nextLine().split(",");

                if(Integer.valueOf(values[0]) > dynamicList.size() - 1)
                {
                    dynamicList.add(new ArrayList<>());
                }

                int xSize = Integer.valueOf(values[2]);
                int ySize = Integer.valueOf(values[3]);
                int[][] piece = new int[xSize][ySize];

                for(int i = 0; i < xSize * ySize; i++)
                {
                    piece[i / ySize][i % ySize] = Integer.valueOf(values[4 + i]);
                }

                dynamicList.get(dynamicList.size() - 1).add(piece);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        int[][][][] staticList = new int[dynamicList.size()][][][];
        for(int i = 0; i < dynamicList.size(); i++)
        {
            staticList[i] = dynamicList.get(i).toArray(new int[0][0][0]);
        }
        return staticList;
    }

    public static void main(String[] args)
    {
        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j<data[i].length; j++)
            {
                System.out.print(i + "," + j + "," + data[i][j].length + "," + data[i][j][0].length);

                for(int k = 0; k < data[i][j].length; k++)
                {
                    for(int l = 0; l < data[i][j][k].length; l++)
                    {
                        System.out.print("," + data[i][j][k][l]);
                    }
                }

                System.out.println();
            }
        }
    }
}
