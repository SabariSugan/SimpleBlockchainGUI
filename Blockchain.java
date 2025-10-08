import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Blockchain extends JFrame {
    private JTextField senderField, receiverField, amountField;
    private JButton sendButton;
    private JTextArea transactionArea;
    private ArrayList<String> transactions;

    public Blockchain() {
        setTitle("Blockchain Project");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font areaFont = new Font("Consolas", Font.PLAIN, 13);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        inputPanel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Sender:"), gbc);
        senderField = new JTextField(20);
        senderField.setFont(fieldFont);
        gbc.gridx = 1;
        inputPanel.add(senderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Receiver:"), gbc);
        receiverField = new JTextField(20);
        receiverField.setFont(fieldFont);
        gbc.gridx = 1;
        inputPanel.add(receiverField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Amount:"), gbc);
        amountField = new JTextField(20);
        amountField.setFont(fieldFont);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(60, 179, 113));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(sendButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        transactionArea.setLineWrap(true);
        transactionArea.setWrapStyleWord(true);
        transactionArea.setFont(areaFont);
        transactionArea.setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(transactionArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        add(scrollPane, BorderLayout.CENTER);

        transactions = new ArrayList<>();

        sendButton.addActionListener(e -> sendTransaction());
    }

    private void sendTransaction() {
        String sender = senderField.getText().trim();
        String receiver = receiverField.getText().trim();
        String amount = amountField.getText().trim();

        if (sender.isEmpty() || receiver.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String transaction = "Sender: " + sender + " | Receiver: " + receiver + " | Amount: " + amount;
        String hash = generateHash(transaction);
        String fullTransaction = transaction + " | Hash: " + hash;

        transactions.add(fullTransaction);
        updateTransactionArea();

        senderField.setText("");
        receiverField.setText("");
        amountField.setText("");
    }

    private void updateTransactionArea() {
        transactionArea.setText("");
        for (String t : transactions) {
            transactionArea.append(t + "\n\n");
        }
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Blockchain simulator = new Blockchain();
            simulator.setVisible(true);
        });
    }
}
