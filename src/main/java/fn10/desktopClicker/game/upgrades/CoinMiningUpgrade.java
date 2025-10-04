package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class CoinMiningUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/CoinMining.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Coin Mining";
    }

    @Override
    public String getUpgradeDescription() {
        return "Get coins automaticly";
    }

    @Override
    public int getLevel(SavedGame game) {
        return game.CoinMiningLevel;
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        if (level <= 1) {
            game.CoinMiningInterval = -1;
        } else
            game.CoinMiningInterval = 6000 - (150 * (level - 1)); // get the max level by doing 6000/150

        game.CoinMiningLevel = level;
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel(game.CoinMiningLevel + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 50 + (50 * game.CoinMiningLevel);
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (game.CoinMiningLevel >= 40) {
            game.CoinMiningLevel = 40;
            return true;
        } else
            return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel(game.CoinMiningLevel, game);
    }
}
