package fn10.desktopClicker.game.spells;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class SummonCoins implements ICharageableSpell {

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
        return "Earn a spontaneous amount of coins, based on mana.";
    }

    @Override
    public void cast(SavedGame game, float manaSpent) {
        game.Coins += manaSpent * 2;
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
