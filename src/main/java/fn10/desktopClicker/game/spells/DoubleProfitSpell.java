package fn10.desktopClicker.game.spells;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.game.spells.interfaces.ISpellWithActiveText;

public class DoubleProfitSpell implements ISpell, ISpellWithActiveText {

    @Override
    public ImageIcon getSpellImage() {
        return new ImageIcon(getClass().getResource("/spells/DoubleProduction.png"));
    }

    @Override
    public String getSpellName() {
        return "Double Profit";
    }

    @Override
    public String getSpellDescription() {
        return "Double how many coins you get for a minute.";
    }

    @Override
    public void cast(SavedGame game) {
        game.CoinDoubleTimer = 60000;
        game.Mana -= getManaRequirment(game);
    }

    @Override
    public float getManaRequirment(SavedGame game) {
        return Math.min(((game.CoinsPerClick + 9) / 10) * 5f, 300f);
    }

    @Override
    public boolean canCast(SavedGame game) {
        return game.CoinDoubleTimer <= 0;
    }

    @Override
    public String getString() {
        if (GameManager.CurrentGame.CoinDoubleTimer <= 0) {
            return "";
        } else
            return "Cast again in: " + (GameManager.CurrentGame.CoinDoubleTimer / 1000) + " seconds";
    }

}
