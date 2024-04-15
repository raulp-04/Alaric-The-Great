package game;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arial_30;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_30 = new Font("Arial", Font.PLAIN, 30);
    }

    public void showMessage(String msg) {
        message = msg;
        messageOn = true;
    }

    public void draw(Graphics2D g2d) {

        g2d.setFont(arial_30);
        g2d.setColor(Color.WHITE);
        g2d.drawString("SCORE "+gp.player.hasGem, 25, 45);
        g2d.drawString("KEYS "+gp.player.hasKey, 25, 75);

        if (messageOn) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(15, 100, 265, 35);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(10, 100, 5, 35);
            g2d.fillRect(275, 100, 5, 35);
            g2d.fillRect(10, 100, 265, 5);
            g2d.fillRect(10, 130, 265, 5);
            g2d.setFont(g2d.getFont().deriveFont(15F));
            g2d.drawString(message, 25, 123);

            messageCounter++;
            if (messageCounter == 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
