package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public final class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandle;

    // Gravity variables
    private boolean isFalling = true; // Whether the player is falling
    private int playerYVelocity = 0; // Player's vertical velocity (how fast they are falling)
    private final int mg = 1; // Gravity strength, how much velocity increases each frame
    private final double maxFallSpeed = 9.8; // Acceleration due to gravity


    // Jumping variables
    private boolean jumping = false;
    private int jumpHeight = 300; // Maximum jump height
    private double jumpVelocity = 2;
    private double gravity = 0.5;   // Gravity effect due to jumping
    private int originalY;          // Store the original Y position for gravity
    

    public Player(GamePanel gp, KeyHandler keyHandle){
        this.gp = gp;
        this.keyHandle = keyHandle;
        type = "Player";
        setDefaultValues();
        getPlayerImage();
        loadJumpFrames();
        }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 2;
        direction = "down";
        originalY = y;
    }

    

    private void loadJumpFrames() {
        // Load jump frames as before
    }
    public void getPlayerImage(){
        try {
            downA = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            downB = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            leftA = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            leftB = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            rightA = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            upA = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
            upB = ImageIO.read(getClass().getResourceAsStream("/entity/player.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
       
        // This checks if the player is on the ground or a platform
        if (!isOnPlatform()) {
            // If not on a platform, apply gravity
            applyGravity();
        } else {
            // If the player is on a platform, stop falling
            isFalling = false;
            playerYVelocity = 0; // Reset the vertical velocity
        }

        // Update the player's Y position based on the vertical velocity
        y += playerYVelocity;
        List<Platform> platforms = gp.getPlatforms();
        
        if(keyHandle. leftPressed == true){
            direction = "left";
            x -= speed; //The player goes to the right
        }
        else if(keyHandle.rightPressed == true){
            direction = "right";
            x += speed; //The player goes to the left
        }

    
         // Jumping logic
         if (keyHandle.jumpPressed && !jumping) {
             jumping = true;
             originalY = y; // Set the original position when jump starts
             jumpVelocity = -Math.sqrt(2 * gravity * jumpHeight);
         }
     
         if (jumping) {
             // Update vertical position
            y += jumpVelocity;
            jumpVelocity += gravity; // Apply gravity

             // Check if the player has landed on any platform
            boolean landed = false;
            for (Platform platform : platforms) {
                if (y + gp.tileSize >= platform.y && // Player's bottom
                y + gp.tileSize <= platform.y + platform.height && // Within platform height
                x + gp.tileSize > platform.x && // Player's right side
                x < platform.x + platform.width) { // Player's left side

                     // Snap player to platform
                     y = platform.y - gp.tileSize; // Position player on top of the platform
                     jumping = false; // Reset jumping
                     jumpVelocity = 0; // Reset velocity
                     landed = true; // Mark that the player has landed
                     break; // Exit the loop after landing
                 }
             }
     
             // If the player hasn't landed, check if they have fallen past their original Y
             if (!landed && y >= originalY) {
                 y = originalY; // Snap to the ground
                 jumping = false; // Stop jumping
                 jumpVelocity = 0; // Reset velocity
             } else {
                // If the player is not jumping, apply gravity to fall
                y += gravity; // Always apply gravity to fall
                for (Platform platform : platforms) {
                    if (y + gp.tileSize >= platform.y && y + gp.tileSize <= platform.y + platform.height && x + gp.tileSize > platform.x &&  x < platform.x + platform.width) { // Player's left side
                        // Snap player to platform
                        y = platform.y - gp.tileSize; // Position player on top of the platform
                        jumping = false; // Reset jumping
                        jumpVelocity = 0; // Reset velocity
                        break; // Exit the loop after landing
                    }
                }
            }
         
        

        // Check if the player has landed
        if (y >= originalY) {
                    y = originalY; // Snap to original position
                    jumping = false; // Reset jumping state
                    jumpVelocity = 0; // Reset velocity
                }
        }

    }

    private void applyGravity() {
    isFalling = true;

    // Increase the player's vertical velocity, but don't exceed max fall speed
    if (playerYVelocity < maxFallSpeed) {
        playerYVelocity += mg;
        }
    }

    
    // Check if the player is on a platform
    private boolean isOnPlatform() {
        List<Platform> platforms = gp.getPlatforms(); // Get the platforms list

        for (Platform platform : platforms) {
            // Simple collision check: If player's feet touch a platform
            if (y + gp.tileSize >= platform.y && y + gp.tileSize <= platform.y + platform.height && x + gp.tileSize > platform.x && x < platform.x + platform.width) { // Player's left side
                return true; // Player is on this platform
            }
        }
        return false; // Not on any platform
    }

   

    public void draw(Graphics2D graphics2) {
        // graphics2.setColor(Color.white);

        // graphics2.fillRect(x, y , gp.tileSize, gp.tileSize);
        BufferedImage image = null;

            switch (direction) {
                case "up" -> image = upA;
                case "down" -> image = downB;
                case "left" -> image = leftA;
                case "right" -> image = rightA;
            }
            graphics2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
}
    


    
    