package monster;
import entity.Entity;
import game.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import static java.lang.Math.abs;

public class MON_Boss extends Entity {
    BufferedImage[] attackUp;
    BufferedImage[] attackDown;
    BufferedImage[] attackLeft;
    BufferedImage[] attackRight;

    private static MON_Boss singleInstance = null;
    private MON_Boss(GamePanel gp) {
        super(gp);

        name = "Goblin";
        type = 2;
        speed = 2;
        maxLife = 30;
        life = maxLife;

        solidArea = new Rectangle(11*10, 9*10, 10*10, 14*10);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public static synchronized  MON_Boss getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new MON_Boss(gp);
        }
        return singleInstance;
    }

    public void getImage(){
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("monster/Goblin_Rouge.png")));
            walkRight = cutImage(img, 0, 128, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkUp = cutImage(img, 0, 160, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkDown = cutImage(img, 0, 96, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            walkLeft = new BufferedImage[walkRight.length];
            for (int i = 0; i < walkRight.length; i++) {
                walkLeft[i] = mirrorImage(walkRight[i]);
            }

            attackUp = cutImage(img, 0, 13*32, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            attackDown = cutImage(img, 0, 12*32, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            attackRight = cutImage(img, 0, 13*32, new int[]{32, 32, 32, 32}, new int[]{32, 32, 32, 32});
            attackLeft = new BufferedImage[attackRight.length];
            for (int i = 0; i < attackRight.length; i++) {
                attackLeft[i] = mirrorImage(attackRight[i]);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean visible(){
        boolean visible = false;
        if (worldX + 2*gp.tileSize> gp.player.worldX-gp.player.screenX &&
                worldY + 2*gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldX - 2*gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY - 2*gp.tileSize< gp.player.worldY+gp.player.screenY) {
            visible = true;
        }
        return visible;
    }
    public void setAction() {
        if (visible()) {
            int playerX = gp.player.worldX;
            int playerY = gp.player.worldY;
            int x = solidArea.x+worldX;
            int y = solidArea.y+worldY;
            int distX = abs(x - playerX);
            int distY = abs(y - playerY);

            if (distX > distY) {
                    if (x < playerX) {
                        direction = "right";
                    } else {
                        direction = "left";
                    }
            } else {
                if (y < playerY) {
                    direction = "down";
                } else {
                    direction = "up";
                }
            }

        } else {
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
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    @Override public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.collisionCheckObject(this, true);
        gp.cChecker.collisionCheckTile(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.collisionCheckEntity(this, gp.npc);
        gp.cChecker.collisionCheckEntity(this, gp.monsterArray);

        if (contactPlayer) {
            attacking = true;
        }

        if (!collisionOn && !attacking) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed; break;
                case "right":  worldX += speed; break;
            }
        }
        if (!attacking) {
            spriteCounter++;
            if (spriteCounter > 16) {
                if (spriteNumber == 3) {
                    spriteNumber = 0;
                } else {
                    spriteNumber++;
                }
                spriteCounter = 0;
            }
        }
        else {
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNumber == 1) {
                    gp.playSE(3);

                    int currentWorldX = worldX;
                    int currentWorldY = worldY;
                    int solidAreaWidth = solidArea.width;
                    int solidAreaHeight = solidArea.height;

                    switch (direction) {
                        case "up": worldY -= attackArea.width; break;
                        case "down": worldY += attackArea.width; break;
                        case "left": worldX -= attackArea.width; break;
                        case "right": worldX += attackArea.width; break;
                    }

                    solidArea.width = attackArea.width;
                    solidArea.height = attackArea.height;
                    if (!gp.player.invincible) {
                        gp.player.life -= 4;
                        gp.playSE(6);
                        gp.player.invincible = true;
                        gp.ui.showMessage("LIFE DECREASED");
                    }
                    worldX = currentWorldX;
                    worldY = currentWorldY;
                    solidArea.width = solidAreaWidth;
                    solidArea.height = solidAreaHeight;

                }
                if (spriteNumber == 3) {
                    spriteNumber = 0;
                    attacking = false;
                } else {spriteNumber++;}
                spriteCounter = 0;
            }
        }
        if (invincible) {
            invincibleCounter++;
            speed = 0;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
                speed = 2;
            }
        }
    }
    @Override public void draw(Graphics2D g2d) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        BufferedImage images = switch (direction) {
            case "up" -> (!attacking)? walkUp[spriteNumber] : attackUp[spriteNumber];
            case "down" -> (!attacking)? walkDown[spriteNumber] : attackDown[spriteNumber];
            case "left" -> (!attacking)? walkLeft[spriteNumber] : attackLeft[spriteNumber];
            case "right" -> (!attacking)? walkRight[spriteNumber] : attackRight[spriteNumber];
            default -> null;
        };
        if (invincible) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (dying) {
            dyingAnimation(g2d);
        }
        g2d.drawImage(images, screenX, screenY, 320, 320, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2d.setColor(Color.BLACK);
        g2d.drawRect(screenX+ solidArea.x,screenY+ solidArea.y, solidArea.width, solidArea.height);
    }
    public void dyingAnimation(Graphics2D g2d) {
        dyingCounter++;
        short i = 5;
        if (dyingCounter <= i) {changeAlpha(g2d, 0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2d, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2d, 0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2d, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2d, 0f);if(dyingCounter==i*5)gp.playSE(11);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2d, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2d, 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2d, 1f);}
        if (dyingCounter > i*8) {
            alive = false;

        }
    }
    public void changeAlpha (Graphics2D g2d, float nr) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nr));
    }
    @Override public void checkDrop() {
        gp.player.hasKey = true;
        gp.ui.showMessage("OBTAINED THE KEY");
    }
}
