package fn10.desktopClicker.game.spells;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.ui.GameMenu;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.Various;

public class SummonBoss implements ISpell {

    @Override
    public ImageIcon getSpellImage() {
        return new ImageIcon(getClass().getResource("/spells/SummonGod.png"));
    }

    @Override
    public String getSpellName() {
        return "Summon... something";
    }

    @Override
    public String getSpellDescription() {
        return "I wonder what happens when you do this...";
    }

    @Override
    public void cast(SavedGame game) {
        GameManager.Paused = true;
        for (Window windows : Window.getWindows()) {
            if (windows.isAlwaysOnTop()) {
                windows.setBackground(Color.BLUE);
            }
            windows.setAlwaysOnTop(false);
        }
        int option = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to do this? You should pause/finish anything that you are doing.", "Cast Spell?",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            game.Mana -= getManaRequirment(game);
            for (Window win : Window.getWindows()) {
                win.setVisible(false);
            }
            GameManager.CurrentCoinWindows.forEach(win -> {
                SwingUtilities.invokeLater(() -> {
                    win.click();
                });
            });

            GameMenu.CurrentMenu.setVisible(false);

            Rectangle size = ImageUtilites.getScreenTotalArea(null);
            TransparentWindow win = new TransparentWindow(new Dimension((int) size.getWidth(), (int) size.getHeight()),
                    true);

            JButton beginButton = new JButton("Continue...");
            JButton goBackButton = new JButton("Go back");
            JLabel img = new JLabel(new ImageIcon(getClass().getResource("/summoningCircle.png")));
            win.add(img);
            win.setVisible(true);
            win.setAlwaysOnTop(true);

            SpringLayout lay = new SpringLayout();

            win.setLayout(lay);

            lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, img, 0, SpringLayout.HORIZONTAL_CENTER,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.VERTICAL_CENTER, img, 0, SpringLayout.VERTICAL_CENTER, win.getContentPane());

            lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, goBackButton, 0, SpringLayout.HORIZONTAL_CENTER,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.VERTICAL_CENTER, goBackButton, 30, SpringLayout.VERTICAL_CENTER,
                    win.getContentPane());

            lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, beginButton, 0, SpringLayout.HORIZONTAL_CENTER,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.VERTICAL_CENTER, beginButton, -30, SpringLayout.VERTICAL_CENTER,
                    win.getContentPane());

            goBackButton.addActionListener(ac -> {
                GameManager.Paused = false;
                GameMenu.CurrentMenu.setVisible(true);
                win.dispose();
                for (Window windows : Window.getWindows()) {
                    if (windows.getBackground().equals(Color.BLUE)) {
                        windows.setAlwaysOnTop(true);
                    }
                }
            });

            beginButton.addActionListener(ac -> {
                win.setAlwaysOnTop(false);
                Window newWin = new TransparentWindow(new Dimension((int) size.getWidth(), (int) size.getHeight()),
                        true);

                win.remove(beginButton);
                win.remove(goBackButton);

                newWin.setAlwaysOnTop(true);
                newWin.setVisible(true);

                Various.playSound(getClass().getResource("/undertaleSound.wav"));
                new Timer().scheduleAtFixedRate(new TimerTask() {

                    private float alpha = 0f;
                    private float alphaDown = 1f;

                    private int holdCountDown = 20000;

                    @Override
                    public void run() {
                        if (alpha >= 1) {
                            holdCountDown--;
                            if (holdCountDown <= 0) {
                                if (alphaDown == 1f) {
                                    img.setIcon(new ImageIcon(getClass().getResource("/coinMaster.png")));  
                                }

                                newWin.setBackground(new Color(1f, 1f, 1f, alphaDown));
                                alphaDown -= 0.00166666f;

                                if (alphaDown <= 0) {
                                    newWin.setAlwaysOnTop(false);
                                    win.setAlwaysOnTop(true);
                                    cancel();
                                    return;
                                }
                            }
                        } else {
                            newWin.setBackground(new Color(1f, 1f, 1f, alpha));
                            alpha += 0.00166666f;
                        }
                    }

                }, 1, 1);
            });

            win.add(beginButton);
            win.add(goBackButton);

            win.setBackground(new Color(0, 0, 0, 0.2f));

        } else {
            GameManager.Paused = false;
        }

        for (Window windows : Window.getWindows()) {
            if (windows.getBackground().equals(Color.BLUE)) {
                windows.setAlwaysOnTop(true);
            }
        }
    }

    @Override
    public float getManaRequirment(SavedGame game) {
        return 300f;
    }

    @Override
    public boolean canCast(SavedGame game) {
        return true;
    }
}
