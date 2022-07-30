package menu;

import javax.swing.*;
import java.awt.*;

/**
 * MAYBE NEED, MAYBE NOT?
 * @author Sarah Li and Jennifer Kim
 * @version 1.0
 * @since 1.0
 */
public class MenuLabel extends JLabel {
    public MenuLabel(String text, Font font, Color fontColor) {
        setText(text);
        setFont(font);
        setForeground(fontColor);

        setHorizontalAlignment(JLabel.CENTER);
    }
}
