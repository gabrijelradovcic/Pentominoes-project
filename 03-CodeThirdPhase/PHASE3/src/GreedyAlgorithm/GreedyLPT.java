package GreedyAlgorithm;
import javax.swing.JFrame;

import GUI.Display;
import GUI.entity.builder.EntityManager;
import databases.Pentominoes3D;
import databases.allPentominoes;
//class which implements greedy algorithm for pentominoes
public class GreedyLPT {
    public static Pentominoes3D l = new Pentominoes3D("L");
    public static Pentominoes3D p = new Pentominoes3D("P");
    public static Pentominoes3D t = new Pentominoes3D("T");
    public static Pentominoes3D [] input;
    public static boolean[][][] space = new boolean[33][5][8];
    // constructor which runs our greedy algorithm for pentominoes
    public GreedyLPT(int L, int P, int T) {
        Display display = new Display(); //setting display
        display.frame.setTitle("3D Render");
        display.frame.add(display);
        display.frame.pack();
        display.frame.setSize(800, 600);
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);
        EntityManager manager = display.getEntityManager();
        manager.adding("CONTAINER", 0,0,0, 33, 5, 8);
        new allPentominoes();
        int value =0;
        input = new Pentominoes3D[L + P + T];
        //making input array with first adding t pentomino which has highest value, then p , then l
        for (int i = 0; i < T; i++) {
            input[i] = t.clone();
        }
        for (int i = 0; i < P; i++) {
            input[i + T] = p.clone();
        }
        for (int i = 0; i < L; i++) {
            input[i + P + T] = l.clone();
        }
        for (int i = 0; i < L + P + T; i++) { // going trought input array
            if(bestPosition(input[i])[0]==-1){ // if some pentomino can't be placed algorithm goes back in upper for loop
                continue;
            }
            //if pentomino can be placed it finds best position to place that pentomino
            int x = bestPosition(input[i])[0];
            int y = bestPosition(input[i])[1];
            int z = bestPosition(input[i])[2];
            int par = bestPosition(input[i])[3];
            int rot = bestPosition(input[i])[4];
            String ID = "";
            if(par==0){
                ID="L";
            }
            if(par==1){
                ID="P";
            }
            if(par==2){
                ID="T";
            }
            //adds pentomino to field and to display
            Pentominoes3D pentomino = allPentominoes.all[par][rot].clone();
            addPiece(pentomino, x, y, z);
            manager.addingPentomino(pentomino,ID, x*10, y*10, z*10);
            value += pentomino.getValue();// inscreases result value
        }
        System.out.println(value);
        display.start(); //  display solution
        display.frame.setVisible(true);

    }
    //methods which checks ,if some pentomino can be placed, number of squares it shares with walls of field or other pentomino
    public static int checkArea(Pentominoes3D k, int x, int y, int z) {
        int counter = 0;
        boolean [][][] pent = k.getStrucutre();
        for(int i = 0; i<pent.length; i++){
            for(int j = 0; j<pent[0].length; j++){
                for(int l = 0; l<pent[0][0].length; l++){
                    try{
                        if(pent[i][j][l]&&space[i+x-1][j+y][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(pent[i][j][l]&&space[i+x+1][j+y][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(pent[i][j][l]&&space[i+x][j+y-1][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(pent[i][j][l]&&space[i+x][j+y+1][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(pent[i][j][l]&&space[i+x][j+y][z+l-1]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(pent[i][j][l]&&space[i+x][j+y][z+l+1]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    
                }
            }
        }
        return counter;
    }
    //for one pentomino goes trought all positions and rotations and finds one with biggest checkArea()
    public static int[] bestPosition(Pentominoes3D k) {
        int maximumArea = -1;
        int[] bestPosition = new int[5];
        bestPosition[0] = -1;
        int rotNum = -1;
        int par = -1;
        if(k.equals(l)){
            rotNum = allPentominoes.all[0].length;
            par=0;
        }
        if(k.equals(p)){
            rotNum = allPentominoes.all[1].length;
            par=1;
        }
        if(k.equals(t)){
            rotNum = allPentominoes.all[2].length;
            par=2;
        }
        for (int rot = 0; rot < rotNum; rot++) {
            k=allPentominoes.all[par][rot].clone();
            for (int i = 0; i < 33; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int l = 0; l < 8; l++) {
                        Pentominoes3D m = k.clone();
                        if (canPut(m, i, j, l)) {
                            maximumArea = Math.max(maximumArea, checkArea(m, i, j, l));
                            if (maximumArea == checkArea(m, i, j, l)) {
                                bestPosition[0] = i;
                                bestPosition[1] = j;
                                bestPosition[2] = l;
                                bestPosition[3] = par;
                                bestPosition[4] = rot;
                                
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(Arrays.toString(bestPosition));
        return bestPosition;
    }
    //method which adds piece to field
    public static void addPiece(Pentominoes3D k, int x, int y, int z) {
        boolean [][][] pent = k.getStrucutre();
        for (int i = 0; i < pent.length; i++) {
            for (int j = 0; j < pent[0].length; j++) {
                for (int l = 0; l < pent[0][0].length; l++) {
                    if(pent[i][j][l]){
                        space[x + i][y + j][z + l] = true;
                    }
                }
            }
        }
    }
    // method which checks if some piece can be added to the field
    public static boolean canPut(Pentominoes3D k, int x, int y, int z) {
        boolean [][][] pent = k.getStrucutre();
        if (outOfTheBounds(k, x, y, z)) {
            return false;
        }
        for (int i = 0; i < pent.length; i++) {
            for (int j = 0; j < pent[0].length; j++) {
                for (int l = 0; l < pent[0][0].length; l++) {
                    if (space[x + i][y + j][z + l] && pent[i][j][l]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //methods which check if piece which wants to be added goes out of bounds of the field
    public static boolean outOfTheBounds(Pentominoes3D k, int x, int y, int z) {
        boolean [][][] pent = k.getStrucutre();
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
