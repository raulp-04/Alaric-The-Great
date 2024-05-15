package object;

import entity.Entity;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/heart.png")));
            image = img.getSubimage(0, 0, 19, 18);
            image2 = img.getSubimage(19, 0, 19, 18);
            image3 = img.getSubimage(38, 0, 19, 18);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
