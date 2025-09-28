package screens;

import app.MainApp;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WelcomeScreen extends JPanel {

    private Image backgroundImage;
    private MainApp parent;


    public WelcomeScreen(MainApp parent) {
        this.parent = parent;
        // Load a background image (replace with your own path or resource)
        backgroundImage = new ImageIcon("D:\\Java\\MedicalAssistant\\Resources\\Images\\doctorProfile.jpg").getImage();

        setLayout(new BorderLayout());

        // ----- Overlay Panel (content) -----
        JPanel overlay = new JPanel();
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setOpaque(false);
        overlay.setAlignmentY(Component.CENTER_ALIGNMENT);
        overlay.setBorder(new EmptyBorder(300, 50, 50, 50));

        // ----- Logo / Title -----
        JLabel appTitle = new JLabel("MediGuide");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 48));
        appTitle.setForeground(Color.WHITE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Smart Healthcare Dashboard with AI");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        tagline.setForeground(new Color(220, 220, 220));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlay.add(appTitle);
        overlay.add(Box.createVerticalStrut(15));
        overlay.add(tagline);
        overlay.add(Box.createVerticalStrut(50));

        // ----- Buttons -----
        JButton startBtn = createModernButton("Get Started");
        JButton learnBtn = createModernButton("Learn More");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(startBtn);
        btnPanel.add(learnBtn);

        overlay.add(btnPanel);
        overlay.add(Box.createVerticalGlue());

        // ----- Footer -----
        JLabel footer = new JLabel("© 2025 MediGuide| v1.0");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlay.add(footer);

        add(overlay, BorderLayout.CENTER);

        // ----- Button Actions -----
        startBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Navigate to Login Page here.");
            parent.showScreen("Login");
        });

        learnBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "MediGuide helps doctors manage patients, analyze reports with AI,\n"
                            + "and streamline hospital workflows.");
        });
    }

    // Custom paint for background with dim overlay
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            // Draw scaled background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            // Dark overlay
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 150)); // semi-transparent black
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    // Helper: modern gradient button
    private JButton createModernButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(58, 123, 213),
                        getWidth(), getHeight(),
                        new Color(0, 210, 255));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                super.paintComponent(g2);
                g2.dispose();
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.YELLOW);
            }

            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }
}
