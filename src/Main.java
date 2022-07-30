import game.Game;

public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
