package GUI.entity.builder;

import java.awt.*;
import java.util.ArrayList;

import GUI.point.MyPoint;
import GUI.shapes.MyPoligon;
import GUI.shapes.Tetraedron;
import databases.Pentominoes3D;

public class ComplexEntityBuilder {
    
    public static final int SIZE = 10;

    /**
     * Creates a parcel taking as parameter the parcel type
     * A, B, C and cargo space
     * @param ID type of parcel
     * @param centerX center of the parcel in x axis
     * @param centerY center of the parcel in y axis
     * @param centerZ center of the parcel in z axis
     * @param x size in x axis
     * @param y size in y axis
     * @param z size in z axis
     * @return the parcel
     */
    public static Tetraedron createParcel(String ID, double centerX, double centerY, double centerZ, int x, int y, int z) {
        Color color;

        if (ID.equals("0")) {
            color = Color.BLUE;
        } else if (ID.equals("1")) {
            color = Color.GREEN;
        } else if (ID.equals("2")) {
            color = Color.MAGENTA;
        } else if (ID.equals("CONTAINER")) {
            color = new Color(60, 60, 60, 80);
        } else {
            color = null;
        }

        MyPoint p1 = new MyPoint(centerX + SIZE * x, centerY , centerZ );
        MyPoint p2 = new MyPoint(centerX + SIZE * x, centerY + SIZE * y, centerZ);
        MyPoint p3 = new MyPoint(centerX + SIZE  * x, centerY + SIZE  * y, centerZ + SIZE * z);
        MyPoint p4 = new MyPoint(centerX + SIZE  * x, centerY , centerZ + SIZE * z);
        MyPoint p5 = new MyPoint(centerX , centerY , centerZ );
        MyPoint p6 = new MyPoint(centerX , centerY + SIZE * y, centerZ );
        MyPoint p7 = new MyPoint(centerX , centerY + SIZE * y, centerZ + SIZE * z);
        MyPoint p8 = new MyPoint(centerX , centerY , centerZ + SIZE  * z);

        Tetraedron tetra = new Tetraedron(color,
                true,
                new MyPoligon(color, p1, p2, p3, p4),
                new MyPoligon(color, p5, p6, p7, p8),
                new MyPoligon(color, p1, p2, p6, p5),
                new MyPoligon(color, p1, p5, p8, p4),
                new MyPoligon(color, p2, p6, p7, p3),
                new MyPoligon(color, p4, p3, p7, p8));

        return tetra;
    }

      /**
     * Creates a pentmino taking as parameter the pentomino type
     * L, P and T
     * @param ID type of pentominoe
     * @param centerX center of the pentominoe in x axis
     * @param centerY center of the pentominoe in y axis
     * @param centerZ center of the pentominoe in z axis
     * @param x position in x asis
     * @param y position in x asis
     * @param z position in x asis
     * @return the pentominoe
     */
    public static ArrayList<Tetraedron> createPentomino(Pentominoes3D p, String ID, double centerX, double centerY,double centerZ) {
        ArrayList<Tetraedron> tetras = new ArrayList<Tetraedron>();
        Tetraedron tetra = null;
        Color color;
        double cubeSpacing = 0;
        int size = 10;
        boolean[][][] pentomino = p.getStrucutre();
        if (ID.equals("P")) {
            color = Color.BLUE;
            for (int a = 0; a < pentomino.length; a++) {
                double cubeCenterX = a * (size + cubeSpacing) + centerX;

                for (int b = 0; b < pentomino[0].length; b++) {
                    double cubeCenterY = b * (size + cubeSpacing) + centerY;
                    for (int c = 0; c < pentomino[0][0].length; c++) {
                        double cubeCenterZ = c * (size + cubeSpacing) + centerZ;

                        if (pentomino[a][b][c] == false) {
                            continue;
                        }

                        MyPoint p1 = new MyPoint(cubeCenterX, cubeCenterY,
                                cubeCenterZ);
                        MyPoint p2 = new MyPoint(cubeCenterX, cubeCenterY,
                                cubeCenterZ + size);
                        MyPoint p3 = new MyPoint(cubeCenterX , cubeCenterY + size,
                                cubeCenterZ);
                        MyPoint p4 = new MyPoint(cubeCenterX, cubeCenterY + size,
                                cubeCenterZ + size);
                        MyPoint p5 = new MyPoint(cubeCenterX + size, cubeCenterY,
                                cubeCenterZ );
                        MyPoint p6 = new MyPoint(cubeCenterX + size, cubeCenterY,
                                cubeCenterZ + size);
                        MyPoint p7 = new MyPoint(cubeCenterX + size , cubeCenterY + size,
                                cubeCenterZ);
                        MyPoint p8 = new MyPoint(cubeCenterX + size , cubeCenterY + size,
                                cubeCenterZ + size );

                        MyPoligon polyRed = new MyPoligon(color, p5, p6, p8, p7);
                        MyPoligon polyWhite = new MyPoligon(color, p2, p4, p8, p6);
                        MyPoligon polyBlue = new MyPoligon(color, p3, p4, p8, p7);
                        MyPoligon polyGreen = new MyPoligon(color, p1, p2, p6, p5);
                        MyPoligon polyOrange = new MyPoligon(color, p1, p2, p4, p3);
                        MyPoligon polyYellow = new MyPoligon(color, p1, p3, p7, p5);

                        tetra = new Tetraedron(color, true, polyRed, polyWhite, polyBlue, polyGreen, polyOrange,
                                polyYellow);
                        tetras.add(tetra);
                    }
                }
            }
        }

        else if (ID.equals("L")) {
            color = Color.GREEN;
            for (int a = 0; a < pentomino.length; a++) {
                double cubeCenterX = a * (size + cubeSpacing) + centerX;

                for (int b = 0; b < pentomino[0].length; b++) {
                    double cubeCenterY = b * (size + cubeSpacing) + centerY;
                    for (int c = 0; c < pentomino[0][0].length; c++) {
                        double cubeCenterZ = c * (size + cubeSpacing) + centerZ;

                        if (pentomino[a][b][c] == false) {
                            continue;
                        }

                        MyPoint p1 = new MyPoint(cubeCenterX, cubeCenterY ,
                                cubeCenterZ );
                        MyPoint p2 = new MyPoint(cubeCenterX , cubeCenterY ,
                                cubeCenterZ + size);
                        MyPoint p3 = new MyPoint(cubeCenterX , cubeCenterY + size,
                                cubeCenterZ );
                        MyPoint p4 = new MyPoint(cubeCenterX , cubeCenterY + size ,
                                cubeCenterZ + size );
                        MyPoint p5 = new MyPoint(cubeCenterX + size , cubeCenterY ,
                                cubeCenterZ );
                        MyPoint p6 = new MyPoint(cubeCenterX + size , cubeCenterY ,
                                cubeCenterZ + size );
                        MyPoint p7 = new MyPoint(cubeCenterX + size , cubeCenterY + size ,
                                cubeCenterZ );
                        MyPoint p8 = new MyPoint(cubeCenterX + size, cubeCenterY + size ,
                                cubeCenterZ + size );

                        MyPoligon polyRed = new MyPoligon(color, p5, p6, p8, p7);
                        MyPoligon polyWhite = new MyPoligon(color, p2, p4, p8, p6);
                        MyPoligon polyBlue = new MyPoligon(color, p3, p4, p8, p7);
                        MyPoligon polyGreen = new MyPoligon(color, p1, p2, p6, p5);
                        MyPoligon polyOrange = new MyPoligon(color, p1, p2, p4, p3);
                        MyPoligon polyYellow = new MyPoligon(color, p1, p3, p7, p5);

                        tetra = new Tetraedron(color, true, polyRed, polyWhite, polyBlue, polyGreen, polyOrange,polyYellow);
                        tetras.add(tetra);
                    }
                }
            }
        }

        else if (ID.equals("T")) {
            color = Color.MAGENTA;
            for (int a = 0; a < pentomino.length; a++) {
                double cubeCenterX = a * (size + cubeSpacing) + centerX;

                for (int b = 0; b < pentomino[0].length; b++) {
                    double cubeCenterY = b * (size + cubeSpacing) + centerY;
                    for (int c = 0; c < pentomino[0][0].length; c++) {
                        double cubeCenterZ = c * (size + cubeSpacing) + centerZ;
                        if (pentomino[a][b][c] == false) {
                            continue;
                        }

                        MyPoint p1 = new MyPoint(cubeCenterX , cubeCenterY,
                                cubeCenterZ );
                        MyPoint p2 = new MyPoint(cubeCenterX , cubeCenterY ,
                                cubeCenterZ + size);
                        MyPoint p3 = new MyPoint(cubeCenterX , cubeCenterY + size ,
                                cubeCenterZ );
                        MyPoint p4 = new MyPoint(cubeCenterX , cubeCenterY + size ,
                                cubeCenterZ + size );
                        MyPoint p5 = new MyPoint(cubeCenterX + size , cubeCenterY ,
                                cubeCenterZ );
                        MyPoint p6 = new MyPoint(cubeCenterX + size, cubeCenterY ,
                                cubeCenterZ + size );
                        MyPoint p7 = new MyPoint(cubeCenterX + size , cubeCenterY + size,
                                cubeCenterZ );
                        MyPoint p8 = new MyPoint(cubeCenterX + size , cubeCenterY + size ,
                                cubeCenterZ + size );

                        MyPoligon polyRed = new MyPoligon(color, p5, p6, p8, p7);
                        MyPoligon polyWhite = new MyPoligon(color, p2, p4, p8, p6);
                        MyPoligon polyBlue = new MyPoligon(color, p3, p4, p8, p7);
                        MyPoligon polyGreen = new MyPoligon(color, p1, p2, p6, p5);
                        MyPoligon polyOrange = new MyPoligon(color, p1, p2, p4, p3);
                        MyPoligon polyYellow = new MyPoligon(color, p1, p3, p7, p5);

                        tetra = new Tetraedron(color, true, polyRed, polyWhite, polyBlue, polyGreen, polyOrange,polyYellow);
                        tetras.add(tetra);
                    }
                }
            }

        } else {
            color = null;
        }

        return tetras;
    }
}
