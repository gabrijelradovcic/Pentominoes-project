package GUI.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;


import java.awt.event.MouseWheelEvent;
// mouse listener class for moving picture of display
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
    
    public int mouseX = -1;
    public int mouseY = -1;
    public int mouseB = -1;
    public int scroll = -1;

    public int getX(){
        return mouseX;
    }

    public int getY(){
        return mouseY;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();

    }

    public boolean isScrollingUp(){
        return this.scroll == -1;
    }

    public boolean isScrollingDown(){
        return this.scroll == 1;
    }

    public void resetScroll(){
        this.scroll = 0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        this.mouseX = e.getX();
        this.mouseY = e.getY();

       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();

    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseB = -1;
    }

    public void resetButtom(){
        this.mouseB = -1;
    }

    public ClickType getButtom(){
        switch (this.mouseB) {
            case 1:
                return ClickType.leftClick;
            case 2:
                return ClickType.ScrollClick;    
            case 3:
                return ClickType.RightClick;
            case 4:
                return ClickType.BackClick;
            case 5:
                return ClickType.ForwardPage;
            default:
                return ClickType.Unknow;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }


}
