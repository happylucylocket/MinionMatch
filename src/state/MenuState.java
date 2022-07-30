package state;

import java.awt.*;

public class MenuState implements StateInterface {
    private static MenuState instance;
    public static MenuState instance() {
        if (instance == null) instance = new MenuState();
        return instance;
    }
    @Override
    public void render(Graphics g) {
    }

    @Override
    public void tick() {
        // Check for player clicking Start

    }
}
