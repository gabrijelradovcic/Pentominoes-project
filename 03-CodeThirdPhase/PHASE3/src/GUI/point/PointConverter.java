package GUI.point;

import java.awt.*;

import GUI.Display;

public class PointConverter {
    
    public static double scale = 1;
    public static final double ZoomFactor = 1.2;

    public static void zoomIn(){
        scale *= ZoomFactor;
    }

    public static void zoomOut(){
        scale /= ZoomFactor;
    }

    public static Point convertPoint(MyPoint point3D){
       
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depht = point3D.x * scale;
        double[] newVal = scale(x3d, y3d, depht);
        int x2d = (int) (Display.WIDTH / 2 + newVal[0]);
        int y2d = (int) (Display.HEIGHT / 2 - newVal[1]);
        
        Point point2D = new Point(x2d, y2d);
        
        return point2D;
    }

    public static double[] scale(double x3d, double y3d, double depth){

        double dist = Math.sqrt(x3d*x3d + y3d*y3d);
        double theta = Math.atan2(y3d, x3d);
        double depht2 = 15 - depth;
        double localScale = Math.abs(1400/(depht2 + 1400));
        dist *= localScale;
        double [] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }

    public static void rotateAxisX(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.y* p.y + p.z*p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2 * Math.PI/360*degrees*(CW?-1:1);
        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }

    public static void rotateAxisY(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.x* p.x + p.z*p.z);
        double theta = Math.atan2(p.z, p.x);
        theta += 2 * Math.PI/360*degrees*(CW?1:-1);
        p.x = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }

    public static void rotateAxisZ(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.y* p.y + p.x*p.x);
        double theta = Math.atan2(p.y, p.x);
        theta += 2 * Math.PI/360*degrees*(CW?-1:1);
        p.y = radius * Math.sin(theta);
        p.x = radius * Math.cos(theta);
    }

}
