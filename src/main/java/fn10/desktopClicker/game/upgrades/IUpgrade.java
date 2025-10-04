package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public interface IUpgrade {

    ImageIcon getUpgradeImage();

    String getUpgradeName();

    String getUpgradeDescription();

    int getLevel(SavedGame game);

    /**
     * This code should not only change the level varible, but it should also redo
     * its calculations, and you can even use this in {@code Upgrade()} if you want
     */
    void setLevel(int level, SavedGame game);

    void Upgrade(SavedGame game);

    int getCoinRequirment(SavedGame game);

    boolean isMaxLevel(SavedGame game);

}
