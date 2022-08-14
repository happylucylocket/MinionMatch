package controller;

import card.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Vector;

public class cardController implements ActionListener {
    // stores all the cards that the client has currently flipped
    private static Vector flippedCards;
    private Timer flipDownTimer;
    private final int flipDownDelay = 500; // milliseconds
    private PrintWriter out;
    public cardController(PrintWriter object) {
        this.out = object;
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
        if (getFlippedCards().size() < 2) {
            return doAddCard(card);
        }
        return false;
    }

    private boolean doAddCard(Card card) {
        getFlippedCards().add(card);
        if (getFlippedCards().size() == 2) {
            Card otherCard = (Card) getFlippedCards().get(0);
            if (otherCard.getValue() == card.getValue()) {
                // Sending the message to the server
                int matchedValue = card.getValue();
                out.println(matchedValue);

                // Clear the flippedCards array
                getFlippedCards().clear();

            } else {
                this.flipDownTimer.start();
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < getFlippedCards().size(); i++) {
            Card card = (Card) getFlippedCards().get(i);
            card.turnDown();
        }
        getFlippedCards().clear();
    }
}
