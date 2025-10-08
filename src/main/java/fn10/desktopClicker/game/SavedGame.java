package fn10.desktopClicker.game;

import java.awt.Point;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SavedGame {

    public final Instant Creation;
    public Instant LastPlayed;

    public String GameName;

    public long Coins = 0;
    public float Mana = 5;
    public float MaxMana = 5;

    public int CoinsPerClick = 1;

    public int CoinMaxTime = 5000;

    public int CoinMiningInterval = -1;
    public int CoinMiningLevel = 1;

    public int CoinAutoCollectChance = 0;

    /**
     * im to lazy to figure out the reverse conversion for it, so just store it here
     */
    public int CoinSpeedLevel = 1;

    public Map<Point, Integer> CurrentCoins = new HashMap<Point, Integer>();

    public SavedGame(String Name, Instant made) {
        this.Creation = made;
        this.GameName = Name;
        LastPlayed = Creation;
    }
}
