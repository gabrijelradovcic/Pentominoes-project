package DLX;

import java.util.ArrayList;
//cretaes Node object needed for dancing links, for methods used pseudo codes form Knuth's paper "Dancing links"
public class Node {
    static ArrayList<Node> allnodes  =new ArrayList<>();
    Node l;    // left link of this node
    Node r;   // right link of this node
    Node u;   // up link of this node
    Node d;   // down link of this node
    ColumnObject c; // reference to column object
    int i; // row number of this node in starting matrix
    Node(){ // default constructor all links puts to itself when node is created
        l=r=u=d=this;
    }
    Node(int row, ColumnObject column){ // constructor which  all links puts to itself when node is created and makes references to column and row
        l=r=u=d=this;
        c = column;
        i = row;
        this.c = column;
    }
    public String toString(){ // method for printing node
        return i+"|"+c.name;
    }
}
