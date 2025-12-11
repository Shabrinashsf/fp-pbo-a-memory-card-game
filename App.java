import javax.swing.JOptionPane;


public class App {
    public static void main(String[] args) {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(null, "Choose difficulty",
                "Match Cards", 0, 3, null, options, options[0]);

        if (choice == 0) new MatchCardsLevel1();
        else if (choice == 1) new MatchCardsLevel2();
        else if (choice == 2) new MatchCardsLevel3();
    }
}
