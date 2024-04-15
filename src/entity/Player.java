package entity;

import game.GamePanel;
import game.KeyHandler;
import object.OBJ_Gems;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    public int hasGem = 0;
    boolean hasSword = false;
    public int hasKey = 0;
    BufferedImage[] walkUpS;
    BufferedImage[] walkDownS;
    BufferedImage[] walkLeftS;
    BufferedImage[] walkRightS;


    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyHandler = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize);
        screenY = gp.screenHeight / 2 - (gp.tileSize);

        solidArea = new Rectangle(40, 55, 16, 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 30;
        worldY = gp.tileSize * 30;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {

        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/knight.png"));
            // WITHOUT SWORD
            walkRight = cutImage(img, 0, 160, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkUp = cutImage(img, 0, 192, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkDown = cutImage(img, 0, 224, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkLeft = new BufferedImage[walkRight.length];
            for (int i = 0; i < walkRight.length; i++) {
                walkLeft[i] = mirrorImage(walkRight[i]);
            }

            // WITH SWORD
            walkUpS = cutImage(img, 0, 64, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkDownS = cutImage(img, 0, 128, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkLeftS = cutImage(img, 0, 96, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});
            walkRightS = cutImage(img, 0, 32, new int[]{32, 32, 32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32, 32, 32});

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
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.collisionCheckTile(this);

            int objIndex = gp.cChecker.collitionCheckObject(this, true);
            pickUpObj(objIndex);

            // CHECK COLLISION, FALSE MEANS MOVING
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed; break;
                    case "right":  worldX += speed; break;
                }
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

    public void pickUpObj(int index) {
        if (index != 999) {
            String name = gp.obj[index].name;
            switch (name) {
                case "Gem":
                    hasGem += 100;
                    gp.obj[index] = null;
                    gp.ui.showMessage("PICKED UP GEM");
                    gp.playSE(2);
                    break;
                case "Sword":
                    hasSword = true;
                    gp.obj[index] = null;
                    gp.ui.showMessage("PICKED UP SWORD AND SHIELD");
                    gp.playSE(2);
                    break;
                case "Key":
                    hasKey += 1;
                    gp.obj[index] = null;
                    gp.ui.showMessage("PICKED UP A KEY");
                    gp.playSE(2);
                    break;
                case "Chest":
                    if(hasKey>0) {
                        hasKey--;
                        gp.ui.showMessage("USED A KEY");
                        gp.obj[index] = null;
                        Random rand = new Random();
                        int gemNum = rand.nextInt(900)+100;
                        hasGem += gemNum;
                        gp.ui.showMessage("CHEST HAD "+ gemNum + " GEMS");
                        gp.playSE(1);
                    } else gp.ui.showMessage("YOU NEED A KEY TO OPEN");
                    break;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage images = null;

        switch (direction) {
            case "up":
                images = (!hasSword)? walkUp[spriteNumber] : walkUpS[spriteNumber];
                break;
            case "down":
                images = (!hasSword)? walkDown[spriteNumber] : walkDownS[spriteNumber];
                break;
            case "left":
                images = (!hasSword)? walkLeft[spriteNumber] : walkLeftS[spriteNumber];
                break;
            case "right":
                images = (!hasSword)? walkRight[spriteNumber] : walkRightS[spriteNumber];
                break;
        }
        g2d.drawImage(images, screenX, screenY, 96, 96, null);
    }
}
