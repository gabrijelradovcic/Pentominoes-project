package GUI.entity.builder;
import java.awt.*;
//interface for entities
public interface IEntity {
    

    void render(Graphics g);

    void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees);

    
}
