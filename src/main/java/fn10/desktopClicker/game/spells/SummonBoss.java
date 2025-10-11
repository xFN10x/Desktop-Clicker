package fn10.desktopClicker.game.spells;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.ui.FlatLineBorder;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.game.spells.interfaces.ISpellWithActiveText;
import fn10.desktopClicker.ui.GameMenu;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.Various;

public class SummonBoss implements ISpell, ISpellWithActiveText {

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
            JLabel dialouge = new JLabel("You lay down 6 coins in a star pattern...", SwingConstants.CENTER);

            JPanel choices = new JPanel();
            choices.setLayout(new BoxLayout(choices, BoxLayout.X_AXIS));
            choices.setOpaque(false);
            // choices.setBackground(new Color(0, 0, 0,0f));

            choices.add(Box.createHorizontalGlue());
            choices.add(beginButton);
            choices.add(Box.createHorizontalStrut(10));
            choices.add(goBackButton);
            choices.add(Box.createHorizontalGlue());

            win.add(img);
            win.add(dialouge);

            dialouge.add(choices);

            win.setVisible(true);
            win.setAlwaysOnTop(true);

            dialouge.setForeground(Color.white);
            dialouge.setBackground(Color.darkGray);
            dialouge.setBorder(new FlatLineBorder(new Insets(5, 5, 5, 5), Color.white, 5, 16));

            SpringLayout lay = new SpringLayout();
            SpringLayout lay2 = new SpringLayout();

            win.setLayout(lay);
            dialouge.setLayout(lay2);

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

            lay.putConstraint(SpringLayout.SOUTH, dialouge, -30, SpringLayout.SOUTH,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.EAST, dialouge, -30, SpringLayout.EAST,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.WEST, dialouge, 30, SpringLayout.WEST,
                    win.getContentPane());
            lay.putConstraint(SpringLayout.NORTH, dialouge, 20, SpringLayout.SOUTH,
                    img);

            lay2.putConstraint(SpringLayout.SOUTH, choices, -10, SpringLayout.SOUTH,
                    dialouge);
            lay2.putConstraint(SpringLayout.EAST, choices, -10, SpringLayout.EAST,
                    dialouge);
            lay2.putConstraint(SpringLayout.WEST, choices, 10, SpringLayout.WEST,
                    dialouge);
            lay2.putConstraint(SpringLayout.NORTH, choices, 100, SpringLayout.NORTH,
                    dialouge);

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
                GameManager.CurrentGame.SummonMasterCooldown = 3600000;
                win.setAlwaysOnTop(false);
                Window newWin = new TransparentWindow(new Dimension((int) size.getWidth(), (int) size.getHeight()),
                        true);

                SwingUtilities.invokeLater(() -> {
                    choices.removeAll();
                    dialouge.setText("");
                });

                newWin.setAlwaysOnTop(true);
                newWin.setVisible(true);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {

                    private float alpha = 0f;
                    private float alphaDown = 1f;

                    private int holdCountDown = 5000;

                    private int omgCountdown = 5000;

                    private ImageIcon coinMasterIcon = new ImageIcon(getClass().getResource("/coinMaster.png"));

                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> {
                            System.out.println(alpha);
                            if (alpha >= 1) {
                                holdCountDown--;
                                if (holdCountDown <= 0) {

                                    if (alphaDown <= 0) {
                                        if (omgCountdown > 0) {
                                            omgCountdown--;
                                        } else {
                                            dialouge.setText("Why thou summon me?");
                                            newWin.setAlwaysOnTop(false);
                                            win.setAlwaysOnTop(true);
                                            cancel();

                                            JButton wishButton = new JButton("I require your power");
                                            JButton noReasonButton = new JButton("Err... no reason; bye!");

                                            wishButton.addActionListener(ac -> {
                                                choices.removeAll();
                                                dialouge.setText("Alright, what does thou wish?");

                                                JButton manyCoinsButton = new JButton("Many coins,");
                                                JButton breakMyLimits = new JButton("Break my limits!");
                                                JButton toOvertake = new JButton("To overtake you!");

                                                toOvertake.addActionListener(act -> {
                                                    choices.removeAll();
                                                    JButton goback = new JButton("Go back");
                                                    dialouge.setText(
                                                            "I'm afraid I cannot do that yet. I have urgent matters, and I must leave");

                                                    goback.addActionListener(acti -> {
                                                        choices.removeAll();
                                                        GameManager.Paused = false;
                                                        GameMenu.CurrentMenu.setVisible(true);
                                                        win.dispose();
                                                        for (Window windows : Window.getWindows()) {
                                                            if (windows.getBackground().equals(Color.BLUE)) {
                                                                windows.setAlwaysOnTop(true);
                                                            }
                                                        }
                                                    });

                                                    choices.add(Box.createHorizontalGlue());
                                                    choices.add(goback);
                                                    choices.add(Box.createHorizontalGlue());
                                                });

                                                manyCoinsButton.addActionListener(act -> {
                                                    choices.removeAll();
                                                    JButton goback = new JButton("Go back");
                                                    dialouge.setText(
                                                            "As you wish. I have given you "
                                                                    + (GameManager.CurrentGame.CoinsPerClick * 50)
                                                                    + " coins.");
                                                    GameManager.CurrentGame.Coins *= 50;
                                                    goback.addActionListener(acti -> {
                                                        choices.removeAll();
                                                        GameManager.Paused = false;
                                                        GameMenu.CurrentMenu.setVisible(true);
                                                        win.dispose();
                                                        for (Window windows : Window.getWindows()) {
                                                            if (windows.getBackground().equals(Color.BLUE)) {
                                                                windows.setAlwaysOnTop(true);
                                                            }
                                                        }
                                                    });

                                                    choices.add(Box.createHorizontalGlue());
                                                    choices.add(goback);
                                                    choices.add(Box.createHorizontalGlue());
                                                });

                                                breakMyLimits.addActionListener(act -> {
                                                    choices.removeAll();
                                                    JButton goback = new JButton("Go back");
                                                    dialouge.setText(
                                                            "As you wish, now you can upgrade past normal.");

                                                    GameManager.CurrentGame.BrokenLimits = true;

                                                    goback.addActionListener(acti -> {
                                                        choices.removeAll();
                                                        GameManager.Paused = false;
                                                        GameMenu.CurrentMenu.setVisible(true);
                                                        win.dispose();
                                                        for (Window windows : Window.getWindows()) {
                                                            if (windows.getBackground().equals(Color.BLUE)) {
                                                                windows.setAlwaysOnTop(true);
                                                            }
                                                        }
                                                    });

                                                    choices.add(Box.createHorizontalGlue());
                                                    choices.add(goback);
                                                    choices.add(Box.createHorizontalGlue());
                                                });

                                                choices.add(Box.createHorizontalGlue());
                                                choices.add(manyCoinsButton);
                                                choices.add(Box.createHorizontalStrut(10));
                                                choices.add(breakMyLimits);
                                                choices.add(Box.createHorizontalStrut(10));
                                                choices.add(toOvertake);
                                                choices.add(Box.createHorizontalGlue());
                                            });

                                            noReasonButton.addActionListener(ac -> {
                                                choices.removeAll();
                                                GameManager.Paused = false;
                                                GameMenu.CurrentMenu.setVisible(true);
                                                win.dispose();
                                                for (Window windows : Window.getWindows()) {
                                                    if (windows.getBackground().equals(Color.BLUE)) {
                                                        windows.setAlwaysOnTop(true);
                                                    }
                                                }
                                            });

                                            choices.add(Box.createHorizontalGlue());
                                            choices.add(wishButton);
                                            choices.add(Box.createHorizontalStrut(10));
                                            choices.add(noReasonButton);
                                            choices.add(Box.createHorizontalGlue());
                                        }

                                    } else {
                                        if (!img.getIcon().equals(coinMasterIcon)) {
                                            img.setIcon(coinMasterIcon);
                                        }
                                        newWin.setBackground(new Color(1f, 1f, 1f, alphaDown));
                                        alphaDown -= 0.00166666f;
                                    }
                                }
                            } else {

                                newWin.setBackground(new Color(1f, 1f, 1f, alpha));
                                alpha += 0.00166666f;
                            }
                        });
                    }

                }, 1, 1);
                Various.playSound(getClass().getResource("/undertaleSound.wav"));
            });

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
        return game.SummonMasterCooldown <= 0;
    }

    @Override
    public String getString() {
        SavedGame game = GameManager.CurrentGame;
        if (game.SummonMasterCooldown > 0) {
            return "You can cast this again in " + (game.SummonMasterCooldown / 1000) + " seconds";
        } else
            return "";
    }
}
