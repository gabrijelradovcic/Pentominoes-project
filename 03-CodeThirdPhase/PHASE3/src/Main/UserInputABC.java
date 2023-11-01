package Main;

import javax.swing.JOptionPane;

import DLX.DLXforABC;

public class UserInputABC {
    public UserInputABC() {
        int answer = JOptionPane.showConfirmDialog(null,
                "If you want to launch a Greedy Algorithm press YES, if DLX press NO", "Before we starting:",
                JOptionPane.YES_NO_OPTION);
        switch (answer) {
            case 0:
                new UserInput();
                break;
            case 1:
                new DLXforABC();
        }
    }
}
