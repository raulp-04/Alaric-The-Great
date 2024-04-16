package entity;

import game.GamePanel;
import game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity {

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
    BufferedImage[] attackUp;
    BufferedImage[] attackDown;
    BufferedImage[] attackLeft;
    BufferedImage[] attackRight;
    int spriteNumAt = 0;


    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyHandler = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize);
        screenY = gp.screenHeight / 2 - (gp.tileSize);

        solidArea = new Rectangle(40, 55, 16, 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttack();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 30;
        worldY = gp.tileSize * 30;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {

        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/knight.png")));
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

    public void getPlayerAttack() {
        //17
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/knight.png")));

            attackUp = cutImage(img, 0, 17*32, new int[]{32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32});
            attackDown = cutImage(img, 0, 19*32, new int[]{32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32});
            attackLeft = cutImage(img, 0, 18*32, new int[]{32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32});
            attackRight = cutImage(img, 0, 16*32, new int[]{32, 32, 32, 32, 32, 32}, new int[]{32, 32, 32, 32, 32, 32});

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (attacking && hasSword) {
            attacking();
        } else if (keyHandler.upPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.downPressed || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            // TILE COLL
            collisionOn = false;
            gp.cChecker.collisionCheckTile(this);

            // OBJ COLL
            int objIndex = gp.cChecker.collisionCheckObject(this, true);
            pickUpObj(objIndex);

            // NPC COLL
            int npcIndex = gp.cChecker.collisionCheckEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK COLLISION, FALSE MEANS MOVING
            if (!collisionOn && !keyHandler.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right":  worldX += speed; break;
                }
            }
            gp.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 4) {
                if (spriteNumber == 7) {
                    spriteNumber = 0;
                } else {
                    spriteNumber++;
                }
                spriteCounter = 0;
            }

        }
    }

    public void attacking() {

        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNumAt = 0;
        }
        if(spriteCounter > 5 && spriteCounter <= 10) {
            spriteNumAt = 1;
        }
        if(spriteCounter > 10 && spriteCounter <= 15) {
            spriteNumAt = 2;
        }
        if(spriteCounter > 15 && spriteCounter <= 20) {
            spriteNumAt = 3;
            if (spriteCounter == 16) gp.playSE(3);
        }
        if(spriteCounter > 20 && spriteCounter <= 25){
            spriteNumAt = 4;
        }
        if (spriteCounter > 25) {
            spriteNumAt = 0;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void interactNPC(int index) {
        if (index != gp.NO_COLLISION) {
            if (gp.keyHandler.enterPressed) {
                gp.gameState = gp.DIALOG_STATE;
                gp.npc[index].speak();
            } else gp.ui.showMessage("PRESS ENTER TO INTERACT");
        } else if (gp.keyHandler.enterPressed && hasSword) {
            attacking = true;
        }
    }

    public void pickUpObj(int index) {
        if (index != gp.NO_COLLISION) {
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
                        int gemNum = rand.nextInt(900)+101;
                        hasGem += gemNum;
                        gp.ui.showMessage("CHEST HAD "+ gemNum + " GEMS");
                        gp.playSE(1);
                    } else gp.ui.showMessage("YOU NEED A KEY TO OPEN");
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage images = null;

        switch (direction) {
            case "up":
                if (!attacking) {
                    images = (!hasSword) ? walkUp[spriteNumber] : walkUpS[spriteNumber];
                } else if (hasSword) {
                    images = attackUp[spriteNumAt];
                }
                if (attacking && !hasSword) {
                    images = walkUp[spriteNumber];
                }
                break;
            case "down":
                if (!attacking) {
                    images = (!hasSword) ? walkDown[spriteNumber] : walkDownS[spriteNumber];
                } else if (hasSword) {
                    images = attackDown[spriteNumAt];
                }
                if (attacking && !hasSword) {
                    images = walkDown[spriteNumber];
                }
                break;
            case "left":
                if (!attacking) {
                    images = (!hasSword) ? walkLeft[spriteNumber] : walkLeftS[spriteNumber];
                } else if (hasSword) {
                    images = attackLeft[spriteNumAt];

                }
                if (attacking && !hasSword) {
                    images = walkLeft[spriteNumber];
                }
                break;
            case "right":
                if (!attacking) {
                    images = (!hasSword) ? walkRight[spriteNumber] : walkRightS[spriteNumber];
                } else if (hasSword) {
                    images = attackRight[spriteNumAt];

                }
                if (attacking && !hasSword) {
                    images = walkRight[spriteNumber];
                }
                break;
        }
        g2d.drawImage(images, screenX, screenY, 96, 96, null);
    }
}
