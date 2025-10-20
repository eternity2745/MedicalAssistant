package screens;

import java.awt.*;
import javax.swing.*;
import database.DoctorDAO;
import app.MainApp;


// Class For Login Page
public class Login extends JPanel {

    private MainApp parent;

    public Login(MainApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setOpaque(false);

        // ----- Login Card Panel -----
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(true);
        loginPanel.setBackground(new Color(255, 255, 255, 230));
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        loginPanel.setPreferredSize(new Dimension(400, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel titleLabel = new JLabel("Login Screen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 0;
        loginPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 1;
        loginPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        loginPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        loginPanel.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginBtn, gbc);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 6;
        loginPanel.add(registerBtn, gbc);

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        container.add(loginPanel);

        add(container, BorderLayout.CENTER);

   loginBtn.addActionListener(e -> {
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();

    if(email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    boolean isValid = DoctorDAO.validateLogin(email, password);

    if(isValid) {
        JOptionPane.showMessageDialog(this, "Login Successful!");
        parent.showScreen("Dashboard");
    } else {
        JOptionPane.showMessageDialog(this, "Invalid credentials!");
    }
});

       
        registerBtn.addActionListener(e -> parent.showScreen("Register"));
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