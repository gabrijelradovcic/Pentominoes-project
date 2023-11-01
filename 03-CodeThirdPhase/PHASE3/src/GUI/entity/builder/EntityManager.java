package GUI.entity.builder;

import java.util.ArrayList;

import GUI.input.ClickType;
import GUI.input.Mouse;
import GUI.point.PointConverter;
import GUI.shapes.Tetraedron;
import databases.Pentominoes3D;

import java.awt.*;

public class EntityManager {

    ArrayList<Tetraedron> tetras = new ArrayList<Tetraedron>();
    ArrayList<Tetraedron> tetras1 = new ArrayList<Tetraedron>();
    ArrayList<IEntity> entities;
    Tetraedron tetra;
    Mouse mouse;
    ClickType previousMouse = ClickType.Unknow;
    int initialX, initialY;
    double mouseSensitivity = 2.5;

    public EntityManager() {
        this.entities = new ArrayList<IEntity>();
    }


    public void adding(String ID, double centerX, double centerY, double centerZ, int x, int y, int z) {
        if (ID.equals("CONTAINER")) {
            tetras1.add(ComplexEntityBuilder.createParcel(ID, centerX, centerY, centerZ, x, y, z));
            entities.removeAll(entities);
            entities.add(new Entity(tetras1));
        } else {
            tetras.add(ComplexEntityBuilder.createParcel(ID, centerX, centerY, centerZ, x, y, z));
            entities.removeAll(entities);
            entities.add(new Entity(tetras1));
            entities.add(new Entity(tetras));
        }
    }

    /**
     * Methos for adding pentominoes to the cargo
     * space
     * @param p pentomino
     * @param ID Type of pentomino
     * @param centerX position in x axis
     * @param centerY position in y axis
     * @param centerZ position in z axis
     */
    public void addingPentomino(Pentominoes3D p, String ID, double centerX, double centerY, double centerZ) {

        tetras.addAll(ComplexEntityBuilder.createPentomino(p, ID, centerX, centerY, centerZ));
        entities.removeAll(entities);
        entities.add(new Entity(tetras1));
        entities.add(new Entity(tetras));

    }

    /**
     * Methos for removing pentominoes to the cargo
     * space
     * @param p pentomino
     * @param ID Type of pentomino
     * @param centerX position in x axis
     * @param centerY position in y axis
     * @param centerZ position in z axis
     */
    public void removingPentomino(Pentominoes3D p, String ID, double centerX, double centerY, double centerZ) {
        tetras.remove(tetras.size()-1);
        entities.removeAll(entities);
        entities.add(new Entity(tetras1));
        entities.add(new Entity(tetras));

    }
    public void removing(String ID, double centerX, double centerY, double centerZ, int x, int y, int z) {
        tetras.remove(tetras.size()-1);
        entities.removeAll(entities);
        entities.add(new Entity(tetras1));
        entities.add(new Entity(tetras));
        
    }

    /**
     * Updates the GUI and listen to the mouse
     * for doing actions
     * @param mouse mouse actions
     */
    public void update(Mouse mouse) {

        int x = mouse.getX();
        int y = mouse.getY();

        if (mouse.getButtom() == ClickType.leftClick) {

            int xDif = x - initialX;
            int yDif = y - initialY;

            this.rotate(true, 0, -yDif / mouseSensitivity, -xDif / mouseSensitivity);

        }

        if (mouse.getButtom() == ClickType.RightClick) {

            int xDif = x - initialX;

            this.rotate(true, -xDif / mouseSensitivity, 0, 0);

        }

        if (mouse.isScrollingUp()) {
            PointConverter.zoomIn();

        } else if (mouse.isScrollingDown()) {
            PointConverter.zoomOut();
        }

        mouse.resetScroll();

        // this.tetra.rotate(true, 0, 1, 1);
        initialX = x;
        initialY = y;

        // System.out.println(mouse.getX() + " " + mouse.getY());

    }

    public void render(Graphics g) {
        for (IEntity entity : entities) {
            entity.render(g);
        }
    }

    private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for (IEntity entity : this.entities) {
            entity.rotate(CW, xDegrees, yDegrees, zDegrees);
        }
    }
}
