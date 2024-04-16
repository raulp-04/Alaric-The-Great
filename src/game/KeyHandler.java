package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.System.exit;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public boolean drawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        // MENU STATE
        if (gp.gameState == gp.MENU_STATE) {
            if (code == KeyEvent.VK_W) {
                gp.ui.command--;
                if (gp.ui.command < 0) gp.ui.command = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.command++;
                if (gp.ui.command > 3) gp.ui.command = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.command == 0) {
                    gp.gameState = gp.PLAY_STATE;
                    gp.playMusic(0);
                }
                if (gp.ui.command == 1) {
                    // TODO LATER
                }
                if (gp.ui.command == 2) {
                    gp.gameState = gp.CONTROL_STATE;
                }
                if (gp.ui.command == 3) exit(0);
            }
        }
        else if (gp.gameState == gp.CONTROL_STATE) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.MENU_STATE;
            }
        }
        // PLAY STATE
            else if (gp.gameState == gp.PLAY_STATE) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_T) {
                drawTime = !drawTime;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.PAUSE_STATE;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.stopMusic();
                gp.gameState = gp.MENU_STATE;
            }

        }
        // PAUSE STATE
        else if (gp.gameState == gp.PAUSE_STATE) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.PLAY_STATE;
            }
        }
        // DIALOG STATE
        else if (gp.gameState == gp.DIALOG_STATE) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.PLAY_STATE;
            }
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        // PLAY STATE
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
