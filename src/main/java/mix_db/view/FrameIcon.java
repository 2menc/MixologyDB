package mix_db.view;

import javax.swing.JFrame;

import mix_db.core.GeneralSettings;

import java.awt.Toolkit;
import java.awt.Image;

/**
 * representation of an application frame icon that wraps an underlying image.
 */
public class FrameIcon {

    private FrameIcon() {}

    /**
     * constructs a new frame icon and sets it as the icon image for the specified frame.
     *
     * @param frame the frame to set the icon for
     */
    public static void setIcon(JFrame frame) {
        final Image icon = Toolkit.getDefaultToolkit().getImage(GeneralSettings.iconsPath + "applicationIconRedim.png");   

        frame.setIconImage(icon);
    }

}