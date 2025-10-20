package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.MainApp;
import utilities.GradientPanel;

public class Dashboard extends JPanel {

    private MainApp parent;
    private JPanel contentPanel;

    private HomePage homePage;
    private SearchPatientsScreen searchPatientsScreen;
    private ProfileScreen profileScreen;
    private AIAnalysisScreen aiAnalysisScreen;


    public Dashboard(MainApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        homePage = new HomePage(); // only once
        searchPatientsScreen = new SearchPatientsScreen();
        profileScreen = new ProfileScreen();
        aiAnalysisScreen = new AIAnalysisScreen();

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
            case "Home" -> contentPanel.add(homePage, BorderLayout.CENTER);
            case "Search Patients" -> contentPanel.add(searchPatientsScreen, BorderLayout.CENTER);
            case "Profile" -> contentPanel.add(profileScreen, BorderLayout.CENTER);
            case "AI Analysis" -> contentPanel.add(aiAnalysisScreen, BorderLayout.CENTER);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ----- Screen Methods -----
    private void showHome() {
        switchScreen("Home");
    }

    private void showSearchPatients() {
        switchScreen("Search Patients");
    }


    private void showProfile() {
        switchScreen("Profile");
    }

    private void showAIAnalysis() {
        switchScreen("AI Analysis");
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
