package object;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import entity.Entity;
import game.GamePanel;
import tile.Tile;

public class OBJ_Gems extends Entity {
    public OBJ_Gems(GamePanel gp) {
        super(gp);
        name = "Gem";
        walkDown = new BufferedImage[1];
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            walkDown[0] = img.getSubimage(208, 96, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
