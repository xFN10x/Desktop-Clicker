package fn10.desktopClicker.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.ui.notTransparent.SettingsWindow;
import fn10.desktopClicker.ui.notTransparent.SpellWindow;
import fn10.desktopClicker.ui.notTransparent.UpgradeWindow;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.SaveManager;

public class GameMenu extends TransparentWindow {

    public static GameMenu CurrentMenu;

    public final JLabel CoinsLabel = new JLabel("Coins: 0");
    public final JProgressBar ManaBar = new JProgressBar(0, 100);
    public final JButton UpgradeButton = new JButton("Upgrades!");
    public final JButton SpellButton = new JButton("Spells!");
    public final JButton SettingsButton = new JButton("Settings...");
    public final JButton SaveButton = new JButton("Save");
    public final JButton SaveExitButton = new JButton("Save & Exit");
    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

    public void ApplySettings() {
        try {
            SaveManager save = SaveManager.load();

            setLocation(save.pos.getXAxisLocationFromWidth(
                    (int) (Double.valueOf(ImageUtilites.getScreenWorkingArea(this).getWidth()).intValue()
                            - (getWidth() * (save.pos.x)))),
                    save.pos.getYAxisLocationFromHeight(
                            (int) (Double.valueOf(ImageUtilites.getScreenWorkingArea(this).getHeight()).intValue()
                                    - (getHeight() * (save.pos.y)))));

            System.out.println(save.pos.getYAxisLocationFromHeight(
                    (int) (Double.valueOf(ImageUtilites.getScreenWorkingArea(this).getHeight()).intValue()
                            - (getHeight() * (save.pos.y)))));
            System.out.println(save.pos.getXAxisLocationFromWidth(
                    (int) (Double.valueOf(ImageUtilites.getScreenWorkingArea(this).getWidth()).intValue()
                            - (getWidth() * (save.pos.x)))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameMenu() {
        super(new Dimension(100, 200), false);
        CurrentMenu = this;
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

        SpellButton.addActionListener(ac -> {
            SpellWindow.showSpells();
        });

        SettingsButton.addActionListener(ac -> {
            SettingsWindow.showWindow();
        });

        CoinsLabel.setAlignmentX(0.5f);
        UpgradeButton.setAlignmentX(0.5f);
        ManaBar.setAlignmentX(0.5f);
        SpellButton.setAlignmentX(0.5f);
        SettingsButton.setAlignmentX(0.5f);
        SaveButton.setAlignmentX(0.5f);
        SaveExitButton.setAlignmentX(0.5f);
        ExitButton.setAlignmentX(0.5f);

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                CoinsLabel.setText("Coins: " + GameManager.CurrentGame.Coins);
                ManaBar.setValue((int) ((GameManager.CurrentGame.Mana / GameManager.CurrentGame.MaxMana) * 100));
            }

        }, 1, 1);

        add(CoinsLabel);
        add(Box.createVerticalStrut(5));
        add(UpgradeButton);
        add(Box.createVerticalStrut(5));
        add(ManaBar);
        add(Box.createVerticalStrut(5));
        add(SpellButton);
        add(Box.createVerticalGlue());
        add(SettingsButton);
        add(Box.createVerticalStrut(5));
        add(SaveButton);
        add(Box.createVerticalStrut(5));
        add(SaveExitButton);
        add(Box.createVerticalStrut(5));
        add(ExitButton);

        setAlwaysOnTop(true);

        ApplySettings();
    }

}
