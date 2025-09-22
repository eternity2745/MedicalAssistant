package screens;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utilities.CircularImagePanel;

public class ProfileScreen extends JPanel {

    public ProfileScreen() {
        setLayout(new BorderLayout());
        setOpaque(false); // transparent to show gradient

        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // ----- Title -----
        JLabel title = new JLabel("Doctor Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(40));

        // ----- Round Profile Picture -----

        CircularImagePanel profilePic = new CircularImagePanel("/screens/doctorProfile.jpg", 150);
        profilePic.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(30));

        // ----- Doctor Details Panel -----
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        String[][] details = {
                {"Name:", "Dr. John Doe"},
                {"Specialization:", "Cardiologist"},
                {"Email:", "johndoe@example.com"},
                {"Phone:", "+91 9876543210"},
                {"Hospital:", "City Hospital"}
        };

        for (int i = 0; i < details.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(details[i][0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            detailsPanel.add(label, gbc);

            gbc.gridx = 1;
            JLabel value = new JLabel(details[i][1]);
            value.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            value.setForeground(Color.WHITE);
            detailsPanel.add(value, gbc);
        }

        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ----- Action Buttons -----
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);

        JButton editProfileBtn = new JButton("Edit Profile");
        JButton changePasswordBtn = new JButton("Change Password");

        buttonPanel.add(editProfileBtn);
        buttonPanel.add(changePasswordBtn);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue()); // pushes content slightly up for scroll

        // ----- Scrollable container -----
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

}
