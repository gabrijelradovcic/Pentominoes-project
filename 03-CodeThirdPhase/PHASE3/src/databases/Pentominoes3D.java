package databases;

public class Pentominoes3D {

    public int value;
    public int volume;
    public double cost;
    boolean[][][] structure;
    String type;

    /**
     * This method  creates 3D pentominos
     * taking as parameter the pentomino type
     * L, P, T
     * @param type pentominoe type
     */
    public Pentominoes3D(String type) {
        this.type = type;
        if (type.equals("L")) {// manually seting structure of l pentomino

            this.value = 3;
            this.volume = 5;

            structure = new boolean[4][2][1]; // SEE DOCUMENTATION L & P ✅

            structure[0][0][0] = true;
            structure[1][0][0] = true;
            structure[2][0][0] = true;
            structure[3][0][0] = true;
            structure[3][1][0] = true;

        } else if (type.equals("P")) {// manually seting structure of p pentomino

            this.value = 4;
            this.volume = 5;

            structure = new boolean[3][2][1]; // SEE DOCUMENTATION L & P ✅
            structure[0][0][0] = true;
            structure[1][0][0] = true;
            structure[2][0][0] = true;
            structure[0][1][0] = true;
            structure[1][1][0] = true;

        } else if (type.equals("T")) { // manually seting structure of t pentomino

            this.value = 5;
            this.volume = 5;

            structure = new boolean[3][3][1]; // SEE DOCUMENTATION T ✅

            structure[0][0][0] = true;
            structure[0][1][0] = true;
            structure[0][2][0] = true;
            structure[1][1][0] = true;
            structure[2][1][0] = true;
        }

    }

    /**
     * Getters
     * @return instance variables
     */
    public int getValue(){ //for getting value of pentomino
        return value;
    }

    public boolean[][][] getStrucutre() { // returns structure of pentomino
        return structure;
    }

    public String getType() { // return type of pentomino l,p or t
        return type;
    }

    /**
     * Setters
     * @param p
     */
    public void setStrucutre(boolean[][][] p) { // sets pentomino array 
        structure = copyOf(p);
    }

    public Pentominoes3D clone() { // return clone of Pentomino
        Pentominoes3D m = new Pentominoes3D(type);
        m.setStrucutre(structure);
        return m;
    }

    public String toString() { // for printing pentomino
        return type + "|" + structure.length + "|" + structure[0].length + "|" + structure[0][0].length;
    }

    /**
     * Check if a pentominoe is equal 
     * to another one
     * @param p the pentominoe
     * @return boolean is equal yes / no
     */
    public boolean equals(Pentominoes3D p) { 
        boolean [][][] pent = p.getStrucutre(); // for checking if parcels are same, used to remove duplicates
        if(pent.length!=structure.length||pent[0].length!=structure[0].length||pent[0][0].length!=structure[0][0].length){
            return false;
        }
        for (int a = 0; a < structure.length; a++) {
            for (int b = 0; b < structure[0].length; b++) {
                for (int c = 0; c < structure[0][0].length; c++) {
                    if(pent[a][b][c] != structure[a][b][c]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Creates a copy of a pentominoe
     * @param field The pentominoe to make a copy of
     * @return a new pentominoe 
     */
    public static boolean[][][] copyOf(boolean[][][] field) {
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

    /**
     * Flip pentominoes in the x
     * axis
     */
    public void changeOnX() {
    	boolean [][][] b = new boolean[structure.length][structure[0].length][structure[0][0].length];
    	for(int i=0; i<structure.length; i++) {
    		for(int j=0; j<structure[0].length; j++) {
    			for(int k=0; k<structure[0][0].length; k++) {
    				b[i][j][k]=structure[structure.length-i-1][j][k];
    			}
    		}
    	}
        structure=b;
    }

    /**
     * Rotates pentominoes in the y
     * axis
     */
    public void changeOnY() {
    	boolean [][][] b = new boolean[structure.length][structure[0].length][structure[0][0].length];
    	for(int i=0; i<structure.length; i++) {
    		for(int j=0; j<structure[0].length; j++) {
    			for(int k=0; k<structure[0][0].length; k++) {
    				b[i][j][k]=structure[i][structure[0].length-1-j][k];
    			}
    		}
    	}
        structure = b;
    }

    /**
     * Rotates pentominoes in the z
     * axis
     */
    public void changeOnZ() {
    	boolean [][][] b = new boolean[structure.length][structure[0].length][structure[0][0].length];
    	for(int i=0; i<structure.length; i++) {
    		for(int j=0; j<structure[0].length; j++) {
    			for(int k=0; k<structure[0][0].length; k++) {
    				b[i][j][k]=structure[i][j][structure[0][0].length-1-k];
    			}
    		}
    	}
        structure=b;
    }

    /**
     * Rotates pentominoes in the x
     * axis
     */
    public void rotateOnX() {
        boolean[][][] b = new boolean[structure.length][structure[0][0].length][structure[0].length];
        for(int i=0; i<b.length; i++) {
            for(int j=0; j<b[0].length; j++) {
                for(int k=0; k<b[0][0].length; k++) {
                    b[i][j][k]=structure[i][b[0][0].length-1-k][j];
                }
            }
        }
        structure=b;
    }

    /**
     * Rotates pentominoes in the y
     * axis
     */
    public void rotateOnY() {
        boolean[][][] b = new boolean[structure[0].length][structure.length][structure[0][0].length];
        for(int i=0; i<b.length; i++) {
            for(int j=0; j<b[0].length; j++) {
                for(int k=0; k<b[0][0].length; k++) {
                    b[i][j][k]=structure[j][b.length-1-i][k];
                }
            }
        }
        structure=b;
    }

    /**
     * Rotates pentominoes in the z
     * axis
     */
    public void rotateOnZ() {
        boolean[][][] b = new boolean[structure[0][0].length][structure[0].length][structure.length];
        for(int i=0; i<b.length; i++) {
            for(int j=0; j<b[0].length; j++) {
                for(int k=0; k<b[0][0].length; k++) {
                    b[i][j][k]=structure[k][j][b.length-1-i];
                }
            }
        }
        structure=b;
    }
}
