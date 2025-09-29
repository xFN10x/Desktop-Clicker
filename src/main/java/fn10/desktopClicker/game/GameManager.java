package fn10.desktopClicker.game;

import java.awt.Window;

import fn10.desktopClicker.ui.GameBar;

public class GameManager {

    public static SavedGame CurrentGame;

    public static void LoadGame(SavedGame game) {
        CurrentGame = game;
        for (Window windows : Window.getWindows()) {
            windows.setVisible(false);
        }
        
        new GameBar().setVisible(true);
    }

}
