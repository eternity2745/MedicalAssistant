package screens;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import app.MainApp;
import utilities.GradientPanel;

public class Dashboard extends JPanel {

    private MainApp parent;
    private JPanel contentPanel;

    public Dashboard(MainApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // ----- Left Navigation Panel -----
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(10, 25, 70));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));

        List<JLabel> navLabels = new ArrayList<>();
        JLabel[] active = new JLabel[1]; // store active label
        String[] navItems = {"Home", "Search Patients", "Profile", "AI Analysis"};

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            if(item.equals("Home")) {
                label.setForeground(Color.CYAN);
                active[0] = label;
            } else {
                label.setForeground(Color.WHITE);
            }
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            navLabels.add(label);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Reset previous active label to white
                    if (active[0] != null && active[0] != label) {
                        active[0].setForeground(Color.WHITE);
                    }

                    // Set clicked label as active
                    label.setForeground(Color.CYAN);
                    active[0] = label;

                    switchScreen(item);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (label != active[0]) {
                        label.setForeground(new Color(0, 200, 255)); // hover effect
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (label != active[0]) {
                        label.setForeground(Color.WHITE);
                    }
                }
            });

            navPanel.add(label);
        }


        add(navPanel, BorderLayout.WEST);

        // ----- Right Content Panel -----
        contentPanel = new GradientPanel(new Color(10, 25, 70), new Color(30, 50, 100));
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showHome();
    }

    // ----- Switch screens in contentPanel -----
    public void switchScreen(String screen) {
        contentPanel.removeAll();
        switch(screen) {
            case "Home" -> showHome();
            case "Search Patients" -> showSearchPatients();
            case "Profile" -> showProfile();
            case "AI Analysis" -> showAIAnalysis();
            
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ----- Screen Methods -----
    private void showHome() {
        contentPanel.removeAll();
        contentPanel.add(new HomePage(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showSearchPatients() {
        contentPanel.removeAll();
        contentPanel.add(new SearchPatientsScreen(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showProfile() {
        contentPanel.removeAll();
        contentPanel.add(new ProfileScreen(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAIAnalysis() {
        contentPanel.removeAll();
        contentPanel.add(new AIAnalysisScreen(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Gradient background for right panel
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(58, 123, 213),
                                             0, getHeight(), new Color(58, 213, 187));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
