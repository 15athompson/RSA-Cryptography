import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI {
    private final UserManager userManager;
    private final JTextArea outputArea;

    public UserGUI(UserManager userManager) {
        this.userManager = userManager;
        this.outputArea = new JTextArea(10, 20);
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("RSA Encryption System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel userPanel = createUserPanel();
        JPanel messagePanel = createMessagePanel();

        frame.setLayout(new GridLayout(2, 1));
        frame.add(userPanel);
        frame.add(messagePanel);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();

        JButton createUserButton = new JButton("Create User");
        JButton listUsersButton = new JButton("List Users");
        JButton deleteUserButton = new JButton("Delete User");

        JTextField userIdField = new JTextField(20);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                userManager.createUser(userId);
                outputArea.append("User created successfully: " + userId + "\n");
            }
        });

        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManager.listUsers();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                userManager.deleteUser(userId);
                outputArea.append("User deleted successfully: " + userId + "\n");
            }
        });

        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("User ID: "));
        panel.add(userIdField);
        panel.add(createUserButton);
        panel.add(listUsersButton);
        panel.add(deleteUserButton);

        return panel;
    }

    private JPanel createMessagePanel() {
        JPanel panel = new JPanel();

        JButton loginButton = new JButton("Login");
        JButton encryptButton = new JButton("Encrypt Message");
        JButton decryptButton = new JButton("Decrypt Message");

        JTextField userIdField = new JTextField(20);
        JTextField messageField = new JTextField(20);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                userManager.loginUser(userId);
                outputArea.append("User logged in successfully: " + userId + "\n");
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipientUserId = JOptionPane.showInputDialog(null, "Enter recipient user ID:");
                String message = messageField.getText();

                if (userManager.userExists(recipientUserId)) {
                    KeyPair recipientKeyPair = userManager.getKeyPair(recipientUserId);
                    String encryptedMessage = RSAKeyGenerator.encryptMessage(message, recipientKeyPair.getPublicKey());
                    outputArea.append("Encrypted Message: " + encryptedMessage + "\n");
                } else {
                    outputArea.append("Recipient user not found: " + recipientUserId + "\n");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedMessage = JOptionPane.showInputDialog(null, "Enter encrypted message:");
                KeyPair keyPair = userManager.loginUser(userIdField.getText());

                if (keyPair != null) {
                    String decryptedMessage = userManager.decryptMessage(encryptedMessage, keyPair.getPrivateKey());
                    outputArea.append("Decrypted Message: " + decryptedMessage + "\n");
                }
            }
        });

        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("User ID: "));
        panel.add(userIdField);
        panel.add(loginButton);
        panel.add(new JLabel("Message: "));
        panel.add(messageField);
        panel.add(encryptButton);
        panel.add(decryptButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        return panel;
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        new UserGUI(userManager);
    }
}
