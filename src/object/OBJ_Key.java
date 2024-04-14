package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Key extends SuperObject{

    public OBJ_Key() {
        name = "Key";
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            image = img.getSubimage(176, 128, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
