package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest() {
        name = "Chest";
        collision = true;
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            image = img.getSubimage(192, 33, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
