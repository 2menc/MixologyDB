package mix_db.view;

import javax.swing.JFrame;

import mix_db.core.GeneralSettings;

import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.Graphics;
import java.awt.Image;

public class FrameIcon extends Image{

    final Image icon;

    public FrameIcon(JFrame frame) {
        this.icon = Toolkit.getDefaultToolkit().getImage(GeneralSettings.iconsPath + "applicationIconRedim.png");   

        frame.setIconImage(icon);
    }

    @Override
    public int getWidth(ImageObserver observer) {
        return this.icon.getWidth(observer);
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return this.icon.getHeight(observer);
    }

    @Override
    public ImageProducer getSource() {
        return this.icon.getSource();
    }

    @Override
    public Graphics getGraphics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphics'");
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProperty'");
    }
}
