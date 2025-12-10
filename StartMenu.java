import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu {

    int rows = 4;
    int columns = 5;
    int cardWidth = 90;
    int cardHeight = 128;

    int boardWidth = columns * cardWidth;  
    int boardHeight = rows * cardHeight;

    JFrame frame = new JFrame("Select Level");

    JButton level1 = new JButton("Level 1");
    JButton level2 = new JButton("Level 2");
    JButton level3 = new JButton("Level 3");

    StartMenu() {

        frame.setSize(boardWidth, boardHeight);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel yang berada di tengah
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        setupButton(level1);
        setupButton(level2);
        setupButton(level3);

        // biar tombolnya ada spasi dan tetap di tengah
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(level1);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(level2);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(level3);
        centerPanel.add(Box.createVerticalGlue());

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void setupButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MatchCards();
            }
        });
    }
}
