//package object;
//
//import entity.Entity;
//import game.GamePanel;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class SuperObject extends Entity {
//    GamePanel gp;
//    public BufferedImage image, image2, image3;
//    public String name;
//    public boolean collision = false;
//    public int worldX, worldY;
//
//    public SuperObject (GamePanel gp) {
//        super(gp);
//        this.gp = gp;
//    }
//
//    @Override
//    public void draw(Graphics2D g2d) {
//
//        int screenX = worldX - gp.player.worldX + gp.player.screenX;
//        int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//        if (worldX + 2*gp.tileSize> gp.player.worldX-gp.player.screenX &&
//                worldY + 2*gp.tileSize> gp.player.worldY-gp.player.screenY &&
//                worldX - 2*gp.tileSize< gp.player.worldX+gp.player.screenX &&
//                worldY - 2*gp.tileSize< gp.player.worldY+gp.player.screenY) { // END IF
//            g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//        }
//    }
//}
