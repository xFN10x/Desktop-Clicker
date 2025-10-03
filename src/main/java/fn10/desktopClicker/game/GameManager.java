package fn10.desktopClicker.game;

import java.awt.Point;
import java.awt.Window;
import java.time.Instant;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.random.RandomGenerator;

import fn10.desktopClicker.ui.CoinWindow;
import fn10.desktopClicker.ui.GameBar;
import fn10.desktopClicker.util.SettingsManager;
import fn10.desktopClicker.util.Various;

public class GameManager {

    public static SavedGame CurrentGame;
    private static SavedGame LoadedGame;
    private static Thread RunningThread = null;

    public static boolean Paused = false;

    private static long NextCoinSpawn = 0;
    private static long MiningTimer = 0;
    private static TimerTask OnGameTick = new TimerTask() {

        @Override
        public void run() {
            if (CurrentGame.CoinMiningLevel >= 0 && CurrentGame.CoinMiningInterval >= 0) {
                if (MiningTimer <= 0) {
                    MiningTimer = CurrentGame.CoinMiningInterval;
                    Various.playRandomMiningSound();
                    CurrentGame.Coins += CurrentGame.CoinsPerClick;
                } else {
                    MiningTimer--;
                }
            }
            System.out.println(MiningTimer);

            if (NextCoinSpawn <= 0) {
                NextCoinSpawn = Random.from(RandomGenerator.getDefault()).nextLong(
                        Math.max(CurrentGame.CoinMaxTime - 2000, 200),
                        Math.max(CurrentGame.CoinMaxTime, 500));
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

    public static void UpdateCoins(Point location, long life) {
        if (CurrentGame.CurrentCoins == null) {
            System.out.println("Was null");
            CurrentGame.CurrentCoins = new HashMap<Point, Integer>();
        }

        if (life >= 0)
            CurrentGame.CurrentCoins.put(location, Long.valueOf(life).intValue());
        else if (life < 0) {
            {
                System.out.println("Removed coin at " + location + "\n " + CurrentGame.CurrentCoins.remove(location));

            }
        }
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
        Paused = true;
        CurrentGame = game;
        LoadedGame = game;

        for (Window windows : Window.getWindows()) {
            windows.setVisible(false);
        }

        new GameBar().setVisible(true);

        if (CurrentGame.CurrentCoins != null)
            for (Entry<Point, Integer> entry : CurrentGame.CurrentCoins.entrySet()) {
                Point key = entry.getKey();
                System.out.println(
                        "Loading coin at x=" + key.getX() + " y=" + key.getY() + " at life time: " + entry.getValue());
                CoinWindow.spawnNew(key, entry.getValue());
            }

        Run();
        Paused = false;
    }

}
