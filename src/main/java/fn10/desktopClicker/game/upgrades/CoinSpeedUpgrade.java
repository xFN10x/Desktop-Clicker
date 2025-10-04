package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class CoinSpeedUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/CoinSpawning.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Coin Speed";
    }

    @Override
    public String getUpgradeDescription() {
        return "Upgrades how fast coins spawn in";
    }

    @Override
    public int getLevel(SavedGame game) {
        return game.CoinSpeedLevel;
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.CoinMaxTime = 5000 - (100 * (level - 1));//get the max level by doing 5000/100
        game.CoinSpeedLevel = level;
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel(game.CoinSpeedLevel + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 50 * game.CoinSpeedLevel;
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (game.CoinSpeedLevel >= 50) {
            game.CoinSpeedLevel = 50;
            return true;
        } else
        return false;
    }

}
