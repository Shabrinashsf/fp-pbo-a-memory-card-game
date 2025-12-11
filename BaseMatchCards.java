import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public abstract class BaseMatchCards {

    class Card {
        String cardName;
        ImageIcon cardImageIcon;

        Card(String cardName, ImageIcon cardImageIcon) {
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon;
        }
    }

    String[] cardList = {
        "darkness", "double", "fairy", "fighting", "fire",
        "grass", "lightning", "metal", "psychic", "water"
    };

    int rows, columns;
    int cardWidth = 90, cardHeight = 128;

    ArrayList<Card> cardSet;
    ImageIcon cardBackImageIcon;

    int boardWidth, boardHeight;

    JFrame frame = new JFrame("Match Cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartPanel = new JPanel();
    JButton restartButton = new JButton();

    int errorCount = 0;
    ArrayList<JButton> board;
    Timer hideCardTimer;
    boolean gameReady = false;
    JButton card1Selected = null;
    JButton card2Selected = null;

    public BaseMatchCards(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        boardWidth = columns * cardWidth;
        boardHeight = rows * cardHeight;

        setupCards();
        shuffleCards();
        initUI();
        startTimer();
    }

    void initUI() {
        frame.setLayout(new BorderLayout());
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: " + errorCount);
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(rows, columns));

        for (int i = 0; i < cardSet.size(); i++) {
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
            tile.setIcon(cardSet.get(i).cardImageIcon);
            tile.setFocusable(false);

            tile.addActionListener(e -> handleCardClick(tile));

            board.add(tile);
            boardPanel.add(tile);
        }

        frame.add(boardPanel);

        restartButton.setText("Restart");
        restartButton.addActionListener(e -> restartGame());
        restartPanel.add(restartButton);
        frame.add(restartPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    void handleCardClick(JButton tile) {
        if (!gameReady) return;

        if (tile.getIcon() == cardBackImageIcon) {

            if (card1Selected == null) {
                card1Selected = tile;
                int idx = board.indexOf(tile);
                tile.setIcon(cardSet.get(idx).cardImageIcon);

            } else if (card2Selected == null) {
                card2Selected = tile;
                int idx = board.indexOf(tile);
                tile.setIcon(cardSet.get(idx).cardImageIcon);

                if (card1Selected.getIcon() != card2Selected.getIcon()) {
                    errorCount++;
                    textLabel.setText("Errors: " + errorCount);
                    hideCardTimer.start();

                } else {
                    card1Selected = null;
                    card2Selected = null;
                }
            }
        }
    }

    void restartGame() {
        gameReady = false;
        card1Selected = null;
        card2Selected = null;

        shuffleCards();
        for (int i = 0; i < board.size(); i++) {
            board.get(i).setIcon(cardSet.get(i).cardImageIcon);
        }

        errorCount = 0;
        textLabel.setText("Errors: " + errorCount);
        hideCardTimer.start();
    }


    void setupCards() {
        cardSet = new ArrayList<>();

        int totalCards = rows * columns;       // jumlah kartu
        int uniqueCards = totalCards / 2;      // jumlah pasangan

        for (int i = 0; i < uniqueCards; i++) {
            String cardName = cardList[i];
            Image img = new ImageIcon(getClass().getResource("./img/" + cardName + ".jpg")).getImage();
            ImageIcon icon = new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            cardSet.add(new Card(cardName, icon));
        }

        // duplikat pair
        cardSet.addAll(new ArrayList<>(cardSet));

        // load back card
        Image back = new ImageIcon(getClass().getResource("./img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(back.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
    }


    void shuffleCards() {
        for (int i = 0; i < cardSet.size(); i++) {
            int j = (int) (Math.random() * cardSet.size());
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
    }

    void startTimer() {
        hideCardTimer = new Timer(5000, e -> hideCards());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();
    }

    void hideCards() {
        if (gameReady && card1Selected != null && card2Selected != null) {
            card1Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected.setIcon(cardBackImageIcon);
            card2Selected = null;
        } else {
            for (JButton button : board) {
                button.setIcon(cardBackImageIcon);
            }
            gameReady = true;
        }
    }
}
