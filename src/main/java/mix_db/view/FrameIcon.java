package mix_db.view;

import javax.swing.JFrame;

import mix_db.core.GeneralSettings;

import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.Graphics;
import java.awt.Image;

/**
 * representation of an application frame icon that wraps an underlying image.
 */
public class FrameIcon extends Image{

    final Image icon;

    /**
     * constructs a new frame icon and sets it as the icon image for the specified frame.
     *
     * @param frame the frame to set the icon for
     */
    public FrameIcon(JFrame frame) {
        this.icon = Toolkit.getDefaultToolkit().getImage(GeneralSettings.iconsPath + "applicationIconRedim.png");   

        frame.setIconImage(icon);
    }

    /**
     * gets the width of the icon.
     *
     * @param observer the image observer waiting for the image to be loaded
     * @return the width of the icon, or -1 if the width is not yet known
     */
    @Override
    public int getWidth(ImageObserver observer) {
        return this.icon.getWidth(observer);
    }

    /**
     * gets the height of the icon.
     *
     * @param observer the image observer waiting for the image to be loaded
     * @return the height of the icon, or -1 if the height is not yet known
     */
    @Override
    public int getHeight(ImageObserver observer) {
        return this.icon.getHeight(observer);
    }

    /**
     * gets the object that produces the pixels for the image.
     *
     * @return the image producer that produces the pixels for this image
     */
    @Override
    public ImageProducer getSource() {
        return this.icon.getSource();
    }

    /**
     * gets a graphics context for drawing to this image.
     *
     * @return a graphics context to draw to this image
     */
    @Override
    public Graphics getGraphics() {
        return this.getGraphics();
    }

    /**
     * gets a property of this image by name.
     *
     * @param name the name of the property
     * @param observer the image observer waiting for the image to be loaded
     * @return the value of the named property
     */
    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return this.getProperty(name, observer);
    }
}