package main;

import core.GamePanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);

        window.pack();

        window.setTitle("Dark Souls dMake");

        window.setLocationRelativeTo(null);

        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
