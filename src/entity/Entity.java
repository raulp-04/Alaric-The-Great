package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    BufferedImage[] walkUp;
    BufferedImage[] walkDown;
    BufferedImage[] walkLeft;
    BufferedImage[] walkRight;
    int spriteCounter = 0;
    int spriteNumber = 0;
}
