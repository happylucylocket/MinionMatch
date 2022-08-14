package game;

import card.Card;
import controller.cardController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


/**
 * Matching Game
 * @author Dylan Feng, Sarah Li
 */
public class matchingGame {
    private JFrame mainFrame;
    private JPanel panel;
    private JPanel textPanel;
    private Container mainContentPane;
    private ImageIcon images[]; //0-17 front side of the card; 18 back side
    private Socket socket;
    private PrintWriter out;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;
    private Listener listener;
    private JLabel text;

    public matchingGame() throws IOException {
        this.mainFrame = new JFrame ("Minion Match");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(430, 678);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));
        this.mainFrame.setIconImage(new ImageIcon(getClass().getResource("/icon.jpg")).getImage()); // application icon
        this.panel = new JPanel(new GridLayout(6, 6));

        // Make new JPanel for text
        this.textPanel = new JPanel();
        this.text = new JLabel();
        this.text.setPreferredSize(new Dimension(430, 30));
        this.text.setFont(new Font("Comic Sans MS", 1, 10));
        this.text.setText("  NEW TEXT");


        //Load the cards
        this.images = loadCardImages();
        try{
            this.socket = new Socket(SERVER_IP, SERVER_PORT);

            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e){

            System.out.println("error");
        }
        listener = new Listener(socket);
        listener.start();

    }

    private ImageIcon[] loadCardImages() {
        ImageIcon images[] = new ImageIcon[20];
        for (int i = 0; i < 20; i++) {
            String fileName = "resources/" + i + ".png";
            images[i] = new ImageIcon(fileName);
        }
        return images;
    }

    public JPanel makeCards(JPanel panel) {

        // All cards have same back side
        ImageIcon backIcon = images[18];
        ImageIcon clearIcon = images[19];
        cardController controller = new cardController(out);

        int cardsToAdd[] = new int[36]; // 6x6 grid
        for (int i = 0; i < 18; i++) {
            cardsToAdd[2*i] = i;
            cardsToAdd[2*i+1] = i;
        }
        // Randomize the cards
        /*randomizeCardArray(cardsToAdd);*/

        // Make card object
        for (int i = 0; i < cardsToAdd.length; i++) {
            int num = cardsToAdd[i];
            Card newCard = new Card(controller, this.images[num], backIcon, clearIcon, num);
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
        this.mainContentPane.add(makeCards(panel));
        this.textPanel.add(text);
        this.mainFrame.add(textPanel);

        //show main in window
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null); // creates window in center of screen
        this.mainFrame.setVisible(true);
    }

    private static class Listener extends Thread {
        BufferedReader input;
        public Listener(Socket socket) throws IOException {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String serverResponse = input.readLine();
                    System.out.println(serverResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

