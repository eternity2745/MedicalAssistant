package screens;
import app.MainApp;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.w3c.dom.events.MouseEvent;
import utilities.CircularImagePanel;

public class WelcomePage extends JPanel {

    private Image bgImage;
    private MainApp parent;

    public WelcomePage(MainApp parent) {
        this.parent = parent;

        // Load background image
        ImageIcon imgIcon = new ImageIcon("Resources/Images/welcomePage.jpg");
        bgImage = imgIcon.getImage();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Resources\\Images\\logo.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Center the logo image using a wrapper panel with FlowLayout
        

        CircularImagePanel logoImage = new CircularImagePanel(img, 200, 200);

        // center it in the layout
        // JPanel imageWrapper = new JPanel();
    JPanel logoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    logoWrapper.setOpaque(false);
    logoWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    logoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
    logoWrapper.setMaximumSize(new Dimension(logoImage.getPreferredSize().width, logoImage.getPreferredSize().height));
    logoWrapper.setMinimumSize(new Dimension(logoImage.getPreferredSize().width, logoImage.getPreferredSize().height));
    logoWrapper.setPreferredSize(new Dimension(logoImage.getPreferredSize().width, logoImage.getPreferredSize().height));
    logoWrapper.add(logoImage);

        // Main container with background image
        JPanel container = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        container.setOpaque(true);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Header
        // JPanel header = new JPanel(new GridBagLayout());
        // header.setPreferredSize(new Dimension(1000, 0));
        // header.setOpaque(false);
        JLabel titleLabel = new JLabel("MediGuide AI");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 90));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.cyan);
        // header.add(titleLabel);

        // Description
        // JPanel describer = new JPanel(new GridBagLayout());
        // describer.setPreferredSize(new Dimension(1000, 0));
        // describer.setOpaque(false);
        JLabel description = new JLabel(
                "<html><div style='text-align: center;'>Welcome to MediGuide AI.<br>" +
                        "This clinical management system helps doctors classify disease severity<br>" +
                        "and supports informed diagnosis using AI.</div></html>",
                SwingConstants.CENTER
        );
        description.setFont(new Font("Segoe UI", Font.BOLD, 28));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setForeground(new Color(0x123456));
        // describer.add(description);

        // Button
        // JPanel bHolder = new JPanel(new GridBagLayout());
        // bHolder.setPreferredSize(new Dimension(1000, 100));
        // bHolder.setOpaque(false);
        JButton getStartedButton = createModernButton("Get Started");
        getStartedButton.addActionListener(e -> {
            if(e.getSource() == getStartedButton){

            }
        });
        getStartedButton.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        getStartedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // bHolder.add(getStartedButton);

        // Add header, description, button to container
    // Remove vertical strut above logoWrapper and between logoWrapper and titleLabel
        container.add(Box.createVerticalStrut(40));
        container.add(logoWrapper);
        // Remove any vertical strut between logoWrapper and titleLabel
        container.add(Box.createVerticalStrut(40));
        container.add(titleLabel);
        // container.add(Box.createVerticalStrut(30)); // keep space between title and description
        // container.add(description);
        container.add(Box.createVerticalStrut(20));
        container.add(getStartedButton);

        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(0x123456));
        footer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel footerContent = new JPanel();
        footerContent.setLayout(new BoxLayout(footerContent, BoxLayout.Y_AXIS));
        footerContent.setBackground(new Color(0x123456));

        JLabel slogan = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "MediGuide AI empowers healthcare professionals with intelligent insights.<br>"
                        + "Classify disease severity accurately, streamline clinical decisions,<br>"
                        + "and leverage AI-driven support for faster, informed diagnosis.<br>"
                        + "Better care starts with smarter guidance."
                        + "</div></html>",
                SwingConstants.CENTER
        );
        slogan.setFont(new Font("Segoe UI", Font.BOLD, 22));
        slogan.setForeground(Color.WHITE);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel copyright = new JLabel(
                "© 2025 MediGuide AI. All rights reserved.",
                SwingConstants.CENTER
        );
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyright.setForeground(Color.WHITE);

        footerContent.add(slogan);
        footerContent.add(Box.createRigidArea(new Dimension(0, 12)));
        footerContent.add(copyright);

        footer.add(footerContent, BorderLayout.CENTER);

        

        // Add container to frame center, footer at bottom
        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        // Pack or set size
        // setSize(1200, 800); // Optionally set a size if not maximized

        setVisible(true);
    }

    private JButton createModernButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(58, 123, 213),
                        getWidth(), getHeight(),
                        new Color(0x123456));
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