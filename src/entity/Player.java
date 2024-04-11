package entity;

import game.GamePanel;
import game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    String direction;

    public final int screenX;
    public final int screenY;
    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyHandler = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize);
        screenY = gp.screenHeight / 2 - (gp.tileSize);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/knight.png"));
            walkRight = cutImage(img, 0, 160, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkUp = cutImage(img, 0, 192, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkDown = cutImage(img, 0, 224, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkLeft = new BufferedImage[walkRight.length];
            for (int i = 0; i < walkRight.length; i++) {
                walkLeft[i] = mirrorImage(walkRight[i]);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] cutImage(BufferedImage img, int x, int y, int []width, int []height) {
        BufferedImage[] images = new BufferedImage[width.length];
        for (int i = 0; i < width.length; i++) {
            images[i] = img.getSubimage(x, y, width[i], height[i]);
            x += width[i];
        }
        return images;
    }

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

    public void update() {
        if (keyHandler.upPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.downPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 4) {
                if (spriteNumber == 7) {
                    spriteNumber = 0;
                } else {spriteNumber++;}
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage images = null;

        switch (direction) {
            case "up":
                images = walkUp[spriteNumber];
                break;
            case "down":
                images = walkDown[spriteNumber];
                break;
            case "left":
                images = walkLeft[spriteNumber];
                break;
            case "right":
                images = walkRight[spriteNumber];
                break;
        }
        images = Scalr.resize(images, 100, 100); // RESIZE SPRITE
        g2d.drawImage(images, screenX, screenY, null);
    }
}
