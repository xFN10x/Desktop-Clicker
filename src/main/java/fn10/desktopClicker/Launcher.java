package fn10.desktopClicker;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Launcher {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("test");

        jFrame.setUndecorated(true);

        jFrame.setBackground(new Color(0,0,0,0));

        jFrame.add(new JButton("yippe"));

        jFrame.setVisible(true);

        
    }
}
