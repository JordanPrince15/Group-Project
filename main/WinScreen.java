package main;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
public class win {

      public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("win condition");
        frame.setVisible(true);
        JLabel winLabel = new JLabel("Congratulations! You Win!");
        winLabel.setFont(new Font("Arial", Font.BOLD, 36));
        winLabel.setForeground(Color.WHITE);
      }
    }
