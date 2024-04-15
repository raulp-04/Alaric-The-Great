package game;

import javax.swing.*;

public class Singleton extends JFrame {

    private static JFrame single_instance = null;

    public static synchronized JFrame getInstance() {
        if (single_instance == null)
            single_instance = new JFrame();

        return single_instance;
    }

}