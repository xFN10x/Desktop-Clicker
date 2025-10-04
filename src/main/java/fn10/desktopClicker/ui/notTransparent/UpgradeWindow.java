package fn10.desktopClicker.ui.notTransparent;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.upgrades.CoinAutoCollectUpgrade;
import fn10.desktopClicker.game.upgrades.CoinMiningUpgrade;
import fn10.desktopClicker.game.upgrades.CoinSpeedUpgrade;
import fn10.desktopClicker.game.upgrades.CoinsPerClickUpgrade;
import fn10.desktopClicker.game.upgrades.IUpgrade;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.Various;

public class UpgradeWindow extends JDialog {

    private final JPanel InnerScroll = new JPanel();
    private final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private final SpringLayout Lay = new SpringLayout();
    private final BoxLayout InnerLay = new BoxLayout(InnerScroll, BoxLayout.X_AXIS);

    private final IUpgrade[] upgrades = new IUpgrade[] {
            new CoinsPerClickUpgrade(),
            new CoinSpeedUpgrade(),
            new CoinMiningUpgrade(),
            new CoinAutoCollectUpgrade(),
    };

    public static void showUpgrades() {
        new UpgradeWindow().setVisible(true);
    }

    private UpgradeWindow() {
        super(null, "Upgrades", ModalityType.APPLICATION_MODAL);

        GameManager.Paused = true;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameManager.Paused = false;
            }
        });

        setSize(1200, 705);
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setAlwaysOnTop(true);
        setLayout(Lay);

        ScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLocation(ImageUtilites.getScreenCenter(this));

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.setLayout(InnerLay);

        for (IUpgrade iUpgrade : upgrades) {
            try {
                SpringLayout layout = new SpringLayout();
                JLabel text = new JLabel(
                        "<html> <b>" + iUpgrade.getUpgradeName() + "</b> <br><br> " + iUpgrade.getUpgradeDescription()
                                + " <br><br><br><br>Level: " + iUpgrade.getLevel(GameManager.CurrentGame) + "</html>");
                text.setForeground(Color.white);
                JLabel upgradePanel = new JLabel(iUpgrade.getUpgradeImage());
                JButton upgradeButton = new JButton();

                if (!iUpgrade.isMaxLevel(GameManager.CurrentGame)) {
                    upgradeButton
                            .setText("Upgrade (" + iUpgrade.getCoinRequirment(GameManager.CurrentGame) + " coins)");
                } else {
                    upgradeButton.setEnabled(false);
                    upgradeButton
                            .setText("Max Level");
                }

                upgradeButton.addActionListener(ac -> {
                    try {
                        SavedGame game = GameManager.CurrentGame;
                        if (game.Coins >= iUpgrade.getCoinRequirment(game)) {
                            game.Coins -= iUpgrade.getCoinRequirment(game);
                            Various.playSound(getClass().getResource("/upgrade.wav"));
                            iUpgrade.Upgrade(GameManager.CurrentGame);
                            if (!iUpgrade.isMaxLevel(game)) {
                                upgradeButton
                                        .setText("Upgrade (" + iUpgrade.getCoinRequirment(game) + " coins)");
                            } else {
                                upgradeButton.setEnabled(false);
                                upgradeButton
                                        .setText("Max Level");
                            }
                            text.setText(
                                    "<html> <b>" + iUpgrade.getUpgradeName() + "</b> <br><br> "
                                            + iUpgrade.getUpgradeDescription()
                                            + " <br><br><br><br>Level: " + iUpgrade.getLevel(game)
                                            + "</html>");
                        } else {
                            Various.playSound(getClass().getResource("/cannotBuy.wav"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                upgradePanel.setLayout(layout);

                layout.putConstraint(SpringLayout.EAST, upgradeButton, -5, SpringLayout.EAST, upgradePanel);
                layout.putConstraint(SpringLayout.WEST, upgradeButton, 5, SpringLayout.WEST, upgradePanel);
                layout.putConstraint(SpringLayout.SOUTH, upgradeButton, -5, SpringLayout.SOUTH, upgradePanel);

                layout.putConstraint(SpringLayout.EAST, text, -5, SpringLayout.EAST, upgradePanel);
                layout.putConstraint(SpringLayout.WEST, text, 5, SpringLayout.WEST, upgradePanel);
                layout.putConstraint(SpringLayout.SOUTH, text, -5, SpringLayout.NORTH, upgradeButton);

                upgradePanel.add(upgradeButton);
                upgradePanel.add(text);

                InnerScroll.add(upgradePanel);
                InnerScroll.add(Box.createHorizontalStrut(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        add(ScrollPane);
    }

}
