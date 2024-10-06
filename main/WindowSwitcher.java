package main;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowSwitcher extends JFrame {

    JPanel mainPanel;
    CardLayout cardLayout;

    public WindowSwitcher() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize the game screen and win screen
        GamePanel gamePanel = new GamePanel();
        WinScreen winPanel = new WinScreen();

        // Add the screens to the CardLayout
        mainPanel.add(gamePanel, "GameScreen");
        mainPanel.add(winPanel, "WinScreen");

        // Add the mainPanel to the frame
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // Start on the game screen
        cardLayout.show(mainPanel, "GameScreen");
    }

    // Method to switch screens
    public void switchToScreen(String screenName) {
        if (screenName.equals("GameScreen") || screenName.equals("WinScreen")) {
            cardLayout.show(mainPanel, screenName);
        } else {
            System.err.println("Screen not found: " + screenName);
        }
    }
    public static void main(String[] args) {
        new WindowSwitcher();  // Start the game
    }
}