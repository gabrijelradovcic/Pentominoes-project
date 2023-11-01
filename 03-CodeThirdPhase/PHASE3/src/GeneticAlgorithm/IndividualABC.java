package GeneticAlgorithm;

import java.util.Random;

import databases.allParcels;
import databases.Parcels3D;

public class IndividualABC {
    int weight1 = 0;
	int weight2 = 0;
	int weight3 = 0;
	int[] chromosome;
	double fitness = 0;
	static Random rand = new Random();

    /**
     * Individual object, each individual has a different chomosome
     * @param chromosome
     */
	public IndividualABC(int[] chromosome) {
		this.chromosome = chromosome;
		for(int i = 0; i < 6; i++){
			weight1 += ((int)Math.pow(10, 5-i))*chromosome[i];
			weight2 += ((int)Math.pow(10, 5-i))*chromosome[6+i];
			weight3 += ((int)Math.pow(10, 5-i))*chromosome[12+i];
		}
		
        this.fitness=GreedyABC(30, 30, 30);

        
		System.out.println(fitness);
	}

    /**
     * getters and setters
     * @return
     */
	public int[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(int[] chromosome) {
		this.chromosome = chromosome;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


	public IndividualABC clone() {
		int[] chromClone = new int[chromosome.length];
		for (int i = 0; i < chromClone.length; i++) {
			chromClone[i] = chromosome[i];
		}
		return new IndividualABC(chromClone);
	}

    /**
     * The reproduction method by one poin crossover
     * 
     * @param partner Takes an individual
     * @return a new individual after making the crossover
     */
	public IndividualABC crossOver(IndividualABC partner) {
		int[] child = new int[chromosome.length];
		for (int i = 0; i < child.length; i++) {
			int tempRand = rand.nextInt(2);
			if (tempRand == 1) {
				child[i] = chromosome[i];
			} else {
				child[i] = partner.getChromosome()[i];
			}
		}
		return new IndividualABC(child);
	}

    /**
     * The greedy algorithm for parcels.
     * @param a A type parcels
     * @param b B type parcels
     * @param c C type parcels
     * @return value of best position
     */
    public static Parcels3D a = new Parcels3D(2, 2, 4, 3);
    public static Parcels3D b = new Parcels3D(2, 3, 4, 4);
    public static Parcels3D c = new Parcels3D(3, 3, 3, 5);
    public Parcels3D [] input;
    public int GreedyABC(int A, int B, int C) {
        int value =0;
        input = new Parcels3D[A + B + C];
        for (int i = 0; i < A; i++) {
            input[i] = c.clone();
        }
        for (int i = 0; i < C; i++) {
            input[i + A] = b.clone();
        }
        for (int i = 0; i < B; i++) {
            input[i + A + C] = a.clone();
        }
        for (int i = 0; i < A + B + C; i++) { 
            if(bestPosition(input[i])[0]==-1){ 
                continue;
            }
            int x = bestPosition(input[i])[0];
            int y = bestPosition(input[i])[1];
            int z = bestPosition(input[i])[2];
            int par = bestPosition(input[i])[3];
            int rot = bestPosition(input[i])[4];
            Parcels3D parcel = allParcels.abc[par][rot].clone();
            space = addPiece(parcel, x, y, z,space);
            value += parcel.getValue(); 
        }
        return value;

    }
    
    /**
     * Checks the area that remaning 
     * after putting the pentomino, the best 
     * area is the one that gives more oportunity 
     * for placing more pentominoes
     * @param k the parcel
     * @param x position in x axis
     * @param y position in y axis
     * @param z position in z axis
     * @return value o f the position
     */
    public boolean[][][] space = new boolean[33][5][8];
    public int checkArea(Parcels3D k, int x, int y, int z) {
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

    /**
     * Chooses the best position for the parcel
     * taking the methods of check area and flood field
     * for the cost function
     * @param k the parcel
     * @return an array with the coordinates and the rotaion of the parcel
     */
    public int[] bestPosition(Parcels3D k) {
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
							boolean[][][] space2 = copyOf(space);
							space2 = addPiece(k, i, j, l, space2);
							boolean [][][] space3 = copyOf(space2);
                            int cost = weight1*checkArea(k, i, j, l)+ weight2*FloodFill(32, 4, 7, space3);
                            maximumArea = Math.max(maximumArea, cost);
                            if (maximumArea == cost) {
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

    /**
     * Method for adding pieces in the cargo space
     * @param k The parcel to place
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @param space the cargo space
     * @return the cargo space after placing the parcel
     */
    public boolean[][][] addPiece(Parcels3D k, int x, int y, int z,boolean[][][] space) {
        for (int i = 0; i < k.getX(); i++) {
            for (int j = 0; j < k.getY(); j++) {
                for (int l = 0; l < k.getZ(); l++) {
                    space[x + i][y + j][z + l] = true;
                }
            }
        }
        return space;
    }

    /**
     * Boolean method that chekcs if 
     * is posible to place a parcel in that position
     * @param k The parcel
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @param space the cargo space
     * @return is possible? yes / no
     */
    public boolean canPut(Parcels3D k, int x, int y, int z) {
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

    /**
     * Boolean method that chekcs if 
     * is there is an error out of bounds 
     * where placing a parcel in that position
     * @param k The parcel
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @return out of bounds? yes / no
     */
    public boolean outOfTheBounds(Parcels3D k, int x, int y, int z) {
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

    /**
     * 
     * @param x
     * @param y
     * @param z
     * @param field
     * @return
     */
	public int FloodFill(int x, int y,int z, boolean[][][] field) {
        int r = 1;
		if(field[x][y][z] == true){
			return 0;
		}
        field[x][y][z] = true;
        if (x - 1 >= 0) {
            if (field[x - 1][y][z] == false) {
                r += FloodFill(x - 1, y,z, field);
            }
        }
        if (y - 1 >= 0) {
            if (field[x][y - 1][z] == false) {
                r += FloodFill(x, y - 1,z, field);
            }
        }
        if (x + 1 < field.length) {
            if (field[x + 1][y][z] == false) {
                r += FloodFill(x + 1, y,z, field);
            }
        }
        if (y + 1 < field[0].length) {
            if (field[x][y + 1][z] == false) {
                r += FloodFill(x, y + 1,z, field);
            }
        }
		if (z + 1 < field[0][0].length) {
            if (field[x][y][z+1] == false) {
                r += FloodFill(x, y,z+1, field);
            }
        }
		if (z - 1 >= 0) {
            if (field[x][y][z-1] == false) {
                r += FloodFill(x, y,z-1, field);
            }
        }
        return r;

    }

    /**
     * makes a copy of the cargo space
     * @param field cargo space
     * @return the copy of the cargo space
     */
	public boolean[][][] copyOf(boolean [][][] field) {
        boolean[][][] newfield = new boolean[field.length][field[0].length][field[0][0].length];
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b < field[0].length; b++) {
                for (int c = 0; c < field[0][0].length; c++) {
                    newfield[a][b][c] = field[a][b][c];
                }
            }
        }
        return newfield;
    }
}
