package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    Graphics2D g2d;
    GamePanel gp;
    Font mPixel;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialog = "";
    public int command = 0;

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
        // MENU STATE
        if (gp.gameState == gp.MENU_STATE) {
            drawMenu(g2d);
        }
        // CONTROL STATE
        if (gp.gameState == gp.CONTROL_STATE) {
            drawControl(g2d);
        }
        // PLAY STATE
        if (gp.gameState == gp.PLAY_STATE) {
           drawPlay(g2d);
        }
        // DIALOG STATE
        if (gp.gameState == gp.DIALOG_STATE) {
            drawDialog(g2d);
        }
        // PAUSE STATE
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPause(g2d);}
    }
    public void drawControl(Graphics2D g2d) {
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("wallpaper/background.jpg")));
            g2d.drawImage(img, 0, 0, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        // TITLE NAME
        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 64F));
        String menuTitle = "Controls";
        int x = gp.tileSize/2;
        int y = gp.tileSize*2;


        // SHADOW
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        // MAIN COLOR
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        // MENU
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));

        menuTitle = "WASD for movement";
        x = gp.tileSize;
        y = gp.tileSize*6;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        menuTitle = "ENTER for attack, interaction";
        x = gp.tileSize;
        y = gp.tileSize*7;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        menuTitle = "P to pause game";
        x = gp.tileSize;
        y = gp.tileSize*8;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        menuTitle = "T to show draw time";
        x = gp.tileSize;
        y = gp.tileSize*9;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        menuTitle = "ESC go back to menu";
        x = gp.tileSize;
        y = gp.tileSize*10;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);


    }
    public void drawMenu(Graphics2D g2d) {

        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("wallpaper/background.jpg")));
            g2d.drawImage(img, 0, 0, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        // TITLE NAME
        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String menuTitle = "Alaric The Great";
        int x = gp.tileSize/2;
        int y = gp.tileSize*3;


        // SHADOW
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        // MAIN COLOR
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);

        // MENU
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));

        menuTitle = "NEW GAME";
        x = gp.tileSize;
        y = gp.tileSize*7;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);
        if (command == 0) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(menuTitle, x, y);
        }

        menuTitle = "LOAD GAME";
        x = gp.tileSize;
        y = gp.tileSize*8;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);
        if (command == 1) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(menuTitle, x, y);
        }

        menuTitle = "CONTROLS";
        x = gp.tileSize;
        y = gp.tileSize*9;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);
        if (command == 2) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(menuTitle, x, y);
        }

        menuTitle = "QUIT GAME";
        x = gp.tileSize;
        y = gp.tileSize*10;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);
        if (command == 3) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(menuTitle, x, y);
        }


    }
    public int getXforCenter(String text) {

        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        return gp.screenWidth/2 - length/2;
    }

}
