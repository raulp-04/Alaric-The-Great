package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public String direction;
    BufferedImage[] walkUp;
    BufferedImage[] walkDown;
    BufferedImage[] walkLeft;
    BufferedImage[] walkRight;
    int spriteCounter = 0;
    int spriteNumber = 0;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;
}
