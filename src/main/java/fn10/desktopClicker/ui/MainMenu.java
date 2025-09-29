package fn10.desktopClicker.ui;

import java.awt.Dimension;
import java.time.Instant;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.SettingsManager;

public class MainMenu extends TransparentWindow {

    public final JButton NewGameButton = new JButton("New Game");
    public final JButton LoadGameButton = new JButton("Load Game");
    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

    public MainMenu() {
        super(new Dimension(200, 300));

        ExitButton.addActionListener(ac -> {
            System.exit(0);
        });

        NewGameButton.addActionListener(ac -> {
            try {
                String option = JOptionPane.showInputDialog(this,
                        "Lets start clicking some coins! What do you want to name this game?", "New Game",
                        JOptionPane.QUESTION_MESSAGE);

                SavedGame newGame = new SavedGame(option, Instant.now());

                SettingsManager settings = SettingsManager.load();
                settings.games.add(newGame);
                settings.save();

                GameManager.LoadGame(newGame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        LoadGameButton.addActionListener(ac -> {
            setVisible(false);
            new SavedGameSelector().setVisible(true);
        });

        setAlwaysOnTop(true);

        setLayout(Lay);

        NewGameButton.setAlignmentX(0.5f);
        LoadGameButton.setAlignmentX(0.5f);
        ExitButton.setAlignmentX(0.5f);

        add(Box.createVerticalGlue());
        add(NewGameButton);
        add(Box.createVerticalStrut(10));
        add(LoadGameButton);
        add(Box.createVerticalStrut(10));
        add(ExitButton);
        add(Box.createVerticalGlue());

    }

}
