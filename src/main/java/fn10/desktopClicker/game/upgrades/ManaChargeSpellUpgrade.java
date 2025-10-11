package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class ManaChargeSpellUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/ManaSpeed.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Spell Charging Speed";
    }

    @Override
    public String getUpgradeDescription() {
        return "Increases the speed that you charge spells with";
    }

    @Override
    public int getLevel(SavedGame game) {
        return game.ManaChargeMuliplerLevel;
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.ManaChargeMulipler = 1 + (0.05f * (level - 1));
        game.ManaChargeMuliplerLevel = level;
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel((int) getLevel(game) + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 100 + (125 * getLevel(game));
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (getLevel(game) >= 40 + (game.BrokenLimits ? 20 : 0)) {
            setLevel(40 + (game.BrokenLimits ? 20 : 0), game);;
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel((int) getLevel(game), game);
    }

}
