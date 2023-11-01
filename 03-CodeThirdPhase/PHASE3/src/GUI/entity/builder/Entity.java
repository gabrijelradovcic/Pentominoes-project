package GUI.entity.builder;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GUI.shapes.MyPoligon;
import GUI.shapes.Tetraedron;

public class Entity implements IEntity {

    private List<Tetraedron> tetraedrons;
    private MyPoligon[] polygons;

    /**
     * Entity object than is needed for creating 
     * 3D objects
     * @param tetraedrons list of tetraedrons
     */
    public Entity(List<Tetraedron> tetraedrons){
        this.tetraedrons = tetraedrons;
        List<MyPoligon> tempList = new ArrayList<MyPoligon>();
        for (Tetraedron tetra : tetraedrons) {
            tempList.addAll(Arrays.asList(tetra.getPoligons()));
        }
        this.polygons = new MyPoligon[tempList.size()];
        this.polygons = tempList.toArray(this.polygons);
        this.sortPoligons();
    }

    @Override
    public void render(Graphics g) {
        for (MyPoligon tetra : this.polygons) {
            tetra.render(g);
        }
        this.sortPoligons();
    }

    @Override
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for (Tetraedron tetra : tetraedrons) {
            tetra.rotate(CW, xDegrees, yDegrees, zDegrees);
        }
    }
    
    
    private void sortPoligons(){
        MyPoligon.sortPoligons(this.polygons);
    }
    public MyPoligon [] getPoligons(){
        return polygons;
    }

}
