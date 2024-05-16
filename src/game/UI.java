package game;

import entity.Entity;
import object.OBJ_Heart;


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
    public int command = 1;
    public static boolean hasEnteredOnce = false;
    public BufferedImage fullH, halfH, blankH;

    public UI(GamePanel gp) {

        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/MP16SC.ttf");
            mPixel = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // CREATE OBJ
        Entity heart = new OBJ_Heart(gp);
        fullH = heart.image3;
        halfH = heart.image2;
        blankH = heart.image;
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
    public void drawPlayerLife(Graphics2D g2d) {
        int x = gp.tileSize * 13 - 29;
        int y = gp.tileSize - 22;
        int i = 0;
        // DRAW MAX HEART
        while (i < gp.player.maxLife/2) {
            g2d.drawImage(blankH, x, y-6, gp.tileSize+10, gp.tileSize+10, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize * 13 - 29;
        y = gp.tileSize - 22;
        i = 0;
        // DRAW CURRENT LIFE
        while (i < gp.player.life) {
            g2d.drawImage(halfH, x, y-6, gp.tileSize+10, gp.tileSize+10, null);
            i++;
            if (i < gp.player.life) {
                g2d.drawImage(fullH, x, y-6, gp.tileSize+10, gp.tileSize+10, null);
            }
            i++;
            x += gp.tileSize;
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
        g2d.drawString("SCORE " + gp.player.hasGem, 25, 45);
        g2d.drawString("KEYS " + gp.player.hasKey, 25, 75);


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
           drawPlayerLife(g2d);
        }
        // DIALOG STATE
        if (gp.gameState == gp.DIALOG_STATE) {
            drawDialog(g2d);
        }
        // PAUSE STATE
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPause(g2d);
            drawPlayerLife(g2d);
        }
        // GAME OVER STATE
        if (gp.gameState == gp.GAMEOVER_STATE) {
            drawGameOver(g2d);
        }
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

        if (hasEnteredOnce) {
            menuTitle = "RESUME";
            x = gp.tileSize;
            y = gp.tileSize * 6;
            g2d.setColor(Color.BLACK);
            g2d.drawString(menuTitle, x + 5, y + 5);
            g2d.setColor(Color.WHITE);
            g2d.drawString(menuTitle, x, y);
            if (command == 0) {
                g2d.setColor(Color.GRAY);
                g2d.drawString(menuTitle, x, y);
            }
        }

        menuTitle = "NEW GAME";
        x = gp.tileSize;
        y = gp.tileSize*7;
        g2d.setColor(Color.BLACK);
        g2d.drawString(menuTitle, x+5, y+5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(menuTitle, x, y);
        if (command == 1) {
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
        if (command == 2) {
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
        if (command == 3) {
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
        if (command == 4) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(menuTitle, x, y);
        }


    }
    public int getXforCenter(String text) {

        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        return gp.screenWidth/2 - length/2;
    }
    public void drawGameOver(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x;
        int y;
        String text;
        g2d.setFont(mPixel);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 80f));
        text = "GAME OVER";
        g2d.setColor(Color.gray);
        //shadow
        x = getXforCenter(text);
        y = gp.tileSize*2 - 15;
        g2d.drawString(text, x, y);
        //main
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);
        //retry
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48f));
        x = gp.tileSize;
        y = gp.tileSize*8;
        text = "RETRY";
        g2d.setColor(Color.black);
        g2d.drawString(text, x, y);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);
        if (command == 1) {
            g2d.setColor(Color.gray);
            g2d.drawString(text, x-4, y-4);
        }
        //titlescreen
        hasEnteredOnce = false;
        x = gp.tileSize;
        y = gp.tileSize*9;
        text = "BACK TO MENU";
        g2d.setColor(Color.black);
        g2d.drawString(text, x, y);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);
        if (command == 2) {
            g2d.setColor(Color.gray);
            g2d.drawString(text, x-4, y-4);
        }
        //quit
        x = gp.tileSize;
        y = gp.tileSize*10;
        text = "QUIT";
        g2d.setColor(Color.black);
        g2d.drawString(text, x, y);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);
        if (command == 3) {
            g2d.setColor(Color.gray);
            g2d.drawString(text, x-4, y-4);
        }
    }
}
