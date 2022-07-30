package card;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

/**
 * Card class to represent the cards to be matched as buttons. EDIT THIS CLASS TO FIT!
 *
 * @author Sarah Li and Jennifer Kim
 * @version 1.0
 * @since 1.0
 */
public class Card extends JButton {
    private ImageIcon imageIcon;
    private Color color;
    private boolean addedToBoard;
    private boolean clicked;
    private boolean ninjaMode;

    /* Constructor board size */
    public Card() {
        addedToBoard = false;
        ninjaMode = false;
        imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/1.png")));

        setPrimaryCard();

        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        setBorder(BorderFactory.createCompoundBorder(raisedBevel, loweredBevel));

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addActionListener((e) ->
        {
            setIcon(null);
            setBackground(color);
            setEnabled(false);
            clicked = true;
        });
    }

    public void setPrimaryCard() {
        setIcon(imageIcon);
        setDisabledIcon(imageIcon);

        if (!ninjaMode)
            setBackground(Color.WHITE);

        clicked = false;
        setEnabled(true);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isAddedToBoard() {
        return addedToBoard;
    }

    public void setAddedToBoard(boolean addedToBoard) {
        this.addedToBoard = addedToBoard;
    }

    public void setNinjaMode() {
        ninjaMode = !ninjaMode;
    }
}
