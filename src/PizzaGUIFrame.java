import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;
    private ButtonGroup crustGroup;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Select Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Select Size"));
        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Select Toppings"));
        String[] toppingNames = {"Pepperoni", "Mushrooms", "Onions", "Sausage", "Bacon", "Extra Cheese"};
        toppings = new JCheckBox[toppingNames.length];
        for (int i = 0; i < toppingNames.length; i++) {
            toppings[i] = new JCheckBox(toppingNames[i]);
            toppingsPanel.add(toppings[i]);
        }

        // Order display panel
        JPanel orderDisplayPanel = new JPanel();
        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderDisplayPanel.add(scrollPane);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add ActionListeners to buttons
        orderButton.addActionListener(new OrderListener());
        clearButton.addActionListener(new ClearListener());
        quitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Add panels to frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST);
        add(orderDisplayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Listener for "Order" button
    private class OrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Variables to store order information
            String crustType = "";
            String size = (String) sizeComboBox.getSelectedItem();
            double basePrice = 0.0;
            double toppingsPrice = 0.0;
            double subtotal = 0.0;
            double tax = 0.0;
            double total = 0.0;
            StringBuilder orderReceipt = new StringBuilder();

            // Determine the selected crust type
            if (thinCrust.isSelected()) {
                crustType = "Thin Crust";
            } else if (regularCrust.isSelected()) {
                crustType = "Regular Crust";
            } else if (deepDishCrust.isSelected()) {
                crustType = "Deep-Dish Crust";
            }

            // Ensure the user selected a crust and size
            if (crustType.isEmpty() || size.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a crust type and pizza size.");
                return;
            }

            // Determine the base price based on the size
            switch (size) {
                case "Small ($8)":
                    basePrice = 8.00;
                    break;
                case "Medium ($12)":
                    basePrice = 12.00;
                    break;
                case "Large ($16)":
                    basePrice = 16.00;
                    break;
                case "Super ($20)":
                    basePrice = 20.00;
                    break;
            }

            // Collect toppings selected
            orderReceipt.append("=========================================\n");
            orderReceipt.append(String.format("%s, %s\t$%.2f\n", crustType, size, basePrice));
            orderReceipt.append("Ingredients:\n");

            for (JCheckBox topping : toppings) {
                if (topping.isSelected()) {
                    orderReceipt.append(String.format("%-20s\t$1.00\n", topping.getText()));
                    toppingsPrice += 1.00;
                }
            }

            // Ensure at least one topping is selected
            if (toppingsPrice == 0) {
                JOptionPane.showMessageDialog(null, "Please select at least one topping.");
                return;
            }

            // Calculate subtotal, tax, and total
            subtotal = basePrice + toppingsPrice;
            tax = subtotal * 0.07;
            total = subtotal + tax;

            // Format the final order summary
            orderReceipt.append("=========================================\n");
            orderReceipt.append(String.format("Sub-total:\t\t$%.2f\n", subtotal));
            orderReceipt.append(String.format("Tax:\t\t\t$%.2f\n", tax));
            orderReceipt.append("----------------------------------------------------\n");
            orderReceipt.append(String.format("Total:\t\t\t$%.2f\n", total));
            orderReceipt.append("=========================================\n");

            // Display the order in the JTextArea
            orderTextArea.setText(orderReceipt.toString());
        }
    }

    // Listener for "Clear" button
    private class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            crustGroup.clearSelection(); // Clears the selected crust
            sizeComboBox.setSelectedIndex(0); // Resets size to the first option
            for (JCheckBox topping : toppings) {
                topping.setSelected(false); // Unchecks all toppings
            }
            orderTextArea.setText(""); // Clears the order receipt text area
        }
    }
}