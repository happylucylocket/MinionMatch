package gfx;

import java.awt.image.BufferedImage;

/**
 * Stores all the loaded buffered images needed
 *
 * @author Sarah Li and Jennifer Kim
 * @version 1.0
 * @since 1.0
 */
public class Assets {
    public static BufferedImage test;

    /**
     * Loads all necessary images and stores them as a variable for usage in program
     */
    public static void init() {
        test = ImageLoader.loadImage("/SizeTest.png");

    }
}
