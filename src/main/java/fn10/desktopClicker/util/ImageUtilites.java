package fn10.desktopClicker.util;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.*;

/**
 * Taken from PSPTools, which i took from bedrockR
 */
public class ImageUtilites {

    public static ImageIcon ResizeIcon(ImageIcon OG, int width, int height, int scalingMode) {
        return new ImageIcon(OG.getImage().getScaledInstance(width, height, scalingMode));
    }

    public static ImageIcon ResizeIcon(ImageIcon OG, int width, int height) {
        return new ImageIcon(OG.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static Image ResizeImage(BufferedImage OG, int width, int height) {
        return OG.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static ImageIcon ResizeImageByURL(URL OG, int width, int height, int scalingMode) {
        return new ImageIcon(new ImageIcon(OG).getImage().getScaledInstance(width, height, scalingMode));
    }

    public static ImageIcon ResizeImageByURL(URL OG, int width, int height) {
        var image = new ImageIcon(OG);

        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static Point getScreenCenter(Component target) {
        var size = Toolkit.getDefaultToolkit().getScreenSize();
        // Launcher.LOG.info(new Point(((int)(size.getWidth() * 0.5)), ((int)
        // (size.getHeight() * 0.5))).toString());
        return new Point(((int) ((size.getWidth() - target.getWidth()) * 0.5)),
                ((int) ((size.getHeight() - target.getHeight()) * 0.5)));
    }

    // credit: https://stackoverflow.com/a/7603815, and github copilot
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle as the clipping mask
        g2.setClip(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // Draw the image only where the mask is set
        g2.drawImage(image, 0, 0, null);

        g2.dispose();
        return output;
    }

    // credit to next 4 funcs: https://stackoverflow.com/a/38191404
    /**
     * getScreenInsets, This returns the insets of the screen, which are defined by
     * any task bars
     * that have been set up by the user. This function accounts for multi-monitor
     * setups. If a
     * window is supplied, then the the monitor that contains the window will be
     * used. If a window
     * is not supplied, then the primary monitor will be used.
     */
    static public Insets getScreenInsets(Window windowOrNull) {
        Insets insets;
        if (windowOrNull == null) {
            insets = Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration());
        } else {
            insets = windowOrNull.getToolkit().getScreenInsets(
                    windowOrNull.getGraphicsConfiguration());
        }
        return insets;
    }

    /**
     * getScreenWorkingArea, This returns the working area of the screen. (The
     * working area excludes
     * any task bars.) This function accounts for multi-monitor setups. If a window
     * is supplied,
     * then the the monitor that contains the window will be used. If a window is
     * not supplied, then
     * the primary monitor will be used.
     */
    static public Rectangle getScreenWorkingArea(Window windowOrNull) {
        Insets insets;
        Rectangle bounds;
        if (windowOrNull == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice()
                    .getDefaultConfiguration());
            bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        } else {
            GraphicsConfiguration gc = windowOrNull.getGraphicsConfiguration();
            insets = windowOrNull.getToolkit().getScreenInsets(gc);
            bounds = gc.getBounds();
        }
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= (insets.left + insets.right);
        bounds.height -= (insets.top + insets.bottom);
        return bounds;
    }

    /**
     * getScreenTotalArea, This returns the total area of the screen. (The total
     * area includes any
     * task bars.) This function accounts for multi-monitor setups. If a window is
     * supplied, then
     * the the monitor that contains the window will be used. If a window is not
     * supplied, then the
     * primary monitor will be used.
     */
    static public Rectangle getScreenTotalArea(Window windowOrNull) {
        Rectangle bounds;
        if (windowOrNull == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        } else {
            GraphicsConfiguration gc = windowOrNull.getGraphicsConfiguration();
            bounds = gc.getBounds();
        }
        return bounds;
    }
}
