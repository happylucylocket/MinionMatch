package menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * MAYBE NEED, MAYBE NOT?
 * @author Sarah Li and Jennifer Kim
 * @version 1.0
 * @since 1.0
 */
public class MenuComponent extends JComponent {
    private JPanel mainPanel;
    private Image image;
    private final String BACKGROUND_IMAGE = "/4.PNG";

    public MenuComponent() {
        setLayout(new BorderLayout());

        image = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();

        mainPanel = new JPanel(new GridLayout(5, 1, 20, 40));
        mainPanel.setBorder(new EmptyBorder(40, 180, 80, 180));

        addTitle();
        addStartButton();
        addExitButton();

        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setOpaque(false);
    }

    public void addTitle() {
        MenuLabel gameName = new MenuLabel("Memory Game", new Font(Font.MONOSPACED, Font.BOLD, 102), Color.WHITE);
        mainPanel.add(gameName);
    }

    public void addStartButton() {
        MenuButton newGame = new MenuButton("New Game", new Color(138, 200, 114), 44, null);

        newGame.addActionListener(event ->
        {
        });

        mainPanel.add(newGame);
    }
    public void addExitButton() {
        MenuButton exit = new MenuButton("Exit", new Color(200, 100, 100), 44, null);

        exit.addActionListener((event) -> System.exit(0));

        mainPanel.add(exit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null)
            return;

        g.drawImage(image, 0, 0, null);
    }
}
