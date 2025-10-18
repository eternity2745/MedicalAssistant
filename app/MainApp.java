package app;
import java.awt.CardLayout;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import database.DatabaseConnection;
import screens.Dashboard;
import screens.Login;
import screens.Register;
import screens.WelcomePage;

// Class for Main Starting Point of the App (Uses Card Layout To Switch Screens)
public class MainApp extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainApp() {
        setTitle("MediGuide");
        try {
            setIconImage(ImageIO.read(new File("Resources\\Images\\logo.png")));
        } catch (Exception e) {
            
        }
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        WelcomePage welcomePanel = new WelcomePage(this);
        Login loginPanel = new Login(this);
        Register registerPanel = new Register(this);
        Dashboard dashboardPanel = new Dashboard(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(dashboardPanel, "Dashboard");

        add(mainPanel);
        setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection.getConnection();
            new MainApp();
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeConnection();
        }));
    }
}
