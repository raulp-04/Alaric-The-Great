package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Sword extends SuperObject{

    public OBJ_Sword() {
        name = "Sword";
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            image = img.getSubimage(16, 64, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
