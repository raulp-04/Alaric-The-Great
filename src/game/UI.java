package game;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    Graphics2D g2d;
    GamePanel gp;
    Font mPixel;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialog = "";

    public UI(GamePanel gp) {

        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/MP16SC.ttf");
            mPixel = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }


    }

    public void showMessage(String msg) {
        message = msg;
        messageOn = true;
    }

    public void drawPlay(Graphics2D g2d) {
        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(30F));
        g2d.setColor(Color.WHITE);
        g2d.drawString("SCORE " + gp.player.hasGem, 25, 45);
        g2d.drawString("KEYS " + gp.player.hasKey, 25, 75);

        if (messageOn) {
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(15, 100, 225, 35, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(15, 100, 225, 35, 10, 10);

            g2d.setFont(g2d.getFont().deriveFont(15F));
            g2d.drawString(message, 25, 123);

            messageCounter++;
            if (messageCounter == 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawPause(Graphics2D g2d) {


        String text = "PAUSED";
        int x = getXforCenter(text);
        int y = gp.screenHeight/2;

        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(30F));
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(x-60, y-42, 180, 52, 10 ,10);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x-60, y-42, 180, 52, 10 ,10);


        g2d.setFont(g2d.getFont().deriveFont(50F));
        g2d.drawString(text, x-50, y+2);
    }

    public void drawDialog(Graphics2D g2d) {

        // WINDOW
        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(30F));
        g2d.drawString(currentDialog, 25 ,45);
    }

    public void draw(Graphics2D g2d) {

        this.g2d = g2d;

        // PLAY STATE
        if (gp.gameState == gp.PLAY_STATE) {
           drawPlay(g2d);
        }
        if (gp.gameState == gp.DIALOG_STATE) {
            drawDialog(g2d);
        }
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPause(g2d);}
    }
    public int getXforCenter(String text) {

        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        return gp.screenWidth/2 - length/2;
    }

}
