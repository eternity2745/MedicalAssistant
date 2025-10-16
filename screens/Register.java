package screens;

import java.awt.*;
import javax.swing.*;

import src.main.app.MainApp;

// Class for creating register screen
public class Register extends JPanel {

    private MainApp parent;

    public Register(MainApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setOpaque(true);
        registerPanel.setBackground(new Color(255, 255, 255, 230));
        registerPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        registerPanel.setPreferredSize(new Dimension(450, 550)); // taller for more fields

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel titleLabel = new JLabel("Register Screen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 0;
        registerPanel.add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        registerPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 2;
        registerPanel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 3;
        registerPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 4;
        registerPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 5;
        registerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 6;
        registerPanel.add(passwordField, gbc);

        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 7;
        registerPanel.add(confirmLabel, gbc);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 8;
        registerPanel.add(confirmField, gbc);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(registerBtn, gbc);

        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 10;
        registerPanel.add(backBtn, gbc);

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        container.add(registerPanel);

        add(container, BorderLayout.CENTER);

        registerBtn.addActionListener(e -> {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirm = new String(confirmField.getPassword()).trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if(!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Registered successfully! You can login now.");
        parent.showScreen("Login");
    });

        backBtn.addActionListener(e -> parent.showScreen("Login"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(10, 25, 70),
                                             0, getHeight(), new Color(30, 50, 100));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
