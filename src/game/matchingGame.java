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
import java.util.*;
import static controller.cardController.getFlippedCards;

public class matchingGame {
    private JFrame mainFrame;
    private JPanel panel;
    private JPanel textPanel;
    private Container mainContentPane;
    private ImageIcon images[];
    private Socket socket;
    private PrintWriter out;
    // local
    private static final String SERVER_IP = "127.0.0.1";
    // have to change to the server host ip
//    private static final String SERVER_IP = "142.58.217.96";
    private static final int SERVER_PORT = 8080;
    private Listener listener;
    private static JTextArea text;
    private static ArrayList<Card> cardArray = new ArrayList<>();
    private cardController controller;

    public matchingGame() throws IOException {
        this.mainFrame = new JFrame ("Minion Match");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(430, 678);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));
        this.mainFrame.setIconImage(new ImageIcon(getClass().getResource("/icon.jpg")).getImage()); // application icon
        this.panel = new JPanel(new GridLayout(6, 6));

        // make new JPanel for text
        this.textPanel = new JPanel();
        this.text = new JTextArea();
        this.text.setPreferredSize(new Dimension(430, 45));
        this.text.setFont(new Font("Comic Sans MS", 1, 12));
        this.text.setText("  NEW TEXT");


        // load the card images
        this.images = loadCardImages();

        // connect to the server
        try {
            this.socket = new Socket(SERVER_IP, SERVER_PORT);
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e){
            System.out.println("error");
        }
        this.listener = new Listener(socket);
        listener.start();

        controller = new cardController(this.out);
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

        // all cards have same back side
        ImageIcon backIcon = this.images[18];
        ImageIcon clearIcon = this.images[19];

        int cardsToAdd[] = new int[36]; // 6x6 grid
        for (int i = 0; i < 18; i++) {
            cardsToAdd[2*i] = i;
            cardsToAdd[2*i+1] = i;
        }

        // Randomize the cards
//        randomizeCardArray(cardsToAdd);

        // Make card objects
        for (int i = 0; i < cardsToAdd.length; i++) {
            int num = cardsToAdd[i];
            Card newCard = new Card(controller, this.images[num], backIcon, clearIcon, num, i);
            panel.add(newCard);
            cardArray.add(newCard);
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

    // thread to listen to server
    private static class Listener extends Thread {
        BufferedReader input;
        public Listener(Socket socket) throws IOException {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            try {
                // reads initial message of client id
                String clientNumber = input.readLine();
                System.out.println(clientNumber);
                text.setText("  " + clientNumber);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                while (true) {
                    // reading server message
                    // 2 types of messages, fields separated by "-":
                    // END GAME, card value matched, client id
                    String serverResponse = input.readLine();
                    String[] serverResponseArray = serverResponse.split("-");
                    // END GAME case
                    if(serverResponseArray[0].equalsIgnoreCase("END GAME")) {
                        System.out.println(serverResponseArray[1]);
                        text.setText("   " + text.getText() + "     " + serverResponseArray[1]);
                        text.setLineWrap(true);
                        text.setWrapStyleWord(true);
                    }
                    // card matched case
                    else {
                        Integer matchedValue = Integer.parseInt(serverResponseArray[0]);
                        System.out.println(serverResponseArray[1]);

                        // hiding the cards that have been matched by another client
                        for (Card card : cardArray) {
                            if (card.getValue() == matchedValue) {
                                card.clearCard();
                            }
                        }
                        // removing the matched cards from the currently selected cards
                        Vector<Card> flippedCards = getFlippedCards();
                        if (getFlippedCards().size() == 1) {
                            Card card1 = (Card) getFlippedCards().get(0);
                            if (card1 != null && card1.getValue() == matchedValue) {
                                flippedCards.remove(card1);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

