package databases;

public class allPentominoes {
    public static Pentominoes3D[][] all = new Pentominoes3D[3][24]; // empty array which will contain all pentominoes with their rotations
    //constructor which manually creates all rotations of pentominoes
    public allPentominoes() {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 24; i++) {
                if(j==0)
                all[j][i] = new Pentominoes3D("L");
                if(j==1)
                all[j][i] = new Pentominoes3D("P");
                if(j==2)
                all[j][i] = new Pentominoes3D("T");
            }
        }
        for(int i=0;i<3;i++){
        all[i][1].changeOnX();
        all[i][12].changeOnY();
        all[i][13].changeOnX(); all[i][13].changeOnY();
        all[i][4].rotateOnX();
        all[i][5].rotateOnZ();
        all[i][6].rotateOnY();
        all[i][7].rotateOnX(); all[i][7].rotateOnZ(); 
        all[i][8].rotateOnX(); all[i][8].rotateOnY();
        all[i][9].changeOnX(); all[i][9].rotateOnX();
        all[i][10].changeOnX(); all[i][10].rotateOnZ();
        all[i][11].changeOnX(); all[i][11].rotateOnY();
        all[i][2].changeOnX(); all[i][2].rotateOnX(); all[i][2].rotateOnZ();
        all[i][3].changeOnX(); all[i][3].rotateOnX(); all[i][3].rotateOnY();
        all[i][14].changeOnY(); all[i][14].rotateOnX();
        all[i][15].changeOnY(); all[i][15].rotateOnZ();
        all[i][16].changeOnY(); all[i][16].rotateOnY();
        all[i][17].changeOnY(); all[i][17].rotateOnX(); all[i][17].rotateOnZ();
        all[i][18].changeOnX(); all[i][18].changeOnY(); all[i][18].rotateOnX();
        all[i][19].changeOnX(); all[i][19].changeOnY(); all[i][19].rotateOnZ();
        all[i][20].changeOnX(); all[i][20].changeOnY(); all[i][20].rotateOnY();
        all[i][21].changeOnY(); all[i][21].rotateOnX(); all[i][21].rotateOnY();
        all[i][22].changeOnX(); all[i][22].changeOnY(); all[i][22].rotateOnX(); all[i][22].rotateOnZ();
        all[i][23].changeOnX(); all[i][23].changeOnY(); all[i][23].rotateOnX(); all[i][23].rotateOnY();;
        }
        
    
        
    }
}