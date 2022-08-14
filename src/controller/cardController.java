package controller;

import card.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Vector;

public class cardController implements ActionListener {

    private static Vector flippedCards;
    private Timer flipDownTimer;
    private final int flipDownDelay = 500; // milliseconds
    private PrintWriter out;
    public cardController(PrintWriter object) {
        this.out=object;
        this.setFlippedCards(new Vector<Card>(2));
        this.flipDownTimer = new Timer(this.flipDownDelay, this);
        this.flipDownTimer.setRepeats(false);
    }

    public static Vector getFlippedCards() {
        return flippedCards;
    }

    public static void setFlippedCards(Vector flippedCards) {
        cardController.flippedCards = flippedCards;
    }

    public boolean flipUp(Card card) {
//        System.out.println("FlipUp");
        if (this.getFlippedCards().size() < 2) {
            return doAddCard(card);
        }
        return false;
    }

    // CLEAR CARD FUNCTION AFTER EVERY MATCH
    private boolean doAddCard(Card card) {
//        System.out.println("doAddCard");
        this.getFlippedCards().add(card);
        if (this.getFlippedCards().size() == 2) {
            Card otherCard = (Card) this.getFlippedCards().get(0);
            if (otherCard.getValue() == card.getValue()) {
                // Sending the message to the server
                int matchedValue = card.getValue();
                out.println(matchedValue);

//                card.clearCard();
//                otherCard.clearCard();

                // Clear the flippedCards array
                this.getFlippedCards().clear();

            } else {
                this.flipDownTimer.start();
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println("action ");
        for (int i = 0; i < this.getFlippedCards().size(); i++) {
            Card card = (Card) this.getFlippedCards().get(i);
            card.turnDown();
        }
        this.getFlippedCards().clear();
    }
}
