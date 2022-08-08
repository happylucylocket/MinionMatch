package card;

import controller.cardController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Card extends JLabel implements MouseListener {
    private final cardController controller;
    public Icon faceIcon;
    Icon backIcon;
    Icon clearIcon;
    int value;
    int iconWidthHalf, iconHeightHalf;
    boolean mousePressedOnMe = false;
    boolean faceUp = false;
    boolean clear = false;

    public Card(cardController controller, Icon faceIcon, Icon backIcon, Icon clearIcon, int value) {
        super(backIcon);
        this.faceIcon = faceIcon;
        this.backIcon = backIcon;
        this.clearIcon = clearIcon;
        this.value = value;
        this.addMouseListener(this);
        this.iconHeightHalf = backIcon.getIconHeight() / 2;
        this.iconWidthHalf = faceIcon.getIconWidth() / 2;
        this.controller = controller;
    }

    public int getValue() { return value; }

    private boolean overIcon(int x, int y) {
        int distX = Math.abs(x-this.getWidth()/2);
        int distY = Math.abs(y-this.getHeight()/2);
        // check if the click was outside a card or not
        if(distX > this.iconHeightHalf || distY > this.iconWidthHalf) {
            return false;
        }
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent clickEvent) {
        if(overIcon(clickEvent.getX(), clickEvent.getY())) {
            this.turnUp();
        }
    }

    public void turnUp() {
        if(this.faceUp) {
            return;
        }
        this.faceUp = true;
        this.faceUp = this.controller.flipUp(this);
        if (!this.clear && this.faceUp) {
            this.setIcon(this.faceIcon);
        }
    }

    public void turnDown() {
        if(!this.faceUp) {
            return;
        }
        this.setIcon(this.backIcon);
        this.faceUp = false;
    }

    public void clearCard() {
        this.clear = true;
        this.setIcon(this.clearIcon);
    }

    @Override
    public void mousePressed(MouseEvent clickEvent) {
        if(overIcon(clickEvent.getX(), clickEvent.getY())) {
            this.mousePressedOnMe = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent clickEvent) {
        if(this.mousePressedOnMe) {
            this.mousePressedOnMe = false;
            this.mouseClicked(clickEvent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent clickEvent) {
    }

    @Override
    public void mouseExited(MouseEvent clickEvent) {
    }
}