import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class experimentmain {
    public static int k=1000;
    public static double []scores=new double[k];
    protected static int i;
    public static void main(String[] args) {
        i=0;
        for(i = 0;i<k;i++){
        experimentsloop game = new experimentsloop();
        game.setField();
        game.botplays=true;
        game.cascade=true;
        experimentsbot bot = new experimentsbot(game);
        game.setBot(bot);
        game.game();
        }
    }
    public static void writeCSV(double[] time) {

        try (PrintWriter writer = new PrintWriter(new File("PEO.csv"))) {
    
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < time.length; i++){
                
                sb.append(time[i]);
                sb.append('\n');
 
            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }  
    }
}
