package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Model extends JPanel {

    private Image dbImage;
    private Graphics dbg;
    Color iono = new Color(240, 253, 2);
    // Color newColor = new Color()
    JFrame frame;
    JFrame gameframe;
    JFrame helpwin;
    int x, y;
    int LX2, Ly2;
    int x2, y2;

    boolean[][] guys = new boolean[6][4]; // who's alive?

    //arrya of bad guys and positions
    int[] exez = new int[6];
    int[] whyz = new int[4];

    int[] EBXX = new int[3];
    int[] EBYY = new int[3];
    int EBX;
    int EBY;
    int EBX2;
    int EBY2;
    int EBX3;
    int EBY3;

    boolean bolt1 = false;
    boolean bolt2 = false;
    boolean bolt3 = false;

    int XW = 700;
    int YW = 700;
    // public images I = new images();

    long startTime = 0;

    boolean leftP = false;
    boolean rightP = false;

    boolean movR = true;
    boolean movL = false; // these are the direction in which bad guys move. start moving ->
    boolean pressedpause = false;
    boolean myS;

    public boolean IinAction() {
        if (XW == 700 && YW == 700) {
            return false;
        } else {
            return true;
        }
    }

    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar()=='p')// pressed pause
            {
                if(pressedpause)
                {
                    pressedpause=false;
                }
                else // not pressed
                {
                    pressedpause=true;
                }
                
            }
            
            
            if (!pressedpause) {
                int keyCode = e.getKeyCode();
                BGM();
                if (!imded || lost) {
                    if (keyCode == e.VK_SPACE) {
                        myS = true;
                        if (!IinAction()) {
                            XW = x + 13;
                            YW = 500;
                            function();
                        }

                    }
                    if (keyCode == e.VK_LEFT) {

                        if (x > 0) {
                            x = x - 8;
                        }
                        leftP = true;
                        rightP = false;

                    }
                    if (keyCode == e.VK_RIGHT) {
                        rightP = true;
                        leftP = false;

                        if (x < 544) {
                            x = x + 8;
                        }
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e) // using booleans we can make buttons not inteerupt eachother
        {
            if (leftP == true) {
                leftP = false;
            } else if (rightP == true) {
                rightP = false;
            }
        }

    }

    boolean gamemode = false;

    JPanel mypanel;
    JButton[] button = new JButton[5];

    private void mainscreen() {

        coop L = new coop();

        L.add(this);
        L.setVisible(true);//        ****************
        L.addKeyListener(new AL());
//      L.add(mypanel);
        button[0].addActionListener(new ActionListener() // clear everything. reinitialize everything.
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                L.remove(button[0]);
                L.remove(button[1]);
                L.remove(button[2]);
                L.remove(button[3]);
                L.remove(button[4]);
                //  L.remove(mypanel);
                //  L.addKeyListener(new AL());
                L.dispose();
                FireUpscreen();

                gamemode = true;
            }
        }
        );
        button[1].addActionListener(new ActionListener() // clear everything. reinitialize everything.
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                helpwin = new JFrame();
                JPanel P = new JPanel();
                helpwin.setSize(500, 430);
                helpwin.setLocationRelativeTo(null);

                helpwin.dispose();
                // helpwin.setDefaultCloseOperation(helpwin.EXIT_ON_CLOSE);
                JTextArea x = new JTextArea();
                x.setBounds(0, 0, 500, 500);
                x.setBackground(Color.BLACK);
                x.setFont(new Font("Arial", Font.PLAIN, 23));
                x.setForeground(Color.WHITE);
                x.setText("Space Invaders Game\nCreated By: Sarabjit Bakshi \n\nRules: \nShoot the aliens and UFOs to "
                        + "get Points!\nTry to get as many points as possible before"
                        + " \nlosing 3 lives!\n \nMove with the arrow keys!\nFire using the spacebar!\nPause by pressing p!"
                        + "\n\nAlien=20 pts\nUFO=50pts "
                );

                x.getWrapStyleWord();
                x.setVisible(true);
                x.setLineWrap(true);
                //x.setWrapStyleWord(true);
                x.setEditable(false);
                helpwin.add(x);
                helpwin.setResizable(false);
                helpwin.add(P);
                helpwin.setVisible(true);
            }
        }
        );
        button[2].addActionListener(new ActionListener() // clear everything. reinitialize everything.
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                highscores(true);

                //k.dispose();
            }
        }
        );
        button[3].addActionListener(new ActionListener() // clear everything. reinitialize everything.
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        }
        );

    }

    class coop extends JFrame {

        private coop() {
            setSize(600, 600);
            setResizable(false);
            String[] buttonString = {"New Game", "About", "High Scores",
                "Quit", ""};
            int i = 0;
            int newy = 310;

            //    Font font1 = new Font("ariel", )
            while (i < 5) {

                button[i] = new JButton(buttonString[i]);
                //button[i].setLocation(200,newy);
                //button[i].setSize(200,50);
                //button[i].repaint();
                button[i].setBounds(185, newy, 200, 50);

                //button[i].setBorderPainted(false);
                button[i].setFocusPainted(false);
                //button[i].setColor(Color.GREEN);
                button[i].setForeground(Color.GREEN);
                button[i].setContentAreaFilled(false);
                button[i].setFont(new Font("Arial", Font.BOLD, 28));
                newy = newy + 50;
                //  button[i].setVisible(true);
                //   button[i].addActionListener(f);
                i++;

            }
            setBackground(Color.BLACK);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            //button[0].setFont(new Font("Arial", Font.PLAIN, 20));
            // button[1].setFont(new Font("Arial", Font.PLAIN, 20));
            getContentPane().add(button[0]);
            getContentPane().add(button[1]);
            getContentPane().add(button[2]);
            getContentPane().add(button[3]);
            getContentPane().add(button[4]);
            button[0].setVisible(true);
            button[1].setVisible(true);
            button[2].setVisible(true);
            button[3].setVisible(true);
            button[4].setVisible(false);
            //addKeyListener(new AL());
            // setVisible(true);        ***********

            // setVisible(true);
        }

    }

    public Model() {
        mainscreen();

        //  guys[0][1]=true;
        int i = 0;
        while (i < 4) {
            int x = 0;
            while (x < 6) {
                guys[x][i] = true;
                x++;
            }
            i++;
        }

    }

    int count;
    Timer t;
    Toolkit toolkit;

    public void function() {
        toolkit = Toolkit.getDefaultToolkit();
        t = new Timer();
        t.schedule(new RemindTask(), 0, //initial delay
                15);  //subsequent rate

    }
    int usingnum = 0;
    boolean wassaucer = false;

    class RemindTask extends TimerTask {

        public void run() {
            if(!pressedpause)
            {
            YW = YW - 5;

            if (YW == -15 || hit())// || hitSaucer()) {
            {

                YW = 700;
                XW = 700;
                if (IWON()) {
                    t2.cancel();

                    speed = speed / 2;
                    BGReset();

                }

                t.cancel();
            }
            }
        }

    }

    boolean IRS = false;

    public void RandomShooter() // make someone shoot. ************** fix
    {
        IRS = true;
        int NC = 0;// number columns
        int x = 0;
        //int y=0;
        boolean C1 = false;
        boolean C2 = false;
        boolean C3 = false;
        boolean C4 = false;
        boolean C5 = false;
        boolean C6 = false;
        boolean[] CA = new boolean[6];

        CA[0] = false;
        CA[2] = false;
        CA[1] = false;
        CA[3] = false;
        CA[4] = false;
        CA[5] = false;
        boolean[][] C = new boolean[6][4];
        //C[0]=false;
        int annoying = 0;
        while (annoying < 6) {
            int annoying2 = 0;
            while (annoying2 < 4) {
                C[annoying][annoying2] = false;
                annoying2++;
            }
            annoying++;
        }

        while (x < 6) {
            int y = 3;
            while (y > -1) {
                if ((guys[x][y] == true) && (CA[x] == false)) {
                    CA[x] = true;
                    C[x][y] = true; // get the mapping of each existent guy.
                }
                y--;
            }
            x++;
        }
        int counter = 0;
        while (counter < 6) {
            if (CA[counter] == true) // idk if this is necessary
            {
                NC++;
            }
            counter++;
        }
        //now we know wihch
        boolean NF = true; // we have not found a column from wihch to shoot the ball
        int gotcha = 0;
        while (NF == true) {
            Random rand = new Random();
            int pN = rand.nextInt(6);
            if (CA[pN] == true) {
                gotcha = pN;
                NF = false;
            }
        }
        //whatever gotcha is, is the oclumb from which we shoot. this part willbe annoying.
        int co3 = 3;
        boolean nf2 = true;
        while (co3 > -1 && nf2 == true) {
            if (C[gotcha][co3] == true) {
                nf2 = false;
            }
            co3--;
        }
        co3++;
        //we nowhave the x and y coordinates of the guy from which we want to shoot.
        int sendingx = exez[gotcha];
        int sendingy = whyz[co3];

        //  if(!bolt1)
        //{
        bolt1 = true;
        justcreated1 = true;
        thirdfunction(sendingx, sendingy);
        // }
     /*   else if(!bolt2)// creating second bolt
         {
         // bolt2=true;
         justcreated2=true;
         seventhfunction(sendingx, sendingy);
         }*/
        IRS = false;
    }
    boolean justcreated1 = false;
    boolean justcreated2 = false;
    int blk1 = 0;
    int blk2 = 0;
    int blk3 = 0;
    int blk4 = 0;

    private void gameover() {

        if (!lost) {
            int L = EF("BM", "R");
            int C;
            int y;
            if (BGM() > 0) {

                C = EF("RM", "C");
                y = exez[C] + 40;
            } else {
                C = EF("LM", "C");
                y = exez[C];
            }
            if ((!life1 && !life2 && !life3) || (whyz[L] > 480 && y > x && y < (x
                    + 50))) {
                lost = true;
                highscores(true);
            }
        }

    }
    boolean lost = false;

    public void FireUpscreen() {

        frame = new JFrame();
        setVisible(true);
        frame.setSize(600, 600);
        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(new AL());
        fourthfunction();
        x = 0;
        y = 450;
        x2 = 500;
        y2 = 20;
        LX2 = 0;
        Ly2 = 0;

        BGReset();
        RandomShooter();
        count = 0;
    }

    Timer t2;
    Toolkit toolkit2;
    int speed = 500;

    public void anotherfunction() {
        toolkit2 = Toolkit.getDefaultToolkit();
        t2 = new Timer();
        t2.schedule(new RemindTask2(), 0, //initial delay
                speed);  //subsequent rate

    }

    boolean open = false;
    boolean closed = true;
    boolean jshift = false;

    class RemindTask2 extends TimerTask {
        // int numWarningBeeps = 3;

        public void run() {
            if(!pressedpause)
            {
            if (open) {
                open = false;
                closed = true;
            } else {
                closed = false;
                open = true;
            }

            int RMC = EF("RM", "C");
            int LMC = EF("LM", "C");

            if (exez[RMC] < 530 && exez[LMC] > 5 || jshift) { //r5y r1y
                jshift = false;
                int j = 0;
                while (j < 6) {
                    exez[j] = exez[j] + BGM();
                    j++;
                }

            } else// if (exez[RMC] >= 530) // hit far right side, start moving left.
            {

                jshift = true;
                if (movR) {
                    movR = false;
                    movL = true;
                } else {
                    movR = true;
                    movL = false;
                }

                int i = 0;
                while (i < 4) {
                    whyz[i] = whyz[i] + 30;
                    i++;
                }
            }
            }
        }
    }

    public int BGM() // velocity for bad guys. left or right depending on what is happening
    {
        if (movR == true) {
            return 10;
        } else // we are moving left...
        {
            return -10;
        }
    }

    Timer t3;
    Toolkit toolkit3;

    public void thirdfunction(int x, int y) {
        toolkit3 = Toolkit.getDefaultToolkit();
        t3 = new Timer();
        t3.schedule(new RemindTask3(x, y), 1400, //initial delay
                40);  //subsequent rate

    }

    class RemindTask3 extends TimerTask {

        int needx;
        int needy;

        boolean firstrunthru = true;

        boolean changein2 = false;
        boolean changein3 = false;

        RemindTask3(int x, int y) {
            needx = x;

            needy = y;

        }

        public void run() {
           if(!pressedpause)
           {
            if (lost) {
                t3.cancel();
            }
            firstrunthru = false;

            if (justcreated1 && !jshift) { // we are now creating a ball

                //bolt1=true;
                justcreated1 = false;

                if (BGM() > 0) {
                    EBX = needx + 48; //15

                } else {
                    EBX = needx - 15;
                }
                EBY = needy + 25;
                bolt1 = true;
                theirball = true;

            } else { // bolts are moving

                EBY = EBY + 5;
                if (EBY > 528) {
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        if (!IRS) {
                            RandomShooter();
                        }
                    }

                } else if (EBX > 24 && EBX < 108 && EBY > 405 && blk1 < 8)// hit first blockade
                {
                    blk1++;
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        if (!IRS) {
                            RandomShooter();
                        }
                    }

                } else if (EBX > 164 && EBX < 248 && EBY > 405 && blk2 < 8)// hit 2nd blockade
                {
                    blk2++;
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        // turnOffBolt(i);
                        if (!IRS) {
                            RandomShooter();
                        }
                    }

                } else if (EBX > 304 && EBX < 389 && EBY > 405 && blk3 < 8)// hit third blockade
                {
                    blk3++;
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        // turnOffBolt(i);
                        if (!IRS) {
                            RandomShooter();
                        }
                    }

                } else if (EBX > 443 && EBX < 527 && EBY > 405 && blk4 < 8)//hit fourth blockade
                {
                    blk4++;
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        if (!IRS) {
                            RandomShooter();
                        }
                    }

                } else if (theyhit()) {
                    createmyExpl();
                    if (life1) {
                        if (life2) // currently have atleast 2 lives
                        {
                            if (life3)//actually have 3.. but just lost the third
                            {
                                life3 = false;
                            } else // only have 2, losing second
                            {
                                life2 = false;
                            }
                        } else {
                            life1 = false;
                        }
                        gameover(); //check to see if game is over
                    }

                    // x = 1000;
                    //     theirball = false;
                    bolt1 = false;
                    t3.cancel();
                    if (!bolt2) {
                        theirball = false;
                        if (!IRS) {
                            RandomShooter();
                        }//maybe change

                    }

                }
                if (!bolt1 && !bolt2 && !IRS) {
                    RandomShooter();
                }
            }
           }
        }
    }

    boolean spotforadd;
    JTable scoretable;
    JLabel namelabel;
    JLabel scorelabel;
    JTextField namefield;
    JTextField scorefield;
    JLabel newname;
    JButton submitbutton;
    JButton quitbutton;
    JTextField c1;
    JTextField c2;

    JLabel kk;
    JButton newgamebutton;
    boolean pressedsubmit = false;

    private void highscores(boolean o) {

        spotforadd = o;
        JFrame L = new JFrame();

        //setLocationRelativeTo(null);
        //getContentPane().setLayout(null);
        scoretable = new JTable(4, 2);
        scoretable.setEnabled(false);
        //scoretable
        Color newguy = new Color(238, 238, 238);
        scoretable.setBackground(newguy);
        scoretable.setBounds(20, 70, 250, 120);
        Container content = L.getContentPane();
        c1 = new JTextField("");
        c1.setEditable(false);
        c1.setBounds(19, 40, 126, 30);
        c1.setHorizontalAlignment(JTextField.CENTER);
        content.add(c1);

        c2 = new JTextField("");
        c2.setHorizontalAlignment(JTextField.CENTER);
        c2.setEditable(false);
        c2.setBounds(144, 40, 126, 30);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        c1.setBorder(border);
        c2.setBorder(border);
        //add(c1);
        content.add(c2);

        namelabel = new JLabel("Names");
        Font k = new Font("ariel", Font.BOLD, 20);
        namelabel.setFont(k);
        namelabel.setForeground(Color.WHITE);
        namelabel.setBounds(48, 6, 80, 20);
        //namelabel.setLocation(10, 10);

        scorelabel = new JLabel("Scores");
        scorelabel.setForeground(Color.WHITE);
        scorelabel.setFont(k);
        scorelabel.setBounds(174, 6, 80, 20);

        content.add(namelabel);
        content.add(scorelabel);
        //content.add(scoretable);

        scoretable.setRowHeight(0, 30);
        scoretable.setRowHeight(1, 30);
        scoretable.setRowHeight(2, 30);
        scoretable.setRowHeight(3, 30);
        scoretable.setRowHeight(4, 30);

//scoretable.setColumnWidth(4, 40);
//scoretable.set
        //  content.add(scorelabel);
        //    scoretable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );    
        //scoretable.setPreferredScrollableViewportSize(newDimension(200,200));
        if (!spotforadd) {
            L.setSize(300, 230);
        } else // make room for initials, button...
        {
            newname = new JLabel("Your Initials:");
            newname.setForeground(Color.WHITE);
            newname.setFont(k);
            newname.setBounds(35, 200, 580, 50);
            namefield = new JTextField();
            namefield.setBounds(160, 212, 50, 30);

            submitbutton = new JButton("Submit");
            submitbutton.setContentAreaFilled(false);
            submitbutton.setFocusPainted(false);
            submitbutton.setFont(new Font("ariel", Font.PLAIN, 10));
            submitbutton.setBounds(217, 216, 70, 20);
            submitbutton.setForeground(Color.WHITE);

            quitbutton = new JButton("Quit");
            quitbutton.setContentAreaFilled(false);
            quitbutton.setFocusPainted(false);
            quitbutton.setFont(new Font("ariel", Font.PLAIN, 15));
            quitbutton.setBounds(98, 258, 100, 35);
            quitbutton.setForeground(Color.WHITE);

            newgamebutton = new JButton("New Game");
            newgamebutton.setContentAreaFilled(false);
            newgamebutton.setFocusPainted(false);
            newgamebutton.setFont(new Font("ariel", Font.PLAIN, 15));
            newgamebutton.setBounds(50, 250, 115, 35);
            newgamebutton.setForeground(Color.WHITE);

            content.add(newname);
            content.add(namefield);
            content.add(submitbutton);
            content.add(quitbutton);
            //content.add(newgamebutton);

            submitbutton.addActionListener(new ActionListener() // clear everything. reinitialize everything.
            {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                //take text from textfield.. do somethin with it
                    // take the txt from the field and put it into the high scores thing

                    String s = namefield.getText();
                    System.out.println(s);
                    c1.setText(s);
                    content.add(c1);
                    c2.setText(String.valueOf(score));
                    pressedsubmit = true;
                    content.add(c2);
                }
            }
            );
            newgamebutton.addActionListener(new ActionListener() // clear everything. reinitialize everything.
            {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    imded = false;
                    t.cancel();
                    t2.cancel();
                    t3.cancel();
                    t4.cancel();
                    t5.cancel();
                    t6.cancel();

                    lost = false;
                    newgame();
                    FireUpscreen();

                    //somehow instantiate a new game
                    L.dispose();

                }
            }
            );
            quitbutton.addActionListener(new ActionListener() // clear everything. reinitialize everything.
            {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);

                }
            }
            );

            L.setSize(300, 340);
        }

        L.getContentPane().add(scoretable);
        L.setLayout(new BorderLayout());
        if (pressedsubmit) /* {
         /*   pressedsubmit=false;
         L.getContentPane().add(kk);
         }*/ //add(new JScrollPane(scoretable), BorderLayout.CENTER);
        {
            L.setTitle("High Scores");
        }
        L.setResizable(false);
        L.setLocationRelativeTo(null);
        JPanel P = new JPanel();
        P.setBackground(Color.BLACK);
        L.add(P);
        L.setBackground(Color.BLACK);

        // L.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        L.dispose();
        L.setVisible(true);

    }

    private int numberofbolts() {
        int i = 0;

        if (bolt1) {
            i++;
        }
        if (bolt2) {
            i++;
        }
        if (bolt3) {
            i++;
        }
        return i;
    }

    private void turnOffBolt(int i) {

        if (i == 0) {
            bolt1 = false;
        }
        if (i == 1) {
            bolt2 = false;
            //wasthere2=false;
        }
        if (i == 2) {
            bolt3 = false;
            //   wasthere3=false;
        }
        if (!bolt1 && !bolt2 && !bolt3) {
            theirball = false;
        }

    }

    private boolean hitSaucer() {
        //  g.drawImage(SS, 140, 60, this);
        if ((XW + 25) > (saucerx + 5) && (XW + 21) < (saucerx + 67) && (YW
                + 10) < 80 && (YW + 10) > 65) {
            return true;
        } else {
            return false;
        }
    }

    private void BGReset() {
        lost = false;
        life1 = true;
        life2 = true;
        life3 = true;

        int i = 0; // put all guys back on screen
        while (i < 6) {

            int j = 0;
            while (j < 4) {
                guys[i][j] = true;
                j++;
            }
            i++;
        }

        //reset all original positions
        int j = 0;
        int oinitial = 50;
        while (j < 4) {
            whyz[j] = oinitial;
            oinitial = oinitial + 50;
            j++;
        }

        i = 0;
        int initial = 6;
        while (i < 6) {
            exez[i] = initial;
            initial = initial + 50;

            i++;
        }
        anotherfunction();
    }

    private boolean IWON() {
        int i = 0;
        boolean won = true;
        while (i < 6) {
            int j = 0;
            while (j < 4) {
                if (guys[i][j]) {
                    won = false;
                }

                j++;
            }
            i++;
        }
        return won;
    }

    Timer t4;
    Toolkit toolkit4;

    public void fourthfunction() { // make space 
        toolkit4 = Toolkit.getDefaultToolkit();
        t4 = new Timer();
        t4.schedule(new RemindTask4(), 40000, //initial delay  *******40000
                25);  //subsequent rate

    }

    boolean EXLIFE = false;
    int saucerx = 550;

    class RemindTask4 extends TimerTask {

        RemindTask4() {

            saucerx = 550;
        }

        public void run() {
            if(!pressedpause)
            {
            EXLIFE = true;
            saucerx = saucerx - 5;
            // if (saucerx == -65 ){//|| hitSaucer()) {
            if (hitSaucer() || saucerx == -65) {
                EXLIFE = false;
                if (hitSaucer()) {
                    //  System.out.println("yup it thinks it hit it");
                    t.cancel();
                    createExpl(saucerx - 5, 50);
                    XW = 700;
                    YW = 700;
                    score = score + 50;
                }

                this.cancel();
                fourthfunction();
            }
            }
        }
    }

    boolean imded = false;

    int count3;
    Timer t6;
    Toolkit toolkit6;
//int myexplX=0;

    public void createmyExpl() {
        count3 = 0;
        toolkit6 = Toolkit.getDefaultToolkit();
        t6 = new Timer();

        t6.schedule(new RemindTask6(), 0, //initial delay
                30);  //subsequent rate

    }
    boolean black = false;
    int blackcount = 0;

    class RemindTask6 extends TimerTask {

        //
        public void run() {
if(!pressedpause)
{
            if (count3 < 13) {
                imded = true;
                count3++;
            } else {
                imded = false;
                if (black) {
                    blackcount++;
                    if (blackcount == 20) {
                        blackcount = 0;
                        black = false;
                        x = 10;
                        t6.cancel();
                    }
                } else {
                    black = true;
                    blackcount = 1;
                }

            }
        }
        }
    }

    public boolean theyhit() {

        if (EBX > (x - 10) && EBX < (x + 50) && EBY > 510) {
            return true;
        } else {
            return false;
        }

    }

    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);

    }

    public int EF(String Inc, String CorR) // extreme finder, bottommost row or leftmost columb, etc
    {
        if (CorR.equals("C")) //column
        {
            if (Inc.equals("RM")) //leftmost or rightmost
            {
                int i = 5;

                while (i > -1) {
                    int j = 3;
                    while (j > -1) {
                        if (guys[i][j]) {

                            return i;

                        }
                        j--;
                    }

                    i--;
                }
            } else /// LM
            {
                int i = 0;

                while (i < 6) {
                    int j = 3;
                    while (j > -1) {
                        if (guys[i][j]) {
                            return i;

                        }
                        j--;
                    }

                    i++;
                }
            }
        } else // row
        {

            if (Inc.equals("TM")) //topmost or bottom-most
            {
                int i = 0;

                while (i < 4) {
                    int j = 5;
                    while (j > -1) {
                        if (guys[j][i]) {
                            return i;

                        }
                        j--;
                    }

                    i++;
                }
            } else {

                int i = 3;

                while (i > -1) {
                    int j = 5;
                    while (j > -1) {
                        if (guys[j][i]) {
                            return i;

                        }
                        j--;
                    }

                    i--;
                }
            }
        }
        return 0;
    }

    int expX = 0;
    int expY = 0;
    boolean kildone = false;
    int count2;
    Timer t5;
    Toolkit toolkit5;

    public void createExpl(int x, int y) {
        count2 = 0;
        toolkit5 = Toolkit.getDefaultToolkit();
        t5 = new Timer();
        expX = x;

        expY = y;
        t5.schedule(new RemindTask5(), 0, //initial delay
                30);  //subsequent rate

    }

    class RemindTask5 extends TimerTask {

        //
        public void run() {
            if(!pressedpause)
            {
            if (count2 < 5) {
                kildone = true;
                count2++;
            } else {
                kildone = false;
                t5.cancel();
            }
            }
        }
    }

    public void newgame() {
        imded = false;
        speed = 500;
        XW = 700;
        YW = 700;
        bolt1 = false;
        theirball = false;
        blk1 = 0;
        blk2 = 0;
        blk3 = 0;
        blk4 = 0;

        x = 0;
        y = 450;
        x2 = 500;
        y2 = 20;
        LX2 = 0;
        Ly2 = 0;

        BGReset();
        fourthfunction();
        RandomShooter();
        count = 0;
    }

    public boolean hit() {
        // did my ball hit a bad guy?
        //blockades
        if (XW > 20 && XW < 100 && YW < 465 && blk1 < 8)// hit first blockade
        {
            blk1++;
            return true;
        } else if (XW > 157 && XW < 240 && YW < 465 && blk2 < 8)// hit 2nd blockade
        {
            blk2++;
            return true;
        } else if (XW > 300 && XW < 380 && YW < 465 & blk3 < 8)// hit third blockade
        {
            blk3++;
            return true;
        } else if (XW > 438 && XW < 520 && YW < 465 && blk4 < 8)// hit fourth blockade
        {
            blk4++;
            return true;

        }

        int i = 0;
        while (i < 4) {
            if ((YW < (whyz[i] + 20)) && (YW > whyz[i])) {
                int j = 0;
                while (j < 6) {
                    if (((XW) > (exez[j] - 12)) && (XW < (exez[j] + 38))
                            && guys[j][i] == true) {
                        guys[j][i] = false;
                        createExpl(exez[j], whyz[i]);

                        score = score + 20;
                        return true;
                    }
                    j++;
                }

            }
            i++;

        }

        return false;
    }

    boolean theirball = false;
    int score = 0;

    private String scorE() {
        String s = "SCORE: " + String.valueOf(score);
        return s;
    }
    boolean life1 = true;
    boolean life2 = true;
    boolean life3 = true;

    public void paintComponent(Graphics g) {
        if (gamemode) {
            gameover();
            BufferedImage img = null;
            BufferedImage img2 = null;
            BufferedImage SS = null;
            BufferedImage myguy = null;
            BufferedImage imdestr = null;
            BufferedImage blowup = null;
            BufferedImage theirz = null;

            try {
                URL imageSrc = new File("C:\\Users\\xiaohui\\Pictures\\closedd.png").toURI().toURL();
                //lastlast     
                img = ImageIO.read(imageSrc);

                URL imageSrc999 = new File("C:\\Users\\xiaohui\\Pictures\\openn.png").toURI().toURL();
                //lastlast     
                img2 = ImageIO.read(imageSrc999);

                URL imageSrc2 = new File("C:\\Users\\xiaohui\\Pictures\\SSSS2.png").toURI().toURL();  // SSSS

                SS = ImageIO.read(imageSrc2);

                URL imageSrc3 = new File("C:\\Users\\xiaohui\\Pictures\\perfect.png").toURI().toURL();  // or last5              
                myguy = ImageIO.read(imageSrc3);

                URL imageSrc4 = new File("C:\\Users\\xiaohui\\Pictures\\bigr.png").toURI().toURL();     // tagain           
                imdestr = ImageIO.read(imageSrc4);

                URL imageSrc5 = new File("C:\\Users\\xiaohui\\Pictures\\blowup4.png").toURI().toURL();
                blowup = ImageIO.read(imageSrc5);

                URL imageSrc6 = new File("C:\\Users\\xiaohui\\Pictures\\lastone2.png").toURI().toURL();
// omgg       , TIA  
                theirz = ImageIO.read(imageSrc6);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (kildone) {
                g.drawImage(blowup, (expX - 10), (expY - 18), this);
            }

            if (EXLIFE) // saucer is on the screen
            {

                g.drawImage(SS, saucerx, 60, this); //saucerx
                //  System.out.println((XW+21));
            }

            if (imded) {
                g.drawImage(imdestr, (x - 7), 475, this); /// ebx-23
            }
        //ground

            //  g.drawImage(theirz,200,200,this);
            g.setColor(Color.WHITE);
            g.drawLine(0, 545, 600, 545);

            //g.drawLine(x, y, x2, y2);
            //score
            g.setColor(Color.WHITE);
            g.drawString(scorE(), 20, 40);
        //g.drawImage(theirz,50,510,this); // TEST LIGHTNING BOLT // if(ebx>x && ebx<(x+50) && EBY>510)

            // if i lost display you lose on the screen instead of notifyin, os high score can come up
            //g.draw
            g.setColor(Color.WHITE);
            g.drawString("Lives:", 450, 40);
            if (life1) {
                g.setColor(Color.WHITE);
                g.fillOval(485, 27, 15, 15);
            }
            if (life2) {
                g.setColor(Color.WHITE);
                g.fillOval(505, 27, 15, 15);
            }
            if (life3) {
                g.setColor(Color.WHITE);
                g.fillOval(525, 27, 15, 15);
            }

            // my weapon
            g.setColor(Color.WHITE);
            //g.fillOval(XW, YW, 10, 10);
            g.fillRect((XW + 21), (YW + 10), 2, 10);

            //test ball
            g.setColor(Color.BLUE);
        // g.fillOval((r1y-20), 60, 10, 10);

            //enemyball
            if (theirball && !lost) {
                if (bolt1) {
                    g.drawImage(theirz, EBX, EBY, this);
                }
                if (bolt2) {
                    g.drawImage(theirz, EBX2, EBY2, this);
                }
            }

            g.setColor(Color.BLACK);
            g.fillRect(0, 546, 600, 60);

            g.setColor(Color.GREEN);
            if (!imded && !black) {
                g.drawImage(myguy, x, (510 - 5), this);
            }

            if (hitSaucer() || superx != 0) {
                if (superx == 0) {
                    superx = 1;
                    neednum = 60;
                } else {
                    superx++;
                    neednum++;
                }
                if (superx == 10000) {
                    superx = 0;
                }

            }
            //defensive blocks
            g.setColor(Color.WHITE);

            //block1
            if (blk1 < 2) {
                g.fillRect(40, 420, 80, 60);
            } else if (blk1 > 1 && blk1 < 4) {
                Color newC = new Color(221, 232, 15);
                g.setColor(newC);
                g.fillRect(40, 420, 80, 60);
            } else if (blk1 > 3 && blk1 < 6) {
                Color newC = new Color(232, 145, 15);
                g.setColor(newC);
                g.fillRect(40, 420, 80, 60);
            } else if (blk1 > 5 && blk1 < 8) {
                Color newC = new Color(236, 22, 11);
                g.setColor(newC);
                g.fillRect(40, 420, 80, 60);
            }

            g.setColor(Color.WHITE);

            if (blk2 < 2) {
                g.fillRect(180, 420, 80, 60);
            } else if (blk2 > 1 && blk2 < 4) {
                Color newC = new Color(221, 232, 15);
                g.setColor(newC);
                g.fillRect(180, 420, 80, 60);
            } else if (blk2 > 3 && blk2 < 6) {
                Color newC = new Color(232, 145, 15);
                g.setColor(newC);
                g.fillRect(180, 420, 80, 60);
            } else if (blk2 > 5 && blk2 < 8) {
                Color newC = new Color(236, 22, 11);
                g.setColor(newC);
                g.fillRect(180, 420, 80, 60);
            }
            //g.fillRect(180, 420, 80, 60);

            g.setColor(Color.WHITE);
            if (blk3 < 2) {
                g.fillRect(320, 420, 80, 60);
            } else if (blk3 > 1 && blk3 < 4) {
                Color newC = new Color(221, 232, 15);
                g.setColor(newC);
                g.fillRect(320, 420, 80, 60);
            } else if (blk3 > 3 && blk3 < 6) {
                Color newC = new Color(232, 145, 15);
                g.setColor(newC);
                g.fillRect(320, 420, 80, 60);
            } else if (blk3 > 5 && blk3 < 8) {
                Color newC = new Color(236, 22, 11);
                g.setColor(newC);
                g.fillRect(320, 420, 80, 60);
            }

            g.setColor(Color.WHITE);
            if (blk4 < 2) {
                g.fillRect(460, 420, 80, 60);
            } else if (blk4 > 1 && blk4 < 4) {
                Color newC = new Color(221, 232, 15);
                g.setColor(newC);
                g.fillRect(460, 420, 80, 60);
            } else if (blk4 > 3 && blk4 < 6) {
                Color newC = new Color(232, 145, 15);
                g.setColor(newC);
                g.fillRect(460, 420, 80, 60);
            } else if (blk4 > 5 && blk4 < 8) {
                Color newC = new Color(236, 22, 11);
                g.setColor(newC);
                g.fillRect(460, 420, 80, 60);
            }

            // g.fillRect(0,500,600,500); // these are the bondaries of thegame
            g.setColor(Color.RED); // 50 bad guys, 10 wide 5 high

            int i = 0;
            while (i < 6) {
                int j = 0;
                while (j < 4) {
                    if (guys[i][j]) {

                        if (closed) {
                            g.drawImage(img, exez[i], whyz[j], this);
                        } else {
                            {
                                g.drawImage(img2, exez[i], whyz[j], this);
                            }
                        }
                    }
                    j++;
                }
                i++;
            }

            if (lost) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("ariel", Font.BOLD, 90));

                g.drawString("You Lose", 100, 270);
            }

            if (!lost) {
                repaint();
            }

        } else {
          //  g.setColor(Color.WHITE);

            // g.fillOval(40,40,40,40);
            Font font1 = new Font("ariel", Font.BOLD, 70);
            g.setFont(font1);
            g.setColor(Color.GREEN);
            g.drawString("Space Invaders", 38, 100); //NC 38

            BufferedImage main = null;
            try {

                URL imageSrc6 = new File("C:\\Users\\xiaohui\\Pictures\\anot.png").toURI().toURL();       // omgg       , TIA  
                main = ImageIO.read(imageSrc6);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            g.drawImage(main, -115, -220, this);

        }

    }

    int superx = 0;
    int neednum = 0;

    public static void main(String[] args) {

        Model l = new Model();
        //   l.FireUpscreen();
    }

}
