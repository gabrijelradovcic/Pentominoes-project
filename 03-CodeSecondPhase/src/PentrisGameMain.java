import javax.swing.JOptionPane;

public class PentrisGameMain {
    public static void main(String[] args) {
        
        int answer = JOptionPane.showConfirmDialog(null, "human player or bot? Human press YES, Bot press NO", "very serious question", JOptionPane.YES_NO_OPTION);
       
        switch(answer){
            case 1:
            int answer2 = JOptionPane.showConfirmDialog(null, "grvity? naive press YES, cascade press NO", "very serious question", JOptionPane.YES_NO_OPTION);
            
            switch(answer2){

                case 1:
                
                PentrisGameloop game0 = new PentrisGameloop();
                game0.setField();
                game0.botplays=true;
                game0.cascade=true;
                noviUI ui = new noviUI(5, 15, 50, game0);
                PentrisGameBot bot = new PentrisGameBot(game0);
                game0.setBot(bot);
                game0.setui(ui);
                game0.game();

                break;

                case 0:

                PentrisGameloop game1 = new PentrisGameloop();
                game1.setField();
                game1.botplays=true;
                game1.cascade=false;
                noviUI ui1 = new noviUI(5, 15, 50, game1);
                PentrisGameBot bot1 = new PentrisGameBot(game1);
                game1.setBot(bot1);
                game1.setui(ui1);
                game1.game();

            }
           
            

            break;

            case 0:

            int answer3 = JOptionPane.showConfirmDialog(null, "grvity? naive press YES, cascade press NO", "very serious question", JOptionPane.YES_NO_OPTION);
            
            switch(answer3){

                case 1:
                
                PentrisGameloop game0 = new PentrisGameloop();
                game0.setField();
                game0.botplays=false;
                game0.cascade=true;
                noviUI ui = new noviUI(5, 15, 50, game0);
                PentrisGameBot bot = new PentrisGameBot(game0);
                game0.setBot(bot);
                game0.setui(ui);
                game0.game();

                break;

                case 0:

                PentrisGameloop game1 = new PentrisGameloop();
                game1.setField();
                game1.botplays=false;
                game1.cascade=false;
                noviUI ui1 = new noviUI(5, 15, 50, game1);
                PentrisGameBot bot1 = new PentrisGameBot(game1);
                game1.setBot(bot1);
                game1.setui(ui1);
                game1.game();

            }

        }
        
    } 
} 
    