package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import tile.Tile;

public class OBJ_Gems extends SuperObject {

    public OBJ_Gems() {
        name = "Gem";
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            image = img.getSubimage(208, 96, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
