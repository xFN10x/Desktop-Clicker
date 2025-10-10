package fn10.desktopClicker.game.spells;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ICharageableSpell;

public class SummonCoinSpell implements ICharageableSpell {

    @Override
    public ImageIcon getSpellImage() {
        return new ImageIcon(getClass().getResource("/spells/SummonCoins.png"));
    }

    @Override
    public String getSpellName() {
        return "Summon Coins";
    }

    @Override
    public String getSpellDescription() {
        return "Earn a spontaneous amount of coins, based on mana spent, and how many coins you get per-click";
    }

    @Override
    public void cast(SavedGame game, float manaSpent) {
        game.Coins += manaSpent * 15;
        System.out.println("Basicly spawned " + ((manaSpent * 15) / game.CoinsPerClick) + " coins");
    }

    @Override
    public float getManaRequirment(SavedGame game, float timeHeld) {
        return (game.CoinsPerClick * timeHeld) * 5;
    }

    @Override
    public float maxChargeTime() {
        return 15f;
    }

}
