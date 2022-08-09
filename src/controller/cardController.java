package controller;

import card.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class cardController implements ActionListener {

    private Vector flippedCards;
    private Timer flipDownTimer;
    private final int flipDownDelay = 500; // milliseconds
    private PrintWriter out;
    public cardController(PrintWriter object) {
        this.out=object;
        this.flippedCards = new Vector<Card>(2);
        this.flipDownTimer = new Timer(this.flipDownDelay, this);
        this.flipDownTimer.setRepeats(false);
    }

    public boolean flipUp(Card card) {
//        System.out.println("FlipUp");
        if (this.flippedCards.size() < 2) {
            return doAddCard(card);
        }
        return false;
    }

    private boolean doAddCard(Card card) {
//        System.out.println("doAddCard");
        this.flippedCards.add(card);
        if (this.flippedCards.size() == 2) {
            Card otherCard = (Card)this.flippedCards.get(0);
            if (otherCard.getValue() == card.getValue()) {
                // Sending the message to the server
                int matchedValue = card.getValue();
                out.println(matchedValue);

                card.clearCard();
                otherCard.clearCard();

                // Clear the flippedCards array
                this.flippedCards.clear();

            } else {
                this.flipDownTimer.start();
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("action ");
        for (int i = 0; i < this.flippedCards.size(); i++) {
            Card card = (Card)this.flippedCards.get(i);
            card.turnDown();
        }
        this.flippedCards.clear();
    }
}
