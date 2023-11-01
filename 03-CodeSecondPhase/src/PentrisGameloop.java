import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PentrisGameloop {
    public PentrisGameloop() {

    }
    public static int[] bestScore = new int[1];
    public static int order = 0;
    public noviUI ui;
    public int[][] field = new int[5][15];
    public int[][] backgroundfield = copyOf(field);
    public Scanner scan = new Scanner(System.in);
    public ArrayList<Integer> a = new ArrayList<>();
    public ArrayList<int[]> squareswithsameID = new ArrayList<>();
    public ArrayList<int[]> squareswithsameID2 = new ArrayList<>();
    public int p = 0;
    public int m = 0;
    public int n = 0;
    public int previousn = 0;
    public int placeinarray = 0;
    public int currentplaceinarray = 0;
    public int x = (int) (Math.random() * 12);
    public int nextx = (int) (Math.random() * 12);
    public int[][] oldfield;
    public int[] botarray = new int[3];
    public boolean botplays = true;
    public boolean cascade = false;
    public PentrisGameBot bot;

    public static int getLast(){
        int last;
        try{
            last = readCSV("BestScore.csv");

        }catch(IOException e){
            last = 0;
        }

        return last;
    }
    public static void writeCSV2(int[] time , String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {
    
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < time.length; i++){
                
                sb.append(time[i]);
                sb.append('\n');
 
            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }  
    }
    public static int readCSV (String file) throws IOException { 
        
        
        BufferedReader csvReader = new BufferedReader(new FileReader(file)); 
       
        int[] input = new int [1]; 

        int size = Integer.parseInt(csvReader.readLine());
        
        input[0] = size;

        csvReader.close(); 
       
        return input[0];   
    }
    public int getorder() {
        return order;
    }

    public void setn(int n) {
        this.n = n;
    }

    public void setm(int m) {
        this.m = m;
    }

    public void setp(int p) {
        this.p = p;
    }

    public void setBot(PentrisGameBot bot) {
        this.bot = bot;
    }

    public void setui(noviUI ui) {
        this.ui = ui;
    }

    public int getnextx() {
        return nextx;
    }

    public int getx() {
        return x;
    }

    public int getfullscore() {
        return fullscore;
    }

    public void naive(int[][] field1) {
        int counter = 0;
        int counter2 = 0;
        for (int i = 0; i < field1[0].length; i++) {
            counter = 0;
            for (int j = 0; j < field1.length; j++) {
                if (field1[j][i] != -1) {
                    counter++;
                }
            }
            if (counter == field1.length) {
                for (int k = i; k > 1; k--) {
                    for (int j = 0; j < field1.length; j++) {
                        field[j][k] = field1[j][k - 1];
                    }
                }
                for (int j = 0; j < field1.length; j++) {
                    field1[j][0] = -1;
                }
                counter2++;

            }
        }
        fullscore += counter2;

    }

    public void game() {
        a.add(x);
        oldfield = copyOf(field);
        TimerTask move = new TimerTask() {
            @Override
            public void run() {

                int[][] pieceToPlace = PentominoDatabase.data[x][p];
                if (botplays) {
                    if (placeinarray == currentplaceinarray) {
                        bot.step();
                        while (PentrisGameBot.methodover == false) {
                        }

                        bot.movements();
                    }
                }
                currentplaceinarray++;
                pieceToPlace = PentominoDatabase.data[x][p];
                if (canPut(pieceToPlace, x, m, n, oldfield)) {
                    if (n - 1 >= 0) {
                        field = copyOf(oldfield);
                    }
                    oldfield = copyOf(field);
                    addPiece(field, x, pieceToPlace, x, m, n);
                    ui.setState(field);
                    n++;
                    previousn = n;

                }

                else {
                    if (cascade) {
                        ui.setState(field);
                        if (n - 1 >= 0 && canPut(pieceToPlace, placeinarray, m, n - 1, backgroundfield))
                            addPiece(backgroundfield, placeinarray, pieceToPlace, placeinarray, m, n - 1);
                        while (FullRowExists(backgroundfield)) {
                            removeFullRow(backgroundfield);
                            while (candropdown(backgroundfield)) {
                            }
                        }

                        field = translating(backgroundfield);
                        ui.setState(field);
                        placeinarray++;
                    } 
                    else {
                        ui.setState(field);
                        naive(field);
                    }
                    currentplaceinarray = placeinarray;
                    n = 0;
                    m = -1;
                    p = -1;
                    ui.setState(field);
                    x = nextx;
                    nextx = (int) (Math.random() * 12);
                    a.add(x);
                    outerloop: for (int v = 0; v < PentominoDatabase.data[x].length; v++) {
                        pieceToPlace = PentominoDatabase.data[x][v];
                        for (int t = 0; t < 5; t++) {
                            if (canPut(pieceToPlace, x, t, 0, field)) {
                                oldfield = copyOf(field);
                                m = t;
                                p = v;
                                break outerloop;
                            }
                        }
                    }
                    if (m == -1) {
                        System.out.println("Game over!");
                        System.out.println("Your score is: " + fullscore);

                        int lastScore = 0;
                        try{
                            lastScore = readCSV("BestScore.csv");
                        }catch(IOException e){
                            e.printStackTrace();
                        }

                        bestScore[0] = Math.max(fullscore, lastScore);

                        writeCSV2(bestScore, "BestScore.csv");
                        System.exit(0);
                    }
                }
            }

        };

        Timer newmove = new Timer();
        newmove.schedule(move, 50, (long) Math.floor(1000 / 2f));
    }

    public void setField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
                backgroundfield[i][j] = -1;
            }
        }
    }

    public void addPiece(int[][] field, int pentID, int[][] piece, int pieceID, int x, int y) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

    public boolean outOfTheBounds(int[][] pieceToPlace, int pentID, int x, int y, int[][] field) {
        int t = x;
        if (x < 0) {
            return false;
        }
        if (field.length < (x + pieceToPlace.length)) {
            return false;
        }
        if (field[0].length < (y + pieceToPlace[0].length)) {
            return false;
        }
        if (t - x > x + 1) {
            return false;

        }

        return true;
    }

    public boolean canPut(int[][] pieceToPlace, int pentID, int x, int y, int[][] field) {
        if (!outOfTheBounds(pieceToPlace, pentID, x, y, field)) {
            return false;
        }

        for (int a = 0; a < pieceToPlace.length; a++) {
            for (int b = 0; b < pieceToPlace[0].length; b++) {
                if (pieceToPlace[a][b] == 1) {
                    if (field[x + a][y + b] != -1) {
                        return false;
                    }
                }

            }

        }
        return true;
    }

    public int[][] copyOf(int[][] field) {
        int[][] newfield = new int[field.length][field[0].length];
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b < field[0].length; b++) {
                newfield[a][b] = field[a][b];
            }
        }
        return newfield;
    }

    public int fullscore = 0;

    public boolean FullRowExists(int[][] field1) {
        int counter = 0;
        for (int i = 14; i >= 0; i--) {
            counter = 0;
            for (int j = 0; j < field1.length; j++) {
                if (field1[j][i] != -1) {
                    counter++;
                }
            }
            if (counter == field1.length) {
                return true;
            }
        }
        return false;
    }

    public void removeFullRow(int[][] field1) {
        int counter = 0;
        for (int i = 14; i >= 0; i--) {
            counter = 0;
            for (int j = 0; j < field1.length; j++) {
                if (field1[j][i] != -1) {
                    counter++;
                }
            }
            if (counter == field1.length) {
                for (int k = 0; k < field1.length; k++) {
                    field1[k][i] = -1;
                }
                fullscore++;
            }
        }
    }

    public void FloodFill(int x, int y, int[][] field2, int v) {
        if (field2[x][y] == -1) {
            return;
        }
        int[][] field1 = copyOf(field2);
        field1[x][y] = -1;
        int[] koordinates = new int[] { x, y };

        squareswithsameID.add(koordinates);
        if (x - 1 >= 0) {
            if (field1[x - 1][y] == v) {
                FloodFill(x - 1, y, field1, v);
            }
        }
        if (y - 1 >= 0) {
            if (field1[x][y - 1] == v) {
                FloodFill(x, y - 1, field1, v);
            }
        }
        if (x + 1 < field1.length) {
            if (field1[x + 1][y] == v) {
                FloodFill(x + 1, y, field1, v);
            }
        }
        if (y + 1 < field1[0].length) {
            if (field1[x][y + 1] == v) {
                FloodFill(x, y + 1, field1, v);
            }
        }
        return;

    }

    public void FloodFill2(int x, int y, int[][] field2, int v) {
        if (field2[x][y] == -1) {
            return;
        }
        int[][] field1 = copyOf(field2);
        field1[x][y] = -1;
        int[] koordinates2 = new int[] { x, y };
        squareswithsameID2.add(koordinates2);
        if (x - 1 >= 0) {
            if (field1[x - 1][y] == v) {
                FloodFill2(x - 1, y, field1, v);
            }
        }
        if (y - 1 >= 0) {
            if (field1[x][y - 1] == v) {
                FloodFill2(x, y - 1, field1, v);
            }
        }
        if (x + 1 < field1.length) {
            if (field1[x + 1][y] == v) {
                FloodFill2(x + 1, y, field1, v);
            }
        }
        if (y + 1 < field1[0].length) {
            if (field1[x][y + 1] == v) {
                FloodFill2(x, y + 1, field1, v);
            }
        }
        return;
    }

    public boolean candropdown(int[][] field2) {
        int[][] field1 = copyOf(field2);
        for (int q = 14; q >= 0; q--) {
            for (int w = 0; w < field1.length; w++) {
                if (field1[w][q] != -1) {
                    int v = field1[w][q];
                    squareswithsameID.clear();
                    FloodFill(w, q, field1, v);
                    int brojac = 0;
                    for (int arraylist = 0; arraylist < squareswithsameID.size(); arraylist++) {
                        if (squareswithsameID.get(arraylist)[1] + 1 < field1[0].length && (field1[squareswithsameID
                                .get(arraylist)[0]][squareswithsameID.get(arraylist)[1] + 1] == -1
                                || field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]
                                        + 1] == v)) {
                            brojac++;
                        }
                    }
                    int brojac2 = 0;
                    if (a.get(v) == 4 && p % 2 == 1 && squareswithsameID.size() == 5) {
                        int v1 = 0;
                        if (q > 0 && w + 1 < field1.length) {
                            if (field1[w][q - 1] != v) {
                                v1 = field1[w][q - 1];
                            }
                            if (field1[w + 1][q - 1] != v) {
                                v1 = field1[w][q - 1];
                            }
                        }
                        squareswithsameID2.clear();
                        FloodFill2(w, q - 1, field1, v1);

                        for (int arraylist = 0; arraylist < squareswithsameID.size(); arraylist++) {
                            if (squareswithsameID.get(arraylist)[1] + 1 < field1[0].length && (field1[squareswithsameID
                                    .get(arraylist)[0]][squareswithsameID.get(arraylist)[1] + 1] == -1
                                    || field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]
                                            + 1] == v
                                    || field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]
                                            + 1] == v1)) {
                                brojac2++;
                            }
                        }
                        for (int arraylist = 0; arraylist < squareswithsameID2.size(); arraylist++) {
                            if (squareswithsameID2.get(arraylist)[1] + 1 < field1[0].length
                                    && (field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2
                                            .get(arraylist)[1] + 1] == -1
                                            || field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2
                                                    .get(arraylist)[1] + 1] == v
                                            || field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2
                                                    .get(arraylist)[1] + 1] == v1)) {
                                brojac2++;
                            }
                        }

                        if (brojac2 == squareswithsameID2.size() + squareswithsameID.size()) {
                            for (int arraylist = 0; arraylist < squareswithsameID.size(); arraylist++) {
                                field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]
                                        + 1] = v;
                                field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]] = -1;
                            }
                            for (int arraylist = 0; arraylist < squareswithsameID2.size(); arraylist++) {
                                field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2.get(arraylist)[1]
                                        + 1] = v1;
                                if (field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2
                                        .get(arraylist)[1]] != v)
                                    field1[squareswithsameID2.get(arraylist)[0]][squareswithsameID2
                                            .get(arraylist)[1]] = -1;
                            }

                        }

                    }
                    if (brojac == squareswithsameID.size()) {
                        for (int arraylist = 0; arraylist < squareswithsameID.size(); arraylist++) {
                            field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1] + 1] = v;
                            field1[squareswithsameID.get(arraylist)[0]][squareswithsameID.get(arraylist)[1]] = -1;
                        }
                        backgroundfield = field1;
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public int[][] translating(int[][] g) {
        int[][] translated = new int[g.length][g[0].length];
        for (int q = 0; q < g.length; q++) {
            for (int w = 0; w < g[0].length; w++) {
                if (g[q][w] != -1) {
                    translated[q][w] = a.get(g[q][w]);
                } else {
                    translated[q][w] = -1;
                }
            }
        }
        return translated;
    }

    public void printarray(int[][] array) {
        for (int a = 0; a < array[0].length; a++) {
            for (int b = 0; b < array.length; b++) {
                System.out.print(array[b][a] + " ");
            }
            System.out.println(" ");
        }
    }

    public boolean moveLeft() {
        int[][] pieceToPlace2 = PentominoDatabase.data[x][p];
        if (m - 1 >= 0 && canPut(pieceToPlace2, x, m - 1, n, oldfield)) {
            m--;
            return true;
        }
        return false;
    }

    public boolean moveRight() {
        int[][] pieceToPlace2 = PentominoDatabase.data[x][p];
        if (m + 1 < oldfield.length && canPut(pieceToPlace2, x, m + 1, n, oldfield)) {
            m++;
            return true;
        }
        return false;
    }

    public boolean rotate() {
        int[][] pieceToPlace2 = PentominoDatabase.data[x][(p + 1) % PentominoDatabase.data[x].length];
        int m2 = m;
        int m1 = m;
        while (m1 >= 0 && !canPut(pieceToPlace2, x, m1, n, oldfield)) {
            m1--;
            if (m1 < 0 || Math.abs(m1 - m) > pieceToPlace2[0].length) {
                break;
            }
        }
        if (canPut(pieceToPlace2, x, m1, n, oldfield)) {
            m = m1;
            p = (p + 1) % PentominoDatabase.data[x].length;
            return true;
        }

        while (m2 <= 5 && !canPut(pieceToPlace2, x, m2, n, oldfield)) {
            m2++;
            if (m1 > 5 || Math.abs(m2 - m) > pieceToPlace2[0].length) {
                break;
            }
        }
        if (canPut(pieceToPlace2, x, m2, n, oldfield)) {
            m = m2;
            p = (p + 1) % PentominoDatabase.data[x].length;
            return true;
        }
        return false;
    }

    public void dropDownCompletely() {
        int[][] pieceToPlace2 = PentominoDatabase.data[x][p];
        while (canPut(pieceToPlace2, x, m, n + 1, oldfield) && canPut(pieceToPlace2, x, m, n, oldfield)) {
            n++;
        }
    }

}