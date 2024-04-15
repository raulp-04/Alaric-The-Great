package entity;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public String direction;
    BufferedImage[] walkUp;
    BufferedImage[] walkDown;
    BufferedImage[] walkLeft;
    BufferedImage[] walkRight;
    int spriteCounter = 0;
    int spriteNumber = 0;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int actionLockCounter;
    public String[] dialog = new String[20];
    public int dialogIndex = 0;

    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage[] cutImage(BufferedImage img, int x, int y, int []width, int []height) {
        BufferedImage[] images = new BufferedImage[width.length];
        for (int i = 0; i < width.length; i++) {
            images[i] = img.getSubimage(x, y, width[i], height[i]);
            x += width[i];
        }
        return images;
    }

    public void setAction() {}
    public void speak() {

        if (dialog[dialogIndex] == null) {
            dialogIndex = 0;
        }
        gp.ui.currentDialog = dialog[dialogIndex];
        dialogIndex++;
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update() {}

    public BufferedImage mirrorImage(BufferedImage img) {
        // Get source image dimension
        int width = img.getWidth();
        int height = img.getHeight();

        // BufferedImage for mirror image
        BufferedImage mImg = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);

        // Create mirror image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                int p = img.getRGB(lx, y);
                mImg.setRGB(rx, y, p);
            }
        }

        return mImg;
    }

    public void draw(Graphics2D g2d) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + 2*gp.tileSize> gp.player.worldX-gp.player.screenX &&
                worldY + 2*gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldX - 2*gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY - 2*gp.tileSize< gp.player.worldY+gp.player.screenY) { // END IF

            BufferedImage images = switch (direction) {
                case "up" -> walkUp[spriteNumber];
                case "down" -> walkDown[spriteNumber];
                case "left" -> walkLeft[spriteNumber];
                case "right" -> walkRight[spriteNumber];
                default -> null;
            };
            g2d.drawImage(images, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
