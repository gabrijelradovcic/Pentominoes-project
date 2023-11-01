package DLX;
import java.util.ArrayList;
import databases.Pentominoes3D;
import databases.allPentominoes;
// implementation of algorithm X without optimization DL , it is to slove to solve bigger matrix
public class AlgXforLPT {
    static int input[] = new int[300];
    public static void main(String[] args) {
        new allPentominoes();// create new pentomino database
        int[][] colums = new int[57080][1320]; // empty input matrix
        ArrayList<Integer> array = new ArrayList<>(); //arraylist which will store solution
        int counter = 0;
        for (int n = 2; n >= 0; n--) { // iterate trought number of different pentominoes
            for (int p = 0; p < 24; p++) { // iterate trought number of rotations
                for (int i = 0; i < 33; i++) {
                    for (int j = 0; j < 5; j++) {
                        for (int k = 0; k < 8; k++) { // 33*5*8 iterate trought squares of empty field
                            int[][][] space = new int[33][5][8]; // empty field
                            if (canPut(allPentominoes.all[n][p], i, j, k, space)) {
                                space = addPiece(allPentominoes.all[n][p], i, j, k, space);
                                colums = onePieceOnePosition(space, counter, colums);// translate 3d array space into 1d row, fills input matrix
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        algorithmX(array, colums);
    }
    // method which translates field with one pentomino placed in one row of input array
    public static int[][] onePieceOnePosition(int[][][] space, int p, int colums[][]) {
        int count = 0;
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                for (int k = 0; k < space[0][0].length; k++) {
                    colums[p][count] = space[i][j][k];
                    count++;
                }
            }
        }
        return colums;
    }
    //method which removes whole row from input matrix
    public static int[][] removeRow(int[][] field, int i) {
        int[][] newfield = new int[field.length - 1][field[0].length];
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < field[0].length; l++) {
                newfield[k][l] = field[k][l];
            }
        }
        for (int k = field.length - 1; k > i; k--) {
            for (int l = 0; l < field[0].length; l++) {
                newfield[k - 1][l] = field[k][l];
            }
        }
        return newfield;
    }
    //method which removes whole column from input matrix
    public static int[][] removeColumn(int[][] field, int i) {
        int[][] newfield = new int[field.length][field[0].length - 1];
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < field.length; l++) {
                newfield[l][k] = field[l][k];
            }
        }
        for (int k = field[0].length - 1; k > i; k--) {
            for (int l = 0; l < field.length; l++) {
                newfield[l][k - 1] = field[l][k];
            }
        }
        return newfield;
    }
    //method which finds column with smallest number of 1's.
    public static int columnfinder(int[][] field) {
        if (field.length == 0) {
            return -1;
        }
        int counter;
        int[] mincolumn = new int[field[0].length];
        for (int i = 0; i < field[0].length; i++) {
            counter = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[j][i] == 1) {
                    counter++;
                }
            }
            mincolumn[i] = counter;
        }
        int min = 10000;
        for (int k = 0; k < mincolumn.length; k++) {
            min = Math.min(mincolumn[k], min);
        }
        for (int k = 0; k < mincolumn.length; k++) {
            if (mincolumn[k] == min) {
                return k;
            }
        }
        return -1;

    }
    // method which copies input matrix
    public static int[][] copyOf(int[][] field) {
        int[][] newfield = new int[field.length][field[0].length];
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b < field[0].length; b++) {
                newfield[a][b] = field[a][b];
            }
        }
        return newfield;
    }
    //main loop of algorithm X made from pseudo code found in Donald Knuth paper "Dancing links"
    public static boolean algorithmX(ArrayList<Integer> array, int[][] field) {
        int c = columnfinder(field); // find column with smallest number of 1's
        if (columnfinder(field) == -1) { // if previous method returned -1 that means input matrix is empty and it terminates loop succesfully
            System.out.println("solution found");
            return true;
        }
        int x = field.length;
        int y = field[0].length;
        for (int i = 0; i < x; i++) {
            if (field[i][c] == 1) {
                int[][] newfield = copyOf(field);
                ArrayList<Integer> rowstoremove = new ArrayList<>(); // arraylist which stores indexes of rows which will be removed
                ArrayList<Integer> columstoremove = new ArrayList<>();// arraylist which stores indexes of columns which will be removed
                for (int a = 0; a < y; a++) {
                    if (newfield[i][a] == 1) {
                        for (int b = 0; b < x; b++) {
                            if (newfield[b][a] == 1 && !rowstoremove.contains(b)) {
                                rowstoremove.add(b); // puts in rowstoremove arraylist
                            }
                        }
                        columstoremove.add(a); //puts in columnstoremove arraylist
                    }
                }
                columstoremove.sort(null);
                for (int a = columstoremove.size() - 1; a >= 0; a--) {
                    newfield = removeColumn(newfield, columstoremove.get(a));// removes rows from input matrix
                }
                rowstoremove.sort(null);
                for (int a = rowstoremove.size() - 1; a >= 0; a--) {
                    newfield = removeRow(newfield, rowstoremove.get(a)); // removes colums from input matrix
                }
                array.add(i); // row i is added to solution
                if(algorithmX(array, newfield)){ // branches 
                    return true;
                }
                field=newfield; // backtracks if solution is not found
            }
        }
        return false;
    }
    //method which adds piece to empty field
    public static int[][][] addPiece(Pentominoes3D k, int x, int y, int z, int[][][] space) {
        boolean[][][] pent = k.getStrucutre();
        for (int i = 0; i < pent.length; i++) {
            for (int j = 0; j < pent[0].length; j++) {
                for (int l = 0; l < pent[0][0].length; l++) {
                    if (pent[i][j][l]) {
                        space[x + i][y + j][z + l] = 1;
                    }
                }
            }
        }
        return space;
    }
    //method which checks if piece can be added to field
    public static boolean canPut(Pentominoes3D k, int x, int y, int z, int[][][] space) {
        boolean[][][] pent = k.getStrucutre();
        if (outOfTheBounds(k, x, y, z)) {
            return false;
        }
        for (int i = 0; i < pent.length; i++) {
            for (int j = 0; j < pent[0].length; j++) {
                for (int l = 0; l < pent[0][0].length; l++) {
                    if (space[x + i][y + j][z + l] == 1 && pent[i][j][l]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //method which checks if piece which wants to be added goes out of bounds of that field
    public static boolean outOfTheBounds(Pentominoes3D k, int x, int y, int z) {
        boolean[][][] pent = k.getStrucutre();
        if (x + pent.length > 33) {
            return true;
        }
        if (y + pent[0].length > 5) {
            return true;
        }
        if (z + pent[0][0].length > 8) {
            return true;
        }
        return false;
    }
}
