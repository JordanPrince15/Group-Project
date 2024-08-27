//Java code here
package main;

import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // This line states that the window will only close when the close button is pressed by the user.
        window.setResizable(false);
        window.setTitle("Flappy bird");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // This is so we can see the display
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
} 

