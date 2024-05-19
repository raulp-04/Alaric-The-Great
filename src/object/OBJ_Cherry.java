package object;
import entity.Entity;
import game.GamePanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Cherry extends Entity {
        public OBJ_Cherry(GamePanel gp) {
            super(gp);
            name = "Cherry";
            walkDown = new BufferedImage[1];
            try {
                BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/health-potion.png"));
                walkDown[0] = img.getSubimage(224, 80, 16, 16);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
