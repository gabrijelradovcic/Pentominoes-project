package GreedyAlgorithm;

import javax.swing.JFrame;

import GUI.Display;
import GUI.entity.builder.EntityManager;
import databases.allParcels;
import databases.Parcels3D;
//class which implements greedy algorithm for parcels
public class GreedyABC {
    public static Parcels3D a = new Parcels3D(2, 2, 4, 3);
    public static Parcels3D b = new Parcels3D(2, 3, 4, 4);
    public static Parcels3D c = new Parcels3D(3, 3, 3, 5);
    public static Parcels3D[] input;
    public static boolean[][][] space = new boolean[33][5][8];
    // constructor which runs our greedy algorithm for parcels
    public GreedyABC(int A, int B, int C) {
        Display display = new Display();//setting display
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
        int value =0;
        input = new Parcels3D[A + B + C];
        //making input array with first adding c parcels which has highest value, then b , then a
        for (int i = 0; i < A; i++) {
            input[i] = c.clone();
        }
        for (int i = 0; i < C; i++) {
            input[i + A] = b.clone();
        }
        for (int i = 0; i < B; i++) {
            input[i + A + C] = a.clone();
        }
        for (int i = 0; i < A + B + C; i++) { // going trought input array
            if(bestPosition(input[i])[0]==-1){ // if some parcel can't be placed algorithm goes back in upper for loop
                continue;
            }
            //if parcel can be placed it finds best position to place that pentomino
            int x = bestPosition(input[i])[0];
            int y = bestPosition(input[i])[1];
            int z = bestPosition(input[i])[2];
            int par = bestPosition(input[i])[3];
            int rot = bestPosition(input[i])[4];
            //adds parcel to field and to display
            Parcels3D parcel = allParcels.abc[par][rot].clone();
            addPiece(parcel, x, y, z);
            manager.adding(par+"", (x)*10, (y)*10, (z)*10, parcel.getX(), parcel.getY(), parcel.getZ());
            value += parcel.getValue(); // inscreases result value
        }
        System.out.println(value);
        display.start(); // display solution
        display.frame.setVisible(true);

    }
    //methods which checks ,if some parcel can be placed, number of borders it shares with walls of field or other parcel
    public static int checkArea(Parcels3D k, int x, int y, int z) {
        int counter = 0;
        for(int i = 0; i<k.getX(); i++){
            for(int j = 0; j<k.getY(); j++){
                for(int l = 0; l<k.getZ(); l++){
                    try{
                        if(space[i+x-1][j+y][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(space[i+x+1][j+y][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(space[i+x][j+y-1][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(space[i+x][j+y+1][z+l]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(space[i+x][j+y][z+l-1]){
                            counter++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        counter++;
                    }
                    try{
                        if(space[i+x][j+y][z+l+1]){
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
    //for one parcel goes trought all positions and rotations and finds one with biggest checkArea()
    public static int[] bestPosition(Parcels3D k) {
        int maximumArea = -1;
        int[] bestPosition = new int[5];
        bestPosition[0] = -1;
        int rotNum = -1;
        int par = -1;
        if(k.equalParcel(a)){
            rotNum = allParcels.abc[0].length;
            par=0;
        }
        if(k.equalParcel(b)){
            rotNum = allParcels.abc[1].length;
            par=1;
        }
        if(k.equalParcel(c)){
            rotNum = allParcels.abc[2].length;
            par=2;
        }
        
        for (int rot = 0; rot < rotNum; rot++) {
            k=allParcels.abc[par][rot];
            for (int i = 0; i < 33; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int l = 0; l < 8; l++) {
                        if (canPut(k, i, j, l)) {
                            maximumArea = Math.max(maximumArea, checkArea(k, i, j, l));
                            if (maximumArea == checkArea(k, i, j, l)) {
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
        return bestPosition;
    }
    //method which adds piece to field
    public static void addPiece(Parcels3D k, int x, int y, int z) {
        for (int i = 0; i < k.getX(); i++) {
            for (int j = 0; j < k.getY(); j++) {
                for (int l = 0; l < k.getZ(); l++) {
                    space[x + i][y + j][z + l] = true;
                }
            }
        }
    }
    // method which checks if some piece can be added to the field
    public static boolean canPut(Parcels3D k, int x, int y, int z) {
        if (outOfTheBounds(k, x, y, z)) {
            return false;
        }
        for (int i = 0; i < k.getX(); i++) {
            for (int j = 0; j < k.getY(); j++) {
                for (int l = 0; l < k.getZ(); l++) {
                    if (space[x + i][y + j][z + l]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //methods which check if piece which wants to be added goes out of bounds of the field
    public static boolean outOfTheBounds(Parcels3D k, int x, int y, int z) {
        if (x + k.getX() > 33) {
            return true;
        }
        if (y + k.getY() > 5) {
            return true;
        }
        if (z + k.getZ() > 8) {
            return true;
        }
        return false;
    }

}
