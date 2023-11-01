package Main;
import javax.swing.JOptionPane;

public class RUN_THIS {
    

    public static void main(String[] args) {
        
        int answer = JOptionPane.showConfirmDialog(null, "If you want to play with Pentominoes press YES, if with Parcels press NO?", "Before we starting:", JOptionPane.YES_NO_OPTION);
                
        switch(answer){
        
            case 0:
            new UserInputLPT();
            break;
            case 1:
            new UserInputABC();
        }
    }
}
