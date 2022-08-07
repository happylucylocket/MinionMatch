import game.matchingGame;

public class Main {
    public static void main(String[] args) {
        try {
            matchingGame game = new matchingGame();
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
