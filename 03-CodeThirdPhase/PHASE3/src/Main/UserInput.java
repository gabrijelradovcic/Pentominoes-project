package Main;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import GreedyAlgorithm.GreedyABC;

import java.awt.event.*;



public class UserInput {
    
    public JFrame frame;
    private JTextField parcelA = new JTextField();
    private JTextField parcelB = new JTextField();
    private JTextField parcelC = new JTextField();
    private JLabel labelA = new JLabel();
    private JLabel labelB = new JLabel();
    private JLabel labelC = new JLabel();
    private int A;
    private int B;
    private int C;

    /**
     * Creates a frame for inputing parcels
     */
    public UserInput(){
        
        frame = new JFrame();
        frame.setLayout(null);
        frame.setBounds(500,200,500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        labelA.setBounds(50, 100, 300, 30);
        labelA.setText("Select number of parcels of 'A' type: ");
        frame.add(labelA);

        parcelA.setBounds(300, 100, 100, 30);
        frame.add(parcelA);

        labelB.setBounds(50, 200, 300, 30);
        labelB.setText("Select number of parcels of 'B' type: ");
        frame.add(labelB);
        
        parcelB.setBounds(300, 200, 100, 30);
        frame.add(parcelB);

        labelC.setBounds(50, 300, 300, 30);
        labelC.setText("Select number of parcels of 'C' type: ");
        frame.add(labelC);

        parcelC.setBounds(300, 300, 100, 30);
        frame.add(parcelC);

        JButton convert = new JButton();
        frame.add(convert);
        convert.setBounds(350, 375, 100, 50);
        convert.setText("PLAY");
        convert.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event) {
                try{
                String A_S = parcelA.getText();
                A = Integer.parseInt(A_S);
                String B_S = parcelB.getText();
                B = Integer.parseInt(B_S);
                String C_S = parcelC.getText();
                C = Integer.parseInt(C_S); 
                }
                catch(NumberFormatException e){
                    return;
                }
                frame.dispose();
                new GreedyABC(A, B, C);
                    
            }
        });
    }
    
    public int getA(){
        return A;
    }

    public int getB(){
        return B;
    }

    public int getC(){
        return C;
    }

}

