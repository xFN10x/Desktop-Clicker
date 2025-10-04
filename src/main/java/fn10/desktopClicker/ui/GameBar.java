package fn10.desktopClicker.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.ui.notTransparent.SettingsWindow;
import fn10.desktopClicker.ui.notTransparent.UpgradeWindow;

public class GameBar extends TransparentWindow {

    public final JLabel CoinsLabel = new JLabel("Coins: 0");
    public final JButton UpgradeButton = new JButton("Upgrades!");
    public final JButton SettingsButton = new JButton("Settings...");
    public final JButton SaveButton = new JButton("Save");
    public final JButton SaveExitButton = new JButton("Save & Exit");
    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

    public GameBar() {
        super(new Dimension(100, 175),false);

        setLayout(Lay);

        ExitButton.addActionListener(ac -> {
            System.exit(0);
        });

        SaveExitButton.addActionListener(ac -> {
            GameManager.SaveCurrentGame();
            
            System.exit(0);
        });

        SaveButton.addActionListener(ac -> {
            GameManager.SaveCurrentGame();
        });

        CoinsLabel.setForeground(Color.white);

        UpgradeButton.addActionListener(ac -> {
            UpgradeWindow.showUpgrades();
        });

        SettingsButton.addActionListener(ac -> {
            SettingsWindow.showWindow();
        });

        CoinsLabel.setAlignmentX(0.5f);
        UpgradeButton.setAlignmentX(0.5f);
        SettingsButton.setAlignmentX(0.5f);
        SaveButton.setAlignmentX(0.5f);
        SaveExitButton.setAlignmentX(0.5f);
        ExitButton.setAlignmentX(0.5f);

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                CoinsLabel.setText("Coins: " + GameManager.CurrentGame.Coins);
            }
            
        }, 1, 1);

        add(CoinsLabel);
        add(Box.createVerticalStrut(5));
        add(UpgradeButton);
        add(Box.createVerticalGlue());
        add(SettingsButton);
        add(Box.createVerticalStrut(5));
        add(SaveButton);
        add(Box.createVerticalStrut(5));
        add(SaveExitButton);
        add(Box.createVerticalStrut(5));
        add(ExitButton);
        
        setAlwaysOnTop(true);
    }

}
