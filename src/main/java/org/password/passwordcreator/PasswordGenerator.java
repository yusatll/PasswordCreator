package org.password.passwordcreator;
/*
 * This code is open-source and can be distributed under the MIT License.
 * Simple UI for generating passwords from given words, optional numbers, and length.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PasswordGenerator extends JFrame {

    private JTextField wordsField;
    private JTextField numbersField;
    private JTextField lengthField;
    private JLabel[] passwordLabels;
    private JButton[] copyButtons;

    public PasswordGenerator() {
        super("Password Generator");

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Enter words (space separated):"));
        wordsField = new JTextField();
        inputPanel.add(wordsField);

        inputPanel.add(new JLabel("Enter optional numbers (comma separated):"));
        numbersField = new JTextField();
        inputPanel.add(numbersField);

        inputPanel.add(new JLabel("Desired password length (integer):"));
        lengthField = new JTextField("12");
        inputPanel.add(lengthField);

        JButton generateButton = new JButton("Generate Passwords");
        inputPanel.add(generateButton);

        JButton clearButton = new JButton("Clear");
        inputPanel.add(clearButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        passwordLabels = new JLabel[3];
        copyButtons = new JButton[3];

        for (int i = 0; i < 3; i++) {
            passwordLabels[i] = new JLabel("");
            passwordLabels[i].setFont(new Font("Monospaced", Font.BOLD, 14));
            copyButtons[i] = new JButton("Copy");
            final int idx = i;
            copyButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    copyToClipboard(passwordLabels[idx].getText());
                }
            });
            outputPanel.add(passwordLabels[i]);
            outputPanel.add(copyButtons[i]);
        }

        mainPanel.add(outputPanel, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePasswords();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordsField.setText("");
                numbersField.setText("");
                lengthField.setText("12");
                for (int i = 0; i < 3; i++) {
                    passwordLabels[i].setText("");
                }
            }
        });

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void generatePasswords() {
        String wordsInput = wordsField.getText().trim();
        String numbersInput = numbersField.getText().trim();
        String lengthInput = lengthField.getText().trim();

        if (wordsInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least one word.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int desiredLength;
        try {
            desiredLength = Integer.parseInt(lengthInput);
            if (desiredLength <= 0) {
                JOptionPane.showMessageDialog(this, "Length must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Length must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] words = wordsInput.split("\\s+");
        List<String> numbers = PasswordUtil.parseNumbers(numbersInput);

        // Generate 3 password samples
        List<String> passwords = PasswordUtil.generatePasswords(java.util.Arrays.asList(words), numbers, 3, desiredLength);

        for (int i = 0; i < 3; i++) {
            passwordLabels[i].setText(passwords.get(i));
        }
    }

    private void copyToClipboard(String text) {
        if (text == null || text.isEmpty()) return;
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        JOptionPane.showMessageDialog(this, "Copied to clipboard!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Set a nice look and feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordGenerator().setVisible(true);
            }
        });
    }
}
