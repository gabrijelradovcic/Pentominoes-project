import javax.swing.*;   
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class noviUI extends JPanel implements ActionListener
{
    public JFrame window;
    private int[][] state;
    private int size;
    private Image imgnext;
    private JLabel labelnext = new JLabel("");
    private PentrisGameloop game = new PentrisGameloop();
    public String best = PentrisGameloop.getLast() + " ";
    public noviUI(int x, int y, int _size,PentrisGameloop game)
    {
        this.game = game;
        size = _size;
        setPreferredSize(new Dimension(x * size+200, y * size));
        this.setLayout(null);
        JButton playButton = new JButton("Left");
        playButton.setBounds(280, 550, 150, 30);
        playButton.addActionListener(this);
        playButton.setActionCommand("Left");
        add(playButton); 
        JButton playButton1 = new JButton("Right");
        playButton1.setBounds(280, 590, 150, 30);
        playButton1.addActionListener(this);
        playButton1.setActionCommand("Right");
        add(playButton1);
        JButton playButton2 = new JButton("Rotate");
        playButton2.setBounds(280, 630, 150, 30);
        playButton2.addActionListener(this);
        playButton2.setActionCommand("Rotate");
        add(playButton2);
        JButton playButton3 = new JButton("Space");
        playButton3.setBounds(280, 670, 150, 30);
        playButton3.addActionListener(this);
        playButton3.setActionCommand("Space");
        add(playButton3);
        JLabel label1 = new JLabel("Controls");
        label1.setBounds(320,450,150,150);
        label1.setFont(new Font("Serif", Font.BOLD, 20));
        JLabel label2 = new JLabel("PENTRIS");
        label2.setFont(new Font("Serif", Font.BOLD, 30));
        label2.setBounds(300,-20,150,150);
        
        JLabel score = new JLabel("Score");
        score.setFont(new Font("Serif", Font.BOLD, 20));
        score.setBounds(280,200,150,150);
        add(score);

        JLabel bets = new JLabel("Best Score");
        bets.setFont(new Font("Serif", Font.BOLD, 20));
        bets.setBounds(280,130,150,150);
        add(bets);

        JPanel panelB = new JPanel();
        panelB.setBackground(Color.WHITE);
        panelB.setBounds(280,220,150,30);
        add(panelB);

        JLabel labela = new JLabel(best);
        labela.setBounds(280,220,150,30);
        panelB.add(labela);

        JLabel nextpentomino = new JLabel("Next pentomino");
        nextpentomino.setFont(new Font("Serif", Font.BOLD, 20));
        nextpentomino.setBounds(280,260,150,150);
        add(nextpentomino);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(280,290,150,30);
        add(panel);

        label4 = new JLabel(str);
        label4.setBounds(320,450,150,150);
        label4.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(label4);

        JLabel label = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("tetris.png")).getImage();
        label.setIcon(new ImageIcon(img));
        label.setBounds(320,50,150,150);
        add(label);
        labelnext.setBounds(310,350,150,150);
        add(label);
        add(label1);
        add(label2);
        window = new JFrame("Pentomino");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.add(this);
        window.pack();
        window.setVisible(true);
        window.setFocusable(true);
        state = new int[x][y];
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                state[i][j] = -1;
            }
        }

        window.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent evt) {}
    
            @Override
            public void keyPressed(KeyEvent evt) 
            {
                if(evt.getKeyCode() == KeyEvent.VK_LEFT && game.moveLeft()==true)
                {
                }

                else if (evt.getKeyCode() == KeyEvent.VK_RIGHT && game.moveRight()==true) 
                {
                }

                else if (evt.getKeyCode() == KeyEvent.VK_UP && game.rotate()==true )
                {
                }
                else if (game.n==game.previousn && evt.getKeyCode() == KeyEvent.VK_SPACE )
                {
                    game.dropDownCompletely();
                    
                }
            }

            @Override
            public void keyReleased(KeyEvent evt) {}
        });
    }
    JLabel label4;
    String str = game.getfullscore()+" ";
    int [][] pieceToPlace2 = PentominoDatabase.data[game.x][game.p];

    @Override
    public void actionPerformed(ActionEvent keyboard) {
         String action = keyboard.getActionCommand();

         if (action.equals("Left")) {
            game.moveLeft();
         }
         if (action.equals("Right")) {
            game.moveRight();
         }
         if (action.equals("Rotate")) {
            game.rotate();
         }
         if (action.equals("Space")) {
            game.rotate();
        }
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(Color.CYAN);
        localGraphics2D.fill(getVisibleRect());

        localGraphics2D.setColor(Color.CYAN);
        for (int i = 0; i <= state.length; i++)
        {
            localGraphics2D.drawLine(i * size, 0, i * size, state[0].length * size);
        }
        for (int i = 0; i <= state[0].length; i++)
        {
            localGraphics2D.drawLine(0, i * size, state.length * size, i * size);
        }

        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(i * size + 1, j * size + 1, size - 1, size - 1));
            }
        }
    }

    private Color GetColorOfID(int i)
    {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.DARK_GRAY;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return new Color(0, 0, 0);}
        else if(i==9) {return new Color(0, 0, 100);}
        else if(i==10) {return new Color(100, 0,0);}
        else if(i==11) {return new Color(0, 100, 0);}
        else {return Color.WHITE;}
    }

    public void setState(int[][] _state)
    {
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                state[i][j] = _state[i][j];
            }
        }
        refresh();
        repaint();
    }
    public String getPicture(int a){
        String nextpicture = "";
        switch(game.getnextx()){
            case 0:
                nextpicture="x.gif";
                break;
            case 1:
                nextpicture="i.gif";
                break;
            case 2:
                nextpicture="z.gif";
                break;
            case 3:
                nextpicture="t.gif";
                break;
            case 4:
                nextpicture="u.gif";
                break;
            case 5:
                nextpicture="v.gif";
                break;
            case 6:
                nextpicture="w.gif";
                break;
            case 7:
                nextpicture="y.gif";
                break;
            case 8:
                nextpicture="l.gif";
                break;
            case 9:
                nextpicture="p.gif";
                break;
            case 10:
                nextpicture="n.gif";
                break;
            case 11:
                nextpicture="f.gif";
                break;
        }
        return nextpicture;
    }
    public void refresh(){
        if(game.getnextx()!=game.getx()){
            remove(labelnext);
            imgnext = new ImageIcon(this.getClass().getResource(getPicture(game.nextx))).getImage();
            labelnext.setIcon(new ImageIcon(imgnext));
            labelnext.setBounds(310,350,150,150);
        } 
        add(labelnext);
        str = game.getfullscore() + " ";
        label4.setText(str);
    }
}

