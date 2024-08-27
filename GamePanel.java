package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{ // This is an extention class of JPanel
    // Screen settings
    final int originalTileSize = 16; // 16 by 16
    final int scale = 3; // scaled by 3 

    final int tileSize = originalTileSize * scale; // 48 by 48 tile size This is the actual tile size
    final int maxScreenCol = 16; // This is the maximum size of the screen in the y coordinate direction
    final int maxScreenRow = 12; // This is the maximum size of the screen in the x coordinate direction
    final int screenWidth= tileSize * maxScreenCol; // 768 pixels (16 x 48)
    final int screenHeight= tileSize * maxScreenRow;// 576 pixels (12 x 48)

    //This object Thread is responsible for the time/clock of the gaame.
    Thread gameThread;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // The default background colour is black
        this.setDoubleBuffered(true); // This is for better game performance
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //Automatically call the run method
    }
    @Override
    public void run() { //thread causes the object's run method to be called in that separately executing thread.
        
        // We will create this game loop
        while(gameThread != null) { 
            // System.out.println("The game has begun!");

            // 1 UPDATE: update information such as a character postions
            update();
            // 2 DRAW: the screen with updated information
            repaint(); 
        }
    }

    public void update() {

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(100, 200, tileSize, tileSize);

        g2.dispose();
    }
}
