package fn10.desktopClicker.game;

import java.time.Instant;

public class SavedGame {

    public final Instant Creation;
    public Instant LastPlayed;

    public String GameName;

    public int Coins;

    public SavedGame(String Name, Instant made) {
        this.Creation = made;
        this.GameName = Name;
    }
}
