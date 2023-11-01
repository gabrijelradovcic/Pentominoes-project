package GeneticAlgorithm;
//import allPentominoes;
import java.util.Random;

import databases.Pentominoes3D;
import databases.allPentominoes;

public class IndividualLPT {
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
	public IndividualLPT(int[] chromosome) {
		this.chromosome = chromosome;
		for(int i = 0; i < 6; i++){
			weight1 += ((int)Math.pow(10, 5-i))*chromosome[i];
			weight2 += ((int)Math.pow(10, 5-i))*chromosome[6+i];
			weight3 += ((int)Math.pow(10, 5-i))*chromosome[12+i];
		}
		
        this.fitness=GreedyLPT(300, 300, 300);

        
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

	public IndividualLPT clone() {
		int[] chromClone = new int[chromosome.length];
		for (int i = 0; i < chromClone.length; i++) {
			chromClone[i] = chromosome[i];
		}
		return new IndividualLPT(chromClone);
	}

    /**
     * The reproduction method by one poin crossover
     * 
     * @param partner Takes an individual
     * @return a new individual after making the crossover
     */
	public IndividualLPT crossOver(IndividualLPT partner) {
		int[] child = new int[chromosome.length];
		for (int i = 0; i < child.length; i++) {
			int tempRand = rand.nextInt(2);
			if (tempRand == 1) {
				child[i] = chromosome[i];
			} else {
				child[i] = partner.getChromosome()[i];
			}
		}
		return new IndividualLPT(child);
	}
    public Pentominoes3D l = new Pentominoes3D("L");
    public Pentominoes3D p = new Pentominoes3D("P");
    public Pentominoes3D t = new Pentominoes3D("T");
    public Pentominoes3D [] input;
    public boolean[][][] space = new boolean[33][5][8];

    /**
     * The greedy algorithm for pentomminoes.
     * @param L L type pentominoe
     * @param P P type pentominoe
     * @param T T type pentominoe
     * @return value of best position
     */
    public int GreedyLPT(int L, int P, int T) {
        new allPentominoes();
        int value =0;
        input = new Pentominoes3D[L + P + T];
        for (int i = 0; i < T; i++) {
            input[i] = t.clone();
        }
        for (int i = 0; i < P; i++) {
            input[i + T] = p.clone();
        }
        for (int i = 0; i < L; i++) {
            input[i + P + T] = l.clone();
        }
        for (int i = 0; i < L + P + T; i++) {
			int [] position = bestPosition(input[i]);
            int x = position[0];
            int y = position[1];
            int z = position[2];
            int par = position[3];
            int rot = position[4];
			if(x==-1){
                continue;
            }
            Pentominoes3D pentomino = allPentominoes.all[par][rot];
            space = addPiece(pentomino, x, y, z,space);
            value += pentomino.getValue();
        }

        return value;
        

    }
    /**
     * Method for printing a 3D matrix
     * @param field The matris that is going to be printed
     */
    public void printarray(boolean[][][] field) {
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b < field[0].length; b++) {
                for (int c = 0; c < field[0][0].length; c++) {
                    System.out.print(field[a][b][c] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks the area that remaning 
     * after putting the pentomino, the best 
     * area is the one that gives more oportunity 
     * for placing more pentominoes
     * @param k the pentominoe
     * @param x position in x axis
     * @param y position in y axis
     * @param z position in z axis
     * @return value o f the position
     */
    public int checkArea(Pentominoes3D k, int x, int y, int z) {
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

    /**
     * Chooses the best position for the pentominoes
     * taking the methods of check area and flood field
     * for the cost function
     * @param k the pentomino
     * @return an array with the coordinates and the rotaion of th pentomino
     */
    public int[] bestPosition(Pentominoes3D k) {
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
                        if (canPut(k, i, j, l,space)) {
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
     * @param k The pentomino to place
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @param space the cargo space
     * @return the cargo space after placing the pentomino
     */
    public boolean[][][] addPiece(Pentominoes3D k, int x, int y, int z, boolean [][][] space) {
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
		return space;
    }

    /**
     * Boolean method that chekcs if 
     * is posible to place a pentomino in that position
     * @param k The pentominoe
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @param space the cargo space
     * @return is possible? yes / no
     */
    public boolean canPut(Pentominoes3D k, int x, int y, int z,boolean [][][] space) {
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

    /**
     * Boolean method that chekcs if 
     * is there is an error out of bounds 
     * where placing a pentomino in that position
     * @param k The pentominoe
     * @param x position in the x axis
     * @param y position in the y axis
     * @param z position in the z axis
     * @return out of bounds? yes / no
     */
    public boolean outOfTheBounds(Pentominoes3D k, int x, int y, int z) {
        boolean [][][] pent = k.getStrucutre();
        if (x + pent.length> 33) {
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


