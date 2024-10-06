package entity;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;


public class Platform extends Entity {
    public int x, y;        // Position of the platform
    public int width, height; // Size of the platform

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = "Platform"; // Set type

    }

  
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height); 
    }

    @Override
    public void update() {
        
        // This makes it so that Platforms don't need to update
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


}
