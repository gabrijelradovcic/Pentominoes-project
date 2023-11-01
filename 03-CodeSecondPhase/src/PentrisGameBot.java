import java.util.ArrayList;

public class PentrisGameBot {
    public static boolean methodover = true;
    private int[] mincostarray = new int[3];
    private int[][] backgroundboardcopy = new int[5][15];
    private int[][] boardcopy = new int[5][15];
    private ArrayList<int[]> testing = new ArrayList<>();
    private ArrayList<int[]> testing2 = new ArrayList<>();
    private PentrisGameloop game;
    private double w1 = 0.005;
    private double w2 = 0.139;
    private double w3 = 0.136;
    private double w4 = 0.007;
    private double w5 = 0.002;
    private double w6 = 0.232;
    public boolean cascade = false;

    public PentrisGameBot(PentrisGameloop game) {
        this.game = game;
        this.cascade = game.cascade;
    }

    public void setw1(double w1) {
        this.w1 = w1;
    }

    public void setw4(double w4) {
        this.w4 = w4;
    }

    public void setw3(double w3) {
        this.w3 = w3;
    }
    public void setw2(double w2) {
        this.w2 = w2;
    }

    public void setw5(double w5) {
        this.w5 = w5;
    }

    public void setw6(double w6) {
        this.w6 = w6;
    }

    public void movements() {
        game.p = mincostarray[0];
        game.m = mincostarray[1];
        game.n = mincostarray[2];

    }

    private ArrayList<int[]> a = new ArrayList<int[]>();
    private ArrayList<int[]> checkposition = new ArrayList<int[]>();

    public void positions(int pentomino, int rotation, int[][] field, int x, int y) {
        int[][] pieceToPlace = PentominoDatabase.data[pentomino][rotation];
        int[] position = { x, y, rotation };
        checkposition.add(position);
        if (canPut(pieceToPlace, pentomino, x + 1, y, field) && alreadybeenthere(x + 1, y)) {
            positions(pentomino, rotation, field, x + 1, y);
        }
        if (canPut(pieceToPlace, pentomino, x - 1, y, field) && alreadybeenthere(x - 1, y)) {
            positions(pentomino, rotation, field, x - 1, y);
        }
        if (canPut(pieceToPlace, pentomino, x, y + 1, field) && canPut(pieceToPlace, pentomino, x, y, field)
                && alreadybeenthere(x, y + 1)) {
            positions(pentomino, rotation, field, x, y + 1);
        }
        if (!canPut(pieceToPlace, pentomino, x, y + 1, field)) {
            a.add(position);
        }
    }

    public boolean alreadybeenthere(int x, int y) {
        for (int i = 0; i < checkposition.size(); i++) {
            if (checkposition.get(i)[0] == x && checkposition.get(i)[1] == y) {
                return false;
            }
        }
        return true;
    }

    public void allPositions() {
        int pentomino = game.x;
        boardcopy = copyOf(game.field);
        for (int rotation = 0; rotation < PentominoDatabase.data[pentomino].length; rotation++) {
            int[][] pieceToPlace = PentominoDatabase.data[pentomino][rotation];
            if (canPut(pieceToPlace, pentomino, game.m, 0, boardcopy)) {
                positions(pentomino, rotation, boardcopy, game.m, 0);
                checkposition.clear();
            }
        }

    }

    public void bestPosition() {

        mincostarray[0] = game.p;
        mincostarray[1] = game.m;
        mincostarray[2] = game.n;
        methodover = false;
        double mincost = 1000000000;
        int placeinarray = game.placeinarray;
        int pentomino = game.x;
        for (int k = 0; k < a.size(); k++) {
            double cost = 0;
            boardcopy = copyOf(game.field);
            backgroundboardcopy = copyOf(game.backgroundfield);
            int t=0;
            int[][] pentominoToPlace = PentominoDatabase.data[pentomino][a.get(k)[2]];
            if (cascade) {
                if (canPut(pentominoToPlace, placeinarray, a.get(k)[0], a.get(k)[1], backgroundboardcopy))
                    addPiece(backgroundboardcopy, placeinarray, pentominoToPlace, placeinarray, a.get(k)[0],
                            a.get(k)[1]);
                while (FullRowExists(backgroundboardcopy)) {
                    removeFullRow(backgroundboardcopy);
                    while (candropdown(backgroundboardcopy, a.get(k)[2])) {
                    }
                }
                boardcopy = translating(backgroundboardcopy);
                t=checkingheight(boardcopy);
            } 
            else {
                if(canPut(pentominoToPlace, pentomino, a.get(k)[0], a.get(k)[1], boardcopy)){
                    addPiece(boardcopy, pentomino, pentominoToPlace, pentomino, a.get(k)[0], a.get(k)[1]);
                }
                naive(boardcopy);
                t=checkingheightnaive(boardcopy);
            }
            cost = w1 * t + w2 * height(boardcopy) + w3 * bumpiness(boardcopy)
                    + w4 * countholes2(boardcopy) + w5 * counthols(boardcopy) + w6 * numberOfRowsRemoved ;
            if (Math.min(mincost, cost) == cost) {
                mincostarray[0] = a.get(k)[2];
                mincostarray[1] = a.get(k)[0];
                mincostarray[2] = a.get(k)[1];
            }
            mincost = Math.min(mincost, cost);
            numberOfRowsRemoved = 0;
            backgroundboardcopy = copyOf(game.backgroundfield);
            boardcopy = copyOf(game.field);

        }
        a.clear();
        methodover = true;
    }

    public void step() {
        allPositions();
        bestPosition();
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
                        boardcopy[j][k] = field1[j][k - 1];
                    }
                }
                for (int j = 0; j < field1.length; j++) {
                    field1[j][0] = -1;
                }
                counter2++;

            }
        }
        numberOfRowsRemoved = counter2;

    }

    public void FloodFill(int x, int y, int[][] field2, int v) {
        if (field2[x][y] == -1) {
            return;
        }
        int[][] field1 = copyOf(field2);
        field1[x][y] = -1;
        int[] koordinates = new int[] { x, y };

        testing.add(koordinates);
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
        testing2.add(koordinates2);
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

    public boolean candropdown(int[][] field2, int permutation) {
        int[][] field1 = copyOf(field2);
        for (int q = 14; q >= 0; q--) {
            for (int w = 0; w < field1.length; w++) {
                if (field1[w][q] != -1) {
                    int v = field1[w][q];
                    testing.clear();
                    FloodFill(w, q, field1, v);
                    int brojac = 0;
                    for (int arraylist = 0; arraylist < testing.size(); arraylist++) {
                        if (testing.get(arraylist)[1] + 1 < field1[0].length
                                && (field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] == -1
                                        || field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] == v)) {
                            brojac++;
                        }
                    }
                    int brojac2 = 0;
                    if (game.a.get(v) == 4 && permutation % 2 == 1 && testing.size() == 5) {
                        int v1 = 0;
                        if (q > 0 && w + 1 < field1.length) {
                            if (field1[w][q - 1] != v) {
                                v1 = field1[w][q - 1];
                            }

                            if (field1[w + 1][q - 1] != v) {
                                v1 = field1[w][q - 1];
                            }
                        }
                        testing2.clear();
                        FloodFill2(w, q - 1, field1, v1);
                        for (int arraylist = 0; arraylist < testing.size(); arraylist++) {
                            if (testing.get(arraylist)[1] + 1 < field1[0].length
                                    && (field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] == -1
                                            || field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] == v
                                            || field1[testing.get(arraylist)[0]][testing.get(arraylist)[1]
                                                    + 1] == v1)) {
                                brojac2++;
                            }
                        }
                        for (int arraylist = 0; arraylist < testing2.size(); arraylist++) {
                            if (testing2.get(arraylist)[1] + 1 < field1[0].length
                                    && (field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1] + 1] == -1
                                            || field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1] + 1] == v
                                            || field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1]
                                                    + 1] == v1)) {
                                brojac2++;
                            }
                        }
                        if (brojac2 == testing2.size() + testing.size()) {
                            for (int arraylist = 0; arraylist < testing.size(); arraylist++) {
                                field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] = v;
                                field1[testing.get(arraylist)[0]][testing.get(arraylist)[1]] = -1;
                            }
                            for (int arraylist = 0; arraylist < testing2.size(); arraylist++) {
                                field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1] + 1] = v1;
                                if (field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1]] != v)
                                    field1[testing2.get(arraylist)[0]][testing2.get(arraylist)[1]] = -1;
                            }

                        }

                    }
                    if (brojac == testing.size()) {
                        for (int arraylist = 0; arraylist < testing.size(); arraylist++) {
                            field1[testing.get(arraylist)[0]][testing.get(arraylist)[1] + 1] = v;
                            field1[testing.get(arraylist)[0]][testing.get(arraylist)[1]] = -1;
                        }
                        backgroundboardcopy = field1;
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
                    translated[q][w] = game.a.get(g[q][w]);
                } else {
                    translated[q][w] = -1;
                }
            }
        }
        return translated;
    }

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

    public int numberOfRowsRemoved = 0;

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
                numberOfRowsRemoved++;
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

    public int[][] copyOf(int[][] field) {
        int[][] newfield = new int[field.length][field[0].length];
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b < field[0].length; b++) {
                newfield[a][b] = field[a][b];
            }
        }
        return newfield;
    }

    public boolean outOfTheBounds(int[][] pieceToPlace, int pentID, int x, int y, int[][] field) {
        if (x < 0) {
            return false;
        }
        if (field.length < (x + pieceToPlace.length)) {
            return false;
        }
        if (field[0].length < (y + pieceToPlace[0].length)) {
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

    public int bumpiness(int[][] field1) {
        int bumpiness = 0;
        int columnheight = 0;
        int columnheight2 = 0;
        for (int j = 0; j < field1.length - 1; j++) {
            for (int k = 0; k < field1[0].length; k++) {
                if (field1[j][k] != -1) {
                    columnheight2 = 14 - k;
                    break;
                }
            }
            for (int i = 0; i < field1[0].length; i++) {
                if (field1[j + 1][i] != -1) {
                    columnheight = 14 - i;
                    break;
                }

            }
            bumpiness += Math.abs(columnheight - columnheight2);
            columnheight = 0;
            columnheight2 = 0;

        }
        return bumpiness;
    }

    public int checkingheight(int[][] field1) {
        int height = 0;
        int heightcounter = 0;
        for (int a = 14; a >= 0; a--) {
            for (int b = 0; b < field1.length; b++) {
                if (field1[b][a] != -1) {
                    heightcounter++;
                }
            }
            if (heightcounter == 0) {
                return height;
            } else {
                height++;
            }
            heightcounter = 0;
        }

        return height;
    }
    public int checkingheightnaive(int[][] field1) {
        for (int a = 0; a < 15; a++) {
            for (int b = 0; b < 5; b++) {
                if(field1[b][a]!=-1){
                    return 15-a;
                }
            }
        }

        return 0;
    }

    public int height(int[][] field1) {
        int sum = 0;
        for (int a = 0; a < field1.length; a++) {
            for (int b = 0; b < field1[0].length; b++) {
                if (field1[a][b] != -1) {
                    sum += 15 - b;
                    break;
                }
            }
        }
        return sum;

    }

    public int countholes2(int[][] field1) {
        int countholes = 0;
        for (int a = 0; a < field1[0].length; a++) {
            for (int b = 0; b < field1.length; b++) {
                for (int c = a; c < field1[0].length; c++) {
                    if (field1[b][a] == -1 && field1[b][c] != -1) {
                        countholes++;
                        break;
                    }
                }
            }
        }
        return countholes;
    }

    public int counthols(int[][] field1) {
        int nonempty = 0;
        int empty = FloodFill3(0, 0, field1);
        int sum = 0;
        int counthols = 0;
        for (int a = 0; a < field1[0].length; a++) {
            for (int b = 0; b < field1.length; b++) {
                if (field1[b][a] != -1) {
                    nonempty++;
                }
                sum++;
            }
        }
        counthols = sum - empty - nonempty;
        return counthols;
    }

    public void printarray(int[][] array) {
        for (int a = 0; a < array[0].length; a++) {
            for (int b = 0; b < array.length; b++) {
                System.out.print(array[b][a] + " ");
            }
            System.out.println(" ");
        }
    }

    public int FloodFill3(int x, int y, int[][] field1) {
        int r = 1;
        field1[x][y] = 1;
        if (x - 1 >= 0) {
            if (field1[x - 1][y] == -1) {
                r += FloodFill3(x - 1, y, field1);
            }
        }
        if (y - 1 >= 0) {
            if (field1[x][y - 1] == -1) {
                r += FloodFill3(x, y - 1, field1);
            }
        }
        if (x + 1 < field1.length) {
            if (field1[x + 1][y] == -1) {
                r += FloodFill3(x + 1, y, field1);
            }
        }
        if (y + 1 < field1[0].length) {
            if (field1[x][y + 1] == -1) {
                r += FloodFill3(x, y + 1, field1);
            }
        }
        return r;

    }
    public static int deepestWell(int [][] field1){
        int deepestwell = 0;
        int columnheight = 0;
        int columnheight2 = 0;
        for (int j = 0; j < field1.length - 1; j++) {
            for (int k = 0; k < field1[0].length; k++) {
                if (field1[j][k] != -1) {
                    columnheight2 = 14 - k;
                    break;
                }
            }
            for (int i = 0; i < field1[0].length; i++) {
                if (field1[j + 1][i] != -1) {
                    columnheight = 14 - i;
                    break;
                }

            }
            deepestwell = Math.max(Math.abs(columnheight - columnheight2),deepestwell);
            columnheight = 0;
            columnheight2 = 0;

        }
        return deepestwell;
    }
}