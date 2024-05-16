package object;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Sword extends Entity {
    public OBJ_Sword(GamePanel gp) {
        super(gp);
        walkDown = new BufferedImage[1];
        name = "Sword";
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            walkDown[0] = img.getSubimage(16, 64, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
