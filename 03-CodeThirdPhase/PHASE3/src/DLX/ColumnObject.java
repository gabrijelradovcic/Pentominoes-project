package DLX;

import java.util.ArrayList;
//cretaes Column object needed for dancing links, for methods used pseudo codes form Knuth's paper "Dancing links"
public class ColumnObject extends Node{  // inherits Node object
    static ArrayList<ColumnObject> allColumns = new ArrayList<>(); // arraylist with all colums created
    String name = null;
    int size;
    ColumnObject(int size,String name){
        super(); // default constructor of node
        this.c = this;
        this.size=size; // number of 1's in column
        this.name = name; // name of column
    }
    public String toString(){ // print column object
        return name+"header";
    }
    public void cover(){ // method for covering column
        this.r.l=this.l;
        this.l.r=this.r;
        for(Node newd = this.d;newd != this;newd = newd.d){
            for(Node newr = newd.r;newr != newd;newr = newr.r){
                newr.u.d = newr.d;
                newr.d.u = newr.u;
                newr.c.size--;
            }
        }
    }
    public void uncover(){ // method for uncovering column
        for(Node newu = this.u;newu != this;newu = newu.u){
            for(Node newl = newu.l;newl != newu;newl = newl.l){
                newl.d.u = newl;
                newl.u.d = newl;
                newl.c.size++;
            }
        }
        this.l.r=this;
        this.r.l=this;
    }
}

