package object;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        collision = true;
        walkDown = new BufferedImage[1];
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            walkDown[0] = img.getSubimage(192, 33, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
