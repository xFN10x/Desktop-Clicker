package fn10.desktopClicker.game;

import java.awt.Window;
import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.random.RandomGenerator;

import fn10.desktopClicker.ui.CoinWindow;
import fn10.desktopClicker.ui.GameBar;
import fn10.desktopClicker.util.SettingsManager;

public class GameManager {

    public static SavedGame CurrentGame;
    private static SavedGame LoadedGame;
    private static Thread RunningThread = null;

    private static long NextCoinSpawn = 0;
    private static TimerTask OnGameTick = new TimerTask() {

        @Override
        public void run() {
            if (NextCoinSpawn <= 0) {
                NextCoinSpawn = Random.from(RandomGenerator.getDefault()).nextLong(500, 2000);
                System.out.println("Coin!");
            CoinWindow.spawnNew();
            }
            NextCoinSpawn--;
            
        }

    };

    public static void Run() {
        RunningThread = new Thread(() -> {
            new Timer().scheduleAtFixedRate(OnGameTick, 1, 1);
        });
        RunningThread.start();
    }

    public static void SaveCurrentGame() {
        if (LoadedGame == null) {
            System.out.println("No game is loaded!");
            return;
        }

        try {
            CurrentGame.LastPlayed = Instant.now();

            SettingsManager settings = SettingsManager.load();

            settings.removeGame(CurrentGame.GameName);
            settings.games.add(CurrentGame);
            settings.save();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadGame(SavedGame game) {
        CurrentGame = game;
        LoadedGame = game;

        for (Window windows : Window.getWindows()) {
            windows.setVisible(false);
        }

        new GameBar().setVisible(true);
        Run();
    }

}
