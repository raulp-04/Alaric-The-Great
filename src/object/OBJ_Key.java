package object;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        walkDown = new BufferedImage[1];
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            walkDown[0] = img.getSubimage(176, 128, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
