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

import fn10.desktopClicker.game.upgrades.CoinAutoCollectUpgrade;
import fn10.desktopClicker.game.upgrades.CoinMiningUpgrade;
import fn10.desktopClicker.game.upgrades.CoinSpeedUpgrade;
import fn10.desktopClicker.game.upgrades.CoinsPerClickUpgrade;
import fn10.desktopClicker.game.upgrades.IUpgrade;
import fn10.desktopClicker.game.upgrades.ManaSpeedUpgrade;
import fn10.desktopClicker.ui.CoinWindow;
import fn10.desktopClicker.ui.GameMenu;
import fn10.desktopClicker.util.SaveManager;
import fn10.desktopClicker.util.Various;

public class GameManager {

	public static SavedGame CurrentGame;
	private static SavedGame LoadedGame;
	private static Thread RunningThread = null;

	public static boolean Paused = false;

	public static final IUpgrade[] upgrades = new IUpgrade[] {
			new CoinsPerClickUpgrade(),
			new CoinSpeedUpgrade(),
			new CoinMiningUpgrade(),
			new CoinAutoCollectUpgrade(),
			new ManaSpeedUpgrade(),
	};

	private static long NextCoinSpawn = 0;
	private static long MiningTimer = 0;
	private static TimerTask OnGameTick = new TimerTask() {

		@Override
		public void run() {
			if (Paused)
				return;

			if (CurrentGame.CoinMiningLevel >= 0 && CurrentGame.CoinMiningInterval >= 0) {
				if (MiningTimer <= 0) {
					MiningTimer = CurrentGame.CoinMiningInterval;
					Various.playRandomMiningSound();
					CurrentGame.Coins += CurrentGame.CoinsPerClick;
				} else {
					MiningTimer--;
				}
			}

			if (NextCoinSpawn <= 0) {
				NextCoinSpawn = Random.from(RandomGenerator.getDefault()).nextLong(
						Math.max(CurrentGame.CoinMaxTime - 2000, 200),
						Math.max(CurrentGame.CoinMaxTime, 500));
				System.out.println("Coin!");
				CoinWindow.spawnNew();
			}
			NextCoinSpawn--;
			if (CurrentGame.Mana < CurrentGame.MaxMana) {
				System.out.println("mana: " + CurrentGame.Mana);
				CurrentGame.Mana += CurrentGame.ManaRecharge;
				if (CurrentGame.Mana >= CurrentGame.MaxMana) {
					CurrentGame.Mana = CurrentGame.MaxMana;
				}
			}
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
		Paused = true;
		if (LoadedGame == null) {
			System.out.println("No game is loaded!");
			return;
		}

		try {
			CurrentGame.LastPlayed = Instant.now();

			SaveManager settings = SaveManager.load();

			settings.removeGame(CurrentGame.GameName);
			settings.games.add(CurrentGame);
			settings.save();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Paused = false;
	}

	public static void LoadGame(SavedGame game) {
		Paused = true;
		CurrentGame = game;
		LoadedGame = game;

		for (Window windows : Window.getWindows()) {
			windows.setVisible(false);
		}

		new GameMenu().setVisible(true);

		for (IUpgrade upgrade : upgrades) {
			upgrade.setLevel(game);
			upgrade.isMaxLevel(game);
		}

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
