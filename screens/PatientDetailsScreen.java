package screens;

import java.awt.*; // optional custom class
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import utilities.CircularImagePanel;

public class PatientDetailsScreen extends JPanel {

    public PatientDetailsScreen(String patientName, String dob, String gender, String contact, String bloodGroup, JScrollPane scrlPane, JPanel searchPanel, JPanel searchPatients, JPanel overPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(10, 25, 70));

        JPanel backBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backBtnPanel.setOpaque(false); // transparent
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtnPanel.add(backButton);
        JLabel backLabel = new JLabel("   Patient Details");
        backLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        backLabel.setForeground(Color.WHITE);
        backBtnPanel.add(backLabel);
        add(backBtnPanel, BorderLayout.NORTH);

        // Scrollable main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Top panel with image and basic info
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        topPanel.setOpaque(false);

        // Patient Image
        CircularImagePanel imagePanel;
        try {
            imagePanel = new CircularImagePanel(ImageIO.read(new File("D:\\Java\\MedicalAssistant\\Resources\\Images\\doctorProfile.jpg"))); // size 120px
        } catch (Exception e) {
            imagePanel = new CircularImagePanel(null); // fallback if image not found
            System.err.println("Error loading image: " + e.getMessage());
        }
        topPanel.add(imagePanel);

        // Basic Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name: " + patientName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        JLabel dobLabel = new JLabel("DOB: " + dob);
        dobLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dobLabel.setForeground(Color.WHITE);
        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        genderLabel.setForeground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(dobLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(genderLabel);

        topPanel.add(infoPanel);

        mainPanel.add(topPanel);

        // Contact & Medical Info Section
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 2, 30, 20));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        detailsPanel.setOpaque(false);

        detailsPanel.add(createLabel("Contact: " + contact));
        detailsPanel.add(createLabel("Blood Group: " + bloodGroup));
        detailsPanel.add(createLabel("Allergies: None"));
        detailsPanel.add(createLabel("Existing Conditions: Hypertension"));
        detailsPanel.add(createLabel("Medications: None"));
        detailsPanel.add(createLabel("Address: 123, Main Street"));

        mainPanel.add(detailsPanel);

        // Visit History / Summary
        JLabel visitLabel = new JLabel("Visit History");
        visitLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        visitLabel.setForeground(Color.WHITE);
        visitLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
        mainPanel.add(visitLabel);

        String[] columns = {"Date", "Reason", "Doctor"};
        String[][] data = {
            {"2025-01-12", "Routine Checkup", "Dr. Smith"},
            {"2025-03-05", "Blood Test", "Dr. Johnson"},
            {"2025-05-20", "Follow-up", "Dr. Smith"}
        };

        JTable visitTable = new JTable(data, columns);
        visitTable.setFillsViewportHeight(true);
        visitTable.setEnabled(false);
        visitTable.setRowHeight(30);
        visitTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        visitTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane tableScroll = new JScrollPane(visitTable);
        tableScroll.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        mainPanel.add(tableScroll);

        add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            overPanel.setVisible(false);
            searchPatients.remove(overPanel);
            scrlPane.setVisible(true);
            searchPanel.setVisible(true);
            // hide overlay when back pressed
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}
