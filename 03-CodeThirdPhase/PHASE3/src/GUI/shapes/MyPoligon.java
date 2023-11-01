package GUI.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import GUI.point.MyPoint;
import GUI.point.PointConverter;

public class MyPoligon {
    
    private MyPoint[] points;
    private Color color;
    
    /**
     * Creates 3D polygons using 
     * 3D points
     * @param color color of the poligon
     * @param points set of points
     */
    public MyPoligon(Color color, MyPoint...points){
        this.color = color;
        this.points = new MyPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }

    public MyPoligon(MyPoint...points){
        this.color = Color.WHITE;
        this.points = new MyPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }

    public void render(Graphics g){
        Polygon poly = new Polygon();

        for (int i = 0; i < this.points.length; i++) {
            Point p = PointConverter.convertPoint(points[i]);
            poly.addPoint(p.x, p.y);
        }
        
        g.setColor(Color.BLACK);
        g.drawPolygon(poly);

        g.setColor(this.color);
        g.fillPolygon(poly);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees){
        for (MyPoint p : points) {
            PointConverter.rotateAxisX(p, CW, xDegrees);
            PointConverter.rotateAxisY(p, CW, yDegrees);
            PointConverter.rotateAxisZ(p, CW, zDegrees);
        }
    }

    public double getAverageX(){
        double sum = 0;
        for (MyPoint p : this.points) {
            sum += p.x;
        }

        return sum/this.points.length;
    }

    /**
     * Order the poligons givig first the polygon that is next 
     * to the user
     * @param poligon Array of poligons
     * @return the nearest poligon
     */
    public static MyPoligon[] sortPoligons(MyPoligon[] poligon){
        ArrayList<MyPoligon> poligonList = new ArrayList<MyPoligon>();
        
        for (MyPoligon poly : poligon) {
            poligonList.add(poly);
        }
        
        Collections.sort(poligonList, new Comparator<MyPoligon>() {
            @Override
            public int compare(MyPoligon p1, MyPoligon p2){
                double p1AverageX = p1.getAverageX();
                double p2AverageX = p2.getAverageX();
                double diff = p2AverageX - p1AverageX;
                if (diff == 0) {
                    return 0;
                }
                return diff < 0 ? 1 : -1;
            }
        });

        for (int i = 0; i < poligon.length; i++) {
            poligon[i] = poligonList.get(i);
        }
        return poligon;
    }
}
