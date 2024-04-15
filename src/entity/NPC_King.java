package entity;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class NPC_King extends Entity{

    public NPC_King(GamePanel gp) {

        super(gp);
        direction = "down";
        speed = 1;
        solidArea = new Rectangle(0, 10, 48, 48);

        getNPCImage();
        setDialog();
    }

    public void getNPCImage() {

        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("NPC/king.png")));

            walkRight = cutImage(img, 0, 64, new int[]{32, 32, 32}, new int[]{32, 32, 32});
            walkUp = cutImage(img, 0, 96, new int[]{32, 32, 32}, new int[]{32, 32, 32});
            walkDown = cutImage(img, 0, 0, new int[]{32, 32, 32}, new int[]{32, 32, 32});
            walkLeft = cutImage(img, 0, 32, new int[]{32, 32, 32}, new int[]{32, 32, 32});
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialog() {

        dialog[0] = "Hello, Alaric.";
        dialog[1] = "I am king Arthur";
        dialog[2] = "You must retrieve the ancient crown";
    }

    @Override
    public void speak() {
        super.speak();
    }

    @Override
    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter == 80) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75 ) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.collisionCheckTile(this);
        gp.cChecker.checkPlayer(this);
        if (!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed; break;
                case "right":  worldX += speed; break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 6) {
            if (spriteNumber == 2) {
                spriteNumber = 0;
            } else {spriteNumber++;}
            spriteCounter = 0;
        }
    }

}
