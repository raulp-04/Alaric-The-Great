package monster;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MON_Skeleton extends Entity {
    public MON_Skeleton(GamePanel gp) {
        super(gp);

        name = "Base Skeleton";
        type = 2;
        speed = 2;
        maxLife = 4;
        life = maxLife;

        solidArea = new Rectangle(11*3, 9*3, 10*3, 14*3);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("monster/Skeleton.png")));
            walkRight = cutImage(img, 0, 128, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkUp = cutImage(img, 0, 160, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkDown = cutImage(img, 0, 96, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkLeft = new BufferedImage[walkRight.length];
            for (int i = 0; i < walkRight.length; i++) {
                walkLeft[i] = mirrorImage(walkRight[i]);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        int npcIndex = gp.cChecker.collisionCheckEntity(this, gp.npc);
        int monsterIndex = gp.cChecker.collisionCheckEntity(this, gp.monsterArray);

        if (contactPlayer) {
            if (!gp.player.invincible) {
                gp.player.life--;
                gp.player.invincible = true;
            }
        }

        if (!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed; break;
                case "right":  worldX += speed; break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 16) {
            if (spriteNumber == 3) {
                spriteNumber = 0;
            } else {spriteNumber++;}
            spriteCounter = 0;
        }
    }

    @Override
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
            g2d.drawImage(images, screenX, screenY, 96, 96, null);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(screenX+ solidArea.x,screenY+ solidArea.y, solidArea.width, solidArea.height);

        }
    }
}
