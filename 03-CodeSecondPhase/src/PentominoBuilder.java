/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

 import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class PentominoBuilder {

    private static int[][][] basicDatabase = {
            {
            	// pentomino representation X
                    {0,1,0},
                    {1,1,1},
                    {0,1,0}
            },
            {
            	// pentomino representation I
                    {1},
                    {1},
                    {1},
                    {1},
                    {1}
            },
            {
            	// pentomino representation Z
                    {0,1,1},
                    {0,1,0},
                    {1,1,0}
            },
            {
            	// pentomino representation T
                    {1,1,1},
                    {0,1,0},
                    {0,1,0}
            },
            {
            	// pentomino representation U
                    {1,1},
                    {1,0},
                    {1,1}
            },
            {
            	// pentomino representation V
                    {1,1,1},
                    {1,0,0},
                    {1,0,0}
            },
            {
            	// pentomino representation W
                    {0,0,1},
                    {0,1,1},
                    {1,1,0}
            },
            {
            	// pentomino representation Y
                    {1,0},
                    {1,1},
                    {1,0},
                    {1,0}
            },
            {
            	// pentomino representation L
                    {1,0},
                    {1,0},
                    {1,0},
                    {1,1}
            },
            {
            	// representation P
                    {1,1},
                    {1,1},
                    {1,0}
            },
            {
        		// representation N
                    {1,0},
                    {1,1},
                    {0,1},
                    {0,1}
        		
            },
            {
        		// representation F
                    {0,1,1},
                    {1,1,0},
                    {0,1,0}
            }
    };

    public static ArrayList<int[][][]> database = new ArrayList<>();

    public static void makeDatabase()
    {
        for(int i=0;i<basicDatabase.length;i++)
        {
            int[][][] tempDatabase = new int[8][5][5];

            
            for (int j = 0; j < 4; j++) {
                tempDatabase[j] = moveToAbove(rotate(makeBigger(basicDatabase[i], 5), j));
            }

            for (int j = 0; j < 4; j++) {
                tempDatabase[4 + j] = moveToAbove(verticalFlip(rotate(makeBigger(basicDatabase[i], 5), j)));
            }

            tempDatabase=eraseDuplicates(tempDatabase);

            for(int j=0;j<tempDatabase.length;j++)
            {
                tempDatabase[j]=eraseEmptySpace(tempDatabase[j]);
            }

            database.add(tempDatabase);
        }
    }

    public static int[][] rotate(int[][] data, int rotation)
    {
        int [][] tempData1 = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                tempData1[i][j]=data[i][j];
            }
        }

        for(int k=0;k<rotation;k++) {
            int[][] tempData2 = new int[tempData1.length][tempData1[0].length];
            for (int i = 0; i < tempData1.length; i++) {
                for (int j = 0; j < tempData1[i].length; j++) {
                    tempData2[i][j] = tempData1[j][tempData1.length - i - 1];
                }
            }
            for (int i = 0; i < tempData1.length; i++) {
                for (int j = 0; j < tempData1[i].length; j++) {
                    tempData1[i][j] = tempData2[i][j];
                }
            }
        }
        return tempData1;
    }

    public static int[][] verticalFlip(int[][] data)
    {
        int[][] returnData = new int[data.length][data[0].length];
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                returnData[i][j]=data[i][data[i].length-j-1];
            }
        }
        return returnData;
    }

    public static int[][] horizontalFlip(int[][] data)
    {
        int[][] returnData = new int[data.length][data[0].length];
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                returnData[i][j]=data[data.length-i-1][j];
            }
        }
        return returnData;
    }

    public static int[][] makeBigger(int[][] data,int size)
    {
        int[][] returnData = new int[size][size];
        for(int i=0;i<data.length;i++)
        {
            for (int j = 0; j < data[i].length; j++)
            {
                returnData[i][j]=data[i][j];
            }
        }
        return returnData;
    }

    public static int[][] moveToAbove(int[][] data)
    {
        int amountToCut=0;
        for(int i=0;i<data[0].length;i++)
        {
            int empty=0;
            for(int j=0;j<data.length;j++)
            {
                if(data[j][0]==1)
                {
                    empty=1;
                }
            }
            if(empty==0)
            {
                for(int j=0;j<data.length;j++)
                {
                    for(int k=1;k<data[j].length;k++)
                    {
                        data[j][k - 1] = data[j][k];
                    }
                }
                amountToCut++;
            }
        }
        for(int j=0;j<data.length;j++) {
            for (int k = data[j].length - amountToCut; k < data[j].length; k++) {
                data[j][k] = 0;
            }
        }

        amountToCut=0;
        for(int i=0;i<data.length;i++)
        {
            int empty=0;
            for(int j=0;j<data[0].length;j++)
            {
                if(data[0][j]==1)
                {
                    empty=1;
                }
            }
            if(empty==0)
            {
                for(int j=0;j<data[0].length;j++)
                {
                    for(int k=1;k<data.length;k++)
                    {
                        data[k - 1][j] = data[k][j];
                    }
                }
                amountToCut++;
            }
        }
        for(int j=data.length - amountToCut;j<data.length;j++) {
            for (int k = 0; k < data[j].length; k++) {
                data[j][k] = 0;
            }
        }
        return data;
    }

    public static int[][][] eraseDuplicates(int[][][] data)
    {
        int counter=0;
        for(int i =0;i<data.length;i++)
        {
            int adder=1;
            for(int j=0;j<i;j++)
            {
                if(isEqual(data[i],data[j]))
                {
                    adder=0;
                }
            }
            counter+=adder;
        }
        int[][][] returnData = new int[counter][][];
        counter=0;
        for(int i =0;i<data.length;i++)
        {
            boolean alreadyExist=false;
            for(int j=0;j<i;j++)
            {
                if(isEqual(data[i],data[j]))
                {
                    alreadyExist=true;
                }
            }
            if(alreadyExist==false) {
                returnData[counter] = data[i];
                counter++;
            }
        }
        return returnData;
    }

    public static boolean isEqual(int[][] data1, int[][] data2)
    {
        if ((data1.length!=data2.length) || (data1[0].length!=data2[0].length)){
            return false;
        }
        else{
        boolean isGood = false;
        for (int i = 0;i<data1.length;i++){
            for (int j = 0;j<data1[0].length;j++){
                isGood=false;
                if(data1[i][j]==data2[i][j]){
                    isGood=true;
                }
                if (isGood==false){
                    return false;
                }
            }
        }
        return true;
        }
        
    }

    public static int[][]eraseEmptySpace(int[][] data)
    {
        int amountOfRows=data.length;
        int amountOfColumns=data.length;
        for(int i=0;i<data[0].length && amountOfRows==data.length;i++)
        {
            int columnIsEmpty=0;
            for(int j=0;j<data.length;j++)
            {
                if(data[j][i]==1)
                {
                    columnIsEmpty=1;
                }
            }
            if(columnIsEmpty==0)
            {
                amountOfRows=i;
            }
        }
        for(int i=0;i<data.length && amountOfColumns==data.length;i++)
        {
            int rowIsEmpty=0;
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]==1)
                {
                    rowIsEmpty=1;
                }
            }
            if(rowIsEmpty==0)
            {
                amountOfColumns=i;
            }
        }
        int[][] returnData = new int[amountOfColumns][amountOfRows];
        for(int i=0;i<amountOfColumns;i++)
        {
            for(int j=0;j<amountOfRows;j++)
            {
                returnData[i][j]=data[i][j];
            }
        }
        return returnData;
    }

    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        makeDatabase();

        PrintWriter writer = new PrintWriter("pentominos.csv", "UTF-8");

        for(int i = 0; i < database.size(); i++)
        {
            for(int j = 0; j<database.get(i).length; j++)
            {
                writer.print(i + "," + j + "," + database.get(i)[j].length + "," + database.get(i)[j][0].length);

                for(int k = 0; k < database.get(i)[j].length; k++)
                {
                    for(int l = 0; l < database.get(i)[j][k].length; l++)
                    {
                        writer.print("," + database.get(i)[j][k][l]);
                    }
                }

                writer.println();
            }
        }
        writer.close();
    }

}
