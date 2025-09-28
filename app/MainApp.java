package app;
import java.awt.*;
import javax.swing.*;
import screens.Dashboard;
import screens.Login;
import screens.Register;
import screens.WelcomePage;

public class MainApp extends JFrame {

    private JPanel mainPanel; // container for all screens
    private CardLayout cardLayout;

    public MainApp() {
        setTitle("MediGuide");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CardLayout container
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Screens
        WelcomePage welcomePanel = new WelcomePage(this);
        Login loginPanel = new Login(this);
        Register registerPanel = new Register(this);
        Dashboard dashboardPanel = new Dashboard(this);

        // Add screens to mainPanel
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(dashboardPanel, "Dashboard");

        add(mainPanel);
        setVisible(true);
    }

    // Method to switch screens
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}
