package object;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Banner extends Entity {
    public OBJ_Banner(GamePanel gp) {
        super(gp);
        name = "Banner";
        walkDown = new BufferedImage[1];
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            walkDown[0] = img.getSubimage(160, 26, 16, 38);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override public void draw(Graphics2D g2d) {

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
            g2d.drawImage(images, screenX, screenY,16*gp.scale, 38* gp.scale, null);
        }
    }
}
