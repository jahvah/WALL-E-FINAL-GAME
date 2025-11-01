package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
 // screen settings

    final int originalTileSize = 16; //16x16 tile size
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile size
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //fps
    int FPS = 60;



    KeyHandlers keyH = new KeyHandlers();
    Thread gameThread; //game clock
    Player player = new Player(this,keyH);

    //set player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread !=null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;


                if (remainingTime < 0){

                    remainingTime = 0;


                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update(){

        player.update();


    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);


        g2.dispose();

    }
}


