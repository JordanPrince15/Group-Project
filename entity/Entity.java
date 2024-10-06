package entity;

import java.awt.image.BufferedImage;

public abstract class Entity{
    public int x, y;
    public int speed;
    public String type; // To distinguish between player and other entities
    public abstract void update(); // Make it an abstract method for subclasses
    public BufferedImage idle, upA, upB, downA, downB, leftA, leftB, rightA, rightB;
    public String direction;
}