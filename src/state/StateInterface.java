package state;

import java.awt.*;

/**
 * Represents each state in the matching game
 *
 * @author Sarah Li and Jennifer Kim
 * @version 1.0
 * @since 1.0
 */
public interface StateInterface {
    Graphics g = null;

    /**
     * Draws the state to the display
     * @param g Graphics object
     */
    void render(Graphics g);

    /**
     * Does what should be done every tick in this state
     */
    void tick();
}