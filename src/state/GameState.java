package state;

import gfx.Assets;

import java.awt.*;

/**
 * Represents the matching game once players click start (WILL HAVE TO ADD OTHER STATES)
 * @author Sarah Li and Jennifer Kim
 */
public class GameState implements StateInterface {
    private static GameState instance;
    public static GameState instance() {
        if (instance == null)
            instance = new GameState();
        return instance;
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.test, 10, 10, null);
        g.drawImage(Assets.test, 10, 110, null);
        g.drawImage(Assets.test, 10, 210, null);
        g.drawImage(Assets.test, 10, 310, null);
        g.drawImage(Assets.test, 10, 410, null);
        g.drawImage(Assets.test, 10, 510, null);

        g.drawImage(Assets.test, 90, 510, null);
        g.drawImage(Assets.test, 170, 510, null);
        g.drawImage(Assets.test, 250, 510, null);
        g.drawImage(Assets.test, 330, 510, null);
        g.drawImage(Assets.test, 410, 510, null);
    }

    /**
     * Moves character and checks for character's interactions with game elements
     */
    @Override
    public void tick() {
        return;
    }

}
