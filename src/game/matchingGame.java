package game;

import card.Card;
import controller.cardController;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Matching Game
 * @author Dylan Feng, Sarah Li
 */
public class matchingGame {
    private JFrame mainFrame;
    private Container mainContentPane;
    private ImageIcon images[]; //0-17 front side of the card; 18 back side

    public matchingGame() {
        this.mainFrame = new JFrame ("Minion Match");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(490, 678);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));
        this.mainFrame.setIconImage(new ImageIcon(getClass().getResource("/icon.jpg")).getImage()); // application icon

        //Load the cards
        this.images = loadCardImages();
    }

    private ImageIcon[] loadCardImages() {
        ImageIcon images[] = new ImageIcon[19];
        for (int i = 0; i < 19; i++) {
            String fileName = "resources/" + i + ".png";
            images[i] = new ImageIcon(fileName);
        }
        return images;
    }

    public JPanel makeCards() {
        JPanel panel = new JPanel(new GridLayout(6, 6));
        // All cards have same back side
        ImageIcon backIcon = this.images[18];
        cardController controller = new cardController();

        int cardsToAdd[] = new int[36]; // 6x6 grid
        for (int i = 0; i < 18; i++) {
            cardsToAdd[2*i] = i;
            cardsToAdd[2*i+1] = i;
        }
        // Randomize the cards (later)
        randomizeCardArray(cardsToAdd);

        // Make card object
        for (int i = 0; i < cardsToAdd.length; i++) {
            int num = cardsToAdd[i];
            Card newCard = new Card(controller, this.images[num], backIcon, num);
            panel.add(newCard);
        }
        return panel;
    }

    private void randomizeCardArray(int[] t) {
        Random randomizer = new Random();
        for (int i = 0; i < t.length; i++) {
            int d = randomizer.nextInt(t.length);
            //swap
            int s = t[d];
            t[d] = t[i];
            t[i] = s;
        }
    }

    public void start() {
        this.mainContentPane.removeAll();
        // make new card set visible
        this.mainContentPane.add(makeCards());
        //show main in window
        this.mainFrame.setVisible(true);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null); // creates window in center of screen
    }
}
