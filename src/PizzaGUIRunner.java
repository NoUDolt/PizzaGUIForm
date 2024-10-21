public class PizzaGUIRunner {
    public static void main(String[] args) {
        // Schedule the GUI to be created on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame(); // Create an instance of your PizzaGUIFrame
            frame.setVisible(true);                    // Make the frame visible
        });
    }
}