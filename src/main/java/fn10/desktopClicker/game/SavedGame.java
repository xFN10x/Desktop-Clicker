package fn10.desktopClicker.game;

import java.awt.Point;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SavedGame {

    public final Instant Creation;
    public Instant LastPlayed;

    public String GameName;

    public int Coins = 0;
    public int CoinsPerClick = 1;

    public Map<Point, Integer> CurrentCoins = new HashMap<Point, Integer>();

    public SavedGame(String Name, Instant made) {
        this.Creation = made;
        this.GameName = Name;
        LastPlayed = Creation;
    }
}
