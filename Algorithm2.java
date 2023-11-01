import java.util.Scanner;
public class Algorithm2
{
    
	public static Scanner scan = new Scanner(System.in);
    public static final int horizontalGridSize = horizontal() ;
    public static final int verticalGridSize = vertical();
	public static int horizontal(){
		System.out.println("Input horizontal grid size:");
		return scan.nextInt();
	}
	public static int vertical(){
		System.out.println("Input vertical grid size:");
		return scan.nextInt();
	}
	
    public static final char[] input = new char [(horizontalGridSize*verticalGridSize)/5];
	public static void main(String[] args) 
    {
        System.out.println("Which pentominoes are availible?");
		for(int i = 0; i<input.length;i++){
			input[i]=scan.next().charAt(0);
		}
        
        int[][] field = new int[horizontalGridSize][verticalGridSize];

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
            	field[i][j] = -1;
            }
        }
        long start = System.currentTimeMillis();
        int i=0;
        if(horizontalGridSize<verticalGridSize){
            branching(i,field);
                
            
        }
        else{
            branching2(i,field);
        }
        
        System.out.println(System.currentTimeMillis()-start);
        

    }
    
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
    
	
	
    private static int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }
    public static boolean isFull(int [][] field){
        for(int a = 0; a<field.length; a++){
            for(int b = 0; b<field[0].length; b++){
                if(field[a][b]==-1){
                    return false;
                }
            }
        }
        return true;
    }
    public static int[] findEmptySquare(int[][]field){
        int [] square = new int [2];
        for(int a = 0; a<field[0].length; a++){
            for(int b = 0; b<field.length; b++){
                if(field[b][a]==-1){
                    square[0]=b;
                    square[1]=a;
                    return square;
                }
            }
        }
        square[0]=-1;
        square[1]=-1;
        return square;
    }
    public static int[] findEmptySquare2(int[][]field){
        int [] square = new int [2];
        for(int a = 0; a<field.length; a++){
            for(int b = 0; b<field[0].length; b++){
                if(field[a][b]==-1){
                    square[0]=a;
                    square[1]=b;
                    return square;
                }
            }
        }
        square[0]=-1;
        square[1]=-1;
        return square;
    }
    public static void addPiece(int[][] field,int pentID, int[][] piece, int pieceID, int x, int y)
    {   x=fixing(piece, pentID, x, y, field);
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {   
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }
    public static void addPiece2(int[][] field,int pentID, int[][] piece, int pieceID, int x, int y)
    {   y=fixing2(piece, pentID, x, y, field);
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {   
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }
    public static int[][] copyOf(int [][] field){
        int [][] newfield = new int[field.length][field[0].length];
        for(int a = 0; a<field.length; a++){
            for(int b = 0; b<field[0].length; b++){
                newfield[a][b] = field[a][b];
            }
        }
        return newfield;
    }
    public static boolean Duplicates(int pentID,int [][]field){
        for(int a = 0; a<field.length; a++){
            for(int b = 0; b<field[0].length; b++){
                if(field[a][b]==pentID){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean outOfTheBounds(int [][]pieceToPlace,int pentID,int x,int y, int[][] field){
        int t=x;
        x=fixing(pieceToPlace, pentID, x, y, field);
        if(field.length<(x+pieceToPlace.length)){
            return false;
        }
        if(field[0].length<(y+pieceToPlace[0].length)){
            return false;
        }
        if(t-x>x+1){
            return false;

        }
        

        return true;
    }
    public static boolean outOfTheBounds2(int [][]pieceToPlace,int pentID,int x,int y, int[][] field){
        int t=y;
        y=fixing2(pieceToPlace, pentID, x, y, field);
        if(field.length<(x+pieceToPlace.length)){
            return false;
        }
        if(field[0].length<(y+pieceToPlace[0].length)){
            return false;
        }
        if(t-y>y+1){
            return false;

        }
        

        return true;
    }
    public static boolean canPut(int [][]pieceToPlace,int pentID ,int x,int y,int[][]field){
        //if(y==0){
         if(!outOfTheBounds(pieceToPlace,pentID, x, y, field)){
            return false;
        }
        x=fixing(pieceToPlace, pentID, x, y, field);
        //if(Duplicates(pentID, field)){
         //   return false;
        //}
        
        for(int m = 0; m<pieceToPlace.length; m++){
            for(int n = 0; n<pieceToPlace[0].length; n++){
                    if(pieceToPlace[m][n]==1){
                           if(field[x+m][y+n]!=-1){
                               return false;
                            }
                    }        
                        
                        
            }
            
        }
        ui.setState(field);
        return true;
    }
    public static boolean canPut2(int [][]pieceToPlace,int pentID ,int x,int y,int[][]field){
        //if(y==0){
         if(!outOfTheBounds2(pieceToPlace,pentID, x, y, field)){
            return false;
        }
        y=fixing2(pieceToPlace, pentID, x, y, field);
        //if(Duplicates(pentID, field)){
        //    return false;
        //}
        
        for(int m = 0; m<pieceToPlace.length; m++){
            for(int n = 0; n<pieceToPlace[0].length; n++){
                    if(pieceToPlace[m][n]==1){
                           if(field[x+m][y+n]!=-1){
                               return false;
                            }
                    }        
                        
                        
            }
            
        }
        ui.setState(field);
        return true;
    }
    public static int fixing(int [][]pieceToPlace,int pentID,int x, int y, int[][]field){
        int a=0;
        int z=x;
        while(pieceToPlace[a][0]==0){
            a++;
            x--; 
        }
        if(z!=x)
            return x;
        else{
            return z;
        }
    }
    public static int fixing2(int [][]pieceToPlace,int pentID,int x, int y, int[][]field){
        int a=0;
        int z=y;
        while(pieceToPlace[0][a]==0){
            a++;
            y--; 
        }
        if(z!=y)
            return y;
        else{
            return z;
        }
    }
    public static int[][] backupfield = new int [horizontalGridSize][verticalGridSize];
    public static boolean branching2(int i,int [][] field){
        if(isFull(field)){
            ui.setState(field);
            System.out.println("Solution found");
            return true;
        }
        int[][] backup2field=copyOf(field);
        for(int m=0;m<backup2field[0].length;m++){
            for(int n=0;n<backup2field.length;n++){
                if(backup2field[n][m]==-1){
                    int fill=FloodFill(n, m, backup2field);
                    if(fill%5!=0){
                        return false;
                    }
                }
            }
        }
        int pentID = characterToID(input[i]);
        for(int x=0;x<field[0].length;x++){
            for(int y=0;y<field.length;y++){
                for(int j = 0; j<PentominoDatabase.data[pentID].length; j++){
                    int [][] pieceToPlace = PentominoDatabase.data[pentID][j];
                    
                    if (canPut2(pieceToPlace,pentID, y, x, field)){
                        int [][] backupfield=copyOf(field);
                        
                        int copyI=i;
                        
                        
                        addPiece2(field,pentID, pieceToPlace, pentID, y, x);
                        
                        if(branching(++i,field)){
                            return true;
                        }
                        i=copyI;
                        field=backupfield;
                    }     
                }
                
            }
        }
        return false;
    }
    public static boolean branching(int i,int [][] field){
        if(isFull(field)){
            ui.setState(field);
            System.out.println("Solution found");
            return true;
        }
        if(i==input.length){
            return false;
        }
        int[][] backup2field=copyOf(field);
        for(int m=0;m<backup2field.length;m++){
            for(int n=0;n<backup2field[0].length;n++){
                if(backup2field[m][n]==-1){
                    int fill=FloodFill(m, n, backup2field);
                    if(fill%5!=0){
                        return false;
                    }
                }
            }
        }
        int pentID = characterToID(input[i]);
        for(int x=0;x<field[0].length;x++){
            for(int y=0;y<field.length;y++){
                for(int j = 0; j<PentominoDatabase.data[pentID].length; j++){
                    int [][] pieceToPlace = PentominoDatabase.data[pentID][j];
                    if (canPut(pieceToPlace,pentID, y, x, field)){
                        int [][] backupfield=copyOf(field);
                        
                        int copyI=i;
                        
                        
                        addPiece(field,pentID, pieceToPlace, pentID, y, x);
                        
                        if(branching(++i,field)){
                            return true;
                        }
                        i=copyI;
                        field=backupfield;
                    }     
                }
                
            }
        }
        return false;
    }
    
    public static int FloodFill(int x,int y,int [][]field){
            int r=1;
            field[x][y]=1;
            if(x-1>=0){
                if(field[x-1][y]==-1){
                    r+=FloodFill(x-1, y, field);
                }
            }
            if(y-1>=0){
                if(field[x][y-1]==-1){
                    r+=FloodFill(x, y-1, field);
                }
            }
            if(x+1<field.length){
                if(field[x+1][y]==-1){
                    r+=FloodFill(x+1, y, field);
                }
            }
            if(y+1<field[0].length){
                if(field[x][y+1]==-1){
                  r+=FloodFill(x, y+1, field);
                }
            }
            return r;
            
    }
}
