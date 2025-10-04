package fn10.desktopClicker.enums;

import java.awt.Rectangle;

public enum MenuPosition {
    TopLeft(0, 0),
    TopRight(1f, 0),
    DownLeft(0, 1f),
    DownRight(1f, 1f),
    TopCenter(0.5f, 0),
    BottomCenter(0.5f, 1f),
    Center(0.5f, 0.5f);

    public final float x;
    public final float y;

    private MenuPosition(float xAmount, float yAmount) {
        this.x = xAmount;
        this.y = yAmount;
    }

    public int getXAxisLocationFromWidth(int width) {
        return (int) (width * (x));
    }
    
    public int getYAxisLocationFromHeight(int height) {
        return (int) (height * (y));
    }
}
