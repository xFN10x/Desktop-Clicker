package fn10.desktopClicker.game.spells;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.game.spells.interfaces.ISpellWithActiveText;

public class DoubleProfitSpell implements ISpell, ISpellWithActiveText {

    private int timer = 0;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cast'");
    }

    @Override
    public float getManaRequirment(SavedGame game) {
        return Math.min(((game.CoinsPerClick + 9) / 10) * 5f, 300f);
    }

    @Override
    public boolean canCast(SavedGame game) {
        return timer <= 0;
    }

    @Override
    public String getString() {
        if (timer <= 0) {
            return "";
        } else
        return "Cast again in: " + (timer / 1000) + " seconds";
    }

}
