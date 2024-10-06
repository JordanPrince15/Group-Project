

package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.James;
import entity.Platform;
import entity.Player;



public class GamePanel extends JPanel implements Runnable{ // This is an extention class of JPanel
    // Screen settings
    final int originalTileSize = 16; // 16 by 16
    final int scale = 3; // scaled by 3

    private Thread gameThread;
    private BufferedImage background;
    public final int tileSize = originalTileSize * scale; // 48 by 48 tile size This is the actual tile size
    final int maxScreenCol = 16; // This is the maximum size of the screen in the y coordinate direction
    final int maxScreenRow = 12; // This is the maximum size of the screen in the x coordinate direction
    final int Width= tileSize * maxScreenCol; // 768 pixels (16 x 48)
    final int Height= tileSize * maxScreenRow;// 576 pixels (12 x 48)
    private List<Platform> platforms = new ArrayList<>();

    //This is for the second moving platform
    private int secondPlatformX = 0;     // Initial x position of the second platform
    private int secondPlatformYStart = 240;
    private int secondPlatformYEnd = 10;
    private int secondPlatformEndX = 400;
    private int platformSpeed = 2;       // Speed of platform movement

    //FPS
    int FPS = 60;
    KeyHandler keyHandle = new KeyHandler();

    //This object Thread is responsible for the time/clock of the gaame.
    WindowSwitcher mainFrame;
    Player player = new Player(this, keyHandle);
    James james  = new James(this);

    //Set player's default postion
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4; // This moves by 4 pixels


    //Define game states
    public static final int STATE_PLAY = 0;
    public static final int STATE_TRANSITION = 1;
    private int gameState = STATE_PLAY;

    //This the method for thew gamepanel it also shows us where tghepanels are located on the screen
    public GamePanel () {
        platforms.add(new Platform(510, 150, 1200, 10)); // platform 1
        platforms.add(new Platform(secondPlatformX, secondPlatformYStart, secondPlatformEndX, secondPlatformYEnd));
        platforms.add(new Platform(652, 300, 800, 10)); // platform 3
        platforms.add(new Platform(300, 400, 300, 10)); // platform 4
        platforms.add(new Platform(500, 500, 800, 10)); //platform 5
        platforms.add(new Platform(0, 600, 800, 10)); // platform 6
        
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(Color.black); // The default background colour is black
        this.setDoubleBuffered(true); // This is for better game performance
        this.addKeyListener(keyHandle); // Recognizes the keyHandler
        this.setFocusable(true); // So teh Game Panel can focus on to recieve the key input
        loadBackground(); // Load the background
        gameUpdate();
       
    }

    public GamePanel(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    

    private void loadBackground() {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/main/background.png"));
        } catch (IOException e) {
            
        }
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //Automatically call the run method
    }


    @Override
    public void run() { //thread causes the object's run method to be called in that separately executing thread.
        double drawInterval  = 1000000000/FPS; //This means 1 second divided by 60 fps or 0.016667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;
        // We will create this game loop
        while(gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            // System.out.println("The game has begun!");
            if(timer/ drawInterval >= 1){
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: "+ drawCount);
                drawCount = 0;
                timer = 0;
            }

            
            // 1 UPDATE: update information such as a character postions
            update();
            updatePlatformPosition();
            // 2 DRAW: the screen with updated information
            repaint(); // Calling paintComponent method
    
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // This updates the position of the second moving platform
private void updatePlatformPosition() {
    secondPlatformX += platformSpeed;

    // Check if the platform hits the screen boundaries and reverse direction
    if (secondPlatformX <= 0 || secondPlatformX + secondPlatformEndX >= 600) {
        platformSpeed = -platformSpeed; // Reverse the direction
    }

    // Update the second platform's position in the platforms list
    platforms.get(1).setX(secondPlatformX);  // Assuming the second platform is at index 1
}
    public void update() {
        player.update();
        gameUpdate();
    }

    @Override
    public void paintComponent(Graphics graphics) { // Built in method in java
        super.paintComponent(graphics);
             
             
            
    
        // Fill the background with a solid color
        graphics.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        Graphics2D graphics2 = (Graphics2D)graphics;

        for (Platform platform : platforms) {
            platform.draw(graphics);
        }
        player.draw(graphics2);
        if (gameState == STATE_PLAY) {
            //Draw the gameplay elements (like James)
            james.draw(graphics2);

        } else if (gameState == STATE_TRANSITION) {
            // Draw the transition screen (this can be a new screen or message)
            graphics2.drawString("You reached the goal! Transitioning to the next screen...", 400, 300);
        }
        graphics2.dispose(); // releases any system resources that it is using, this saves memory
    }
    
    // This update function allows us to see where if the game is in the play stae or the transistion state 
    public void gameUpdate() {
        // Only update game elements when in the playing state
        if (gameState == STATE_PLAY) {
            // Update the player's position (this would happen in james.update())
            // If the player reaches (1000, 50), change the game state
            if (player.x >= 1000 && player.y <= 50) {
                gameState = STATE_TRANSITION;
                mainFrame.switchToScreen("WinScreen");
            }
        }
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
