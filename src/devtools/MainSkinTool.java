package devtools;

import core.GamePanel;

import javax.swing.*;

// JA NAO PRECISO
public class MainSkinTool {
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.skinToolMode = true;

        JFrame window = new JFrame("Skin Tool");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.startGameThread();
    }
}