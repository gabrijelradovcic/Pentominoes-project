package Main;

import GreedyAlgorithm.GreedyABC;

public class Experiment {
    
    public static void main(String[] args) {
        

        for (int i = 0; i < 100; i++) {
            
            double a = Math.random()*70;
            int A = (int) a;
            int primera = (70-A);
            double b = Math.random()*primera;
            int B = (int) b;
            int segunda = (primera-B);
            double c = Math.random()*segunda;
            int C = (int) c;

            new GreedyABC(A, B, C);

            System.out.println(A + " " + B + " " + C + " ");
        }


        
    }
    
}
