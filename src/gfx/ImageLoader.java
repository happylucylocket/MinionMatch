package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Takes image from textures path in resources and turns it into a BufferedImage
 *
 * @author Sarah Li
 * @version 1.0
 * @since 1.0
 */

public class ImageLoader {
    /**
     * Reads an image at a path and returns it as an BufferedImage. Exits if exception is thrown.
     *
     * @param path Location of image to be loaded
     * @return BufferedImage of the image
     */
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
