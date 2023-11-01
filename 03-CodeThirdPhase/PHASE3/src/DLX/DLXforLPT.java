package DLX;
import javax.swing.JFrame;

import GUI.Display;
import GUI.entity.builder.EntityManager;
import databases.allPentominoes;

public class DLXforLPT extends AlgXforLPT { // inherites AlgXforLPT to use some its methods
    static ColumnObject root = new ColumnObject(0, "root"); // root node for DL structure
    static int[][] rows = new int[48008][6];
    public static int[][] createMatrix(int[][] DLMatrix) {
        int counter = 0;
        for (int n = 2; n>=1 ; n--) { // number of pentominoes
            for (int p = 0; p < 24; p++) { // 24 rotations for each pentomino
                for (int x = 0; x < 33; x++) {
                    for (int y = 0; y < 5; y++) {
                        for (int z = 0; z < 8; z++) { // 33*5*8 is number of squares
                            int[][][] space = new int[33][5][8]; // creating empty space
                            if (canPut(allPentominoes.all[n][p], x, y, z, space)&&!(n==2&&p>=12)) { // we don't what T pentominoes duplicates
                                space = addPiece(allPentominoes.all[n][p], x, y, z, space); // ading piece to empty space
                                DLMatrix = onePieceOnePosition(space, counter, DLMatrix); // translating 3D space into 1D array, 1D array(1 row)
                                rows[counter][0]=n;   // storing all values of pentominoes for showing solution
                                rows[counter][1]=p;
                                rows[counter][2]=x;
                                rows[counter][3]=y;
                                rows[counter][4]=z;
                                rows[counter][5]=allPentominoes.all[n][p].getValue(); 
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        return DLMatrix;
    }

    public static void createAndConnectColumns(int[][] DLMatrix) {
        for (int n = 0; n < DLMatrix[0].length; n++) { // goes trought all colums
            ColumnObject c = new ColumnObject(0, n + " column");
            ColumnObject.allColumns.add(c);
            c.l = root.l; // root.l is previously created column object
            c.r = root; // on the right side always connect new column object with root
            root.l.r = c; // previosly created column connect with new column object
            root.l = c; // root on the left connect with last column object to be circuit
        }
    }

    public static void createAndConnectNodes(int[][] DLMatrix) {
        for (int y = 0; y < DLMatrix.length; y++) { // number of non empty rows - experimentaly gotten
            Node firstNodeInRow = null;
            int count = 0;
            for (int x = 0; x < DLMatrix[0].length; x++) { // number of columns 33*5*8
                if (DLMatrix[y][x] == 1) { // all 1's in matrix will become nodes
                    if (count == 0) {
                        ColumnObject c = ColumnObject.allColumns.get(x);
                        firstNodeInRow = new Node(y, c); // firstnode in each row needed to connect all nodes in one row
                        count++;
                        //connect up-down
                        firstNodeInRow.d = c; // same as for columns
                        firstNodeInRow.u = c.u;
                        c.u.d = firstNodeInRow;
                        c.u = firstNodeInRow;
                        c.size++;
                    } 
                    else {
                        ColumnObject c = ColumnObject.allColumns.get(x);
                        Node n = new Node(y, c);
                        // connect up-down
                        n.d = c; // same as for creating columns
                        n.u = c.u;
                        c.u.d = n;
                        c.u = n;
                        // connect left-right
                        n.l = firstNodeInRow.l;
                        n.r = firstNodeInRow; // same as for creating columns
                        firstNodeInRow.l.r = n;
                        firstNodeInRow.l = n;
                        c.size++;
                    }
                }
            }
        }
    }
    public static int value = 0;
    public static boolean DL(EntityManager manager) {
        if (root.r==root) { //  if root.r==root means that matrix is empty and algorithm terminates succesfully
            System.out.println("solution found");
            System.out.println(value);
            return true;
        }
        ColumnObject column = getSmallestSize(); // return column with smallest size
        if(column==null) return false;
        column.cover();
        for (Node startrow = column.d;startrow != column;startrow=startrow.d) {
            value += rows[startrow.i][5];
            manager.addingPentomino(allPentominoes.all[rows[startrow.i][0]][rows[startrow.i][1]],  // adding pentomino to final solution 
                    allPentominoes.all[rows[startrow.i][0]][rows[startrow.i][1]].getType(),        // on display
                    rows[startrow.i][2]*10,rows[startrow.i][3]*10,rows[startrow.i][4]*10);
            for (Node nextrow = startrow.r;nextrow != startrow;nextrow=nextrow.r) {      //remove connections of nodes in one row with their column
                (nextrow.c).cover();
            }
            if(DL(manager)){ // branch to find solution
                return true;
            }
            //backtrack if solution is not found, recover previous fields
            column = startrow.c;
            value -= rows[startrow.i][5];
            manager.removingPentomino(allPentominoes.all[rows[startrow.i][0]][rows[startrow.i][1]], //remove pentomino from final solution
                    allPentominoes.all[rows[startrow.i][0]][rows[startrow.i][1]].getType(),         //on dispaly
                    rows[startrow.i][2]*10,rows[startrow.i][3]*10,rows[startrow.i][4]*10);
            for (Node previousrow = startrow.l;previousrow != startrow;previousrow=previousrow.l) { //return all conections with nodes 
                (previousrow.c).uncover();                                                          //from covered rows
            }
        }
        column.uncover(); 
        return false;
    }
    // method returns column with smallest number of 1's
    public static ColumnObject getSmallestSize() { 
        int mathMin=10000000;
        ColumnObject min=null;
        for(ColumnObject i = (ColumnObject)root.r; i!=root;i=(ColumnObject)i.r){
            if(i==root.r){
                mathMin = i.size;
                min=i;
            }
            else{
                if(i.size<mathMin){
                    mathMin=i.size;
                    min=i;
                }
            }
        }
        return min;
    }
    public DLXforLPT() {
        new allPentominoes();
        Display display = new Display(); // connection of our dlx algorithm with GUI
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
        int [][] DLMatrix = new int [48008][1320]; // empty matrix
        DLMatrix = createMatrix(DLMatrix); // our matrix with 0'1 and 1's
        createAndConnectColumns(DLMatrix);// connect all column objects
        createAndConnectNodes(DLMatrix);// conect all nodes - duble linked lists
        DL(manager); // run our dancing links algorithm
        display.start(); // show image of solution
    }

}
