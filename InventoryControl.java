import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryControl {
    private JFrame frame;
    private JTextField itemNameField;
    private JTextField itemQuantityField;
    private JTextArea inventoryDisplay;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InventoryControl window = new InventoryControl();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public InventoryControl() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblItemName = new JLabel("Item Name:");
        lblItemName.setBounds(10, 11, 86, 14);
        frame.getContentPane().add(lblItemName);

        itemNameField = new JTextField();
        itemNameField.setBounds(106, 8, 86, 20);
        frame.getContentPane().add(itemNameField);
        itemNameField.setColumns(10);

        JLabel lblItemQuantity = new JLabel("Item Quantity:");
        lblItemQuantity.setBounds(10, 36, 86, 14);
        frame.getContentPane().add(lblItemQuantity);

        itemQuantityField = new JTextField();
        itemQuantityField.setBounds(106, 33, 86, 20);
        frame.getContentPane().add(itemQuantityField);
        itemQuantityField.setColumns(10);

        JButton btnAddItem = new JButton("Add Item");
        btnAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                String itemQuantity = itemQuantityField.getText();
                inventoryDisplay.append(itemName + ": " + itemQuantity + "\n");
            }
        });
        btnAddItem.setBounds(10, 61, 89, 23);
        frame.getContentPane().add(btnAddItem);

        inventoryDisplay = new JTextArea();
        inventoryDisplay.setBounds(10, 95, 414, 155);
        frame.getContentPane().add(inventoryDisplay);
    }
}
