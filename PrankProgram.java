import javax.swing.JOptionPane;

public class PrankProgram {
    public static void main(String[] args) {
        boolean keepPranking = true;

        while (keepPranking) {
            String input = JOptionPane.showInputDialog("Hantai Baka Oni-chan!!\n\nDo you admit you're a pervert? (yes/no)");

            if (input == null || input.equalsIgnoreCase("no")) {
                keepPranking = true;
            } else if (input.equalsIgnoreCase("yes")) {
                keepPranking = false;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        JOptionPane.showMessageDialog(null, "Prank successful! You're a baaaka !!");
    }
}
