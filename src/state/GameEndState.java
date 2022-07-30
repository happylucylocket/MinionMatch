package state;

import java.awt.*;

public class GameEndState implements StateInterface {
    private static GameEndState instance;
    public static GameEndState instance() {
        if (instance == null) instance = new GameEndState();
        return instance;
    }
    @Override
    public void render(Graphics g) {

    }

    @Override
    public void tick() {

    }
}
