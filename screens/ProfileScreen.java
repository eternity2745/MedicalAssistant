package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import utilities.CircularImagePanel;
import utilities.Doctor;
import utilities.DoctorSession;

//Class for showing Profile Of Doctor
public class ProfileScreen extends JPanel {

    public ProfileScreen() {

        Doctor doctor = DoctorSession.getCurrentDoctor();
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Doctor Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(40));


        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(doctor.getProfilePic()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        CircularImagePanel profileImage = new CircularImagePanel(img);

        JPanel imageWrapper = new JPanel();
        imageWrapper.setOpaque(false);
        imageWrapper.add(profileImage);

        mainPanel.add(imageWrapper, BorderLayout.NORTH);

        mainPanel.add(Box.createVerticalStrut(10));

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;

        String[][] details = {
                {"Name:", "Dr. " + doctor.getName()},
                {"Specialization:", doctor.getSpecialization()},
                {"Email:", doctor.getEmail()},
                {"Phone:", doctor.getPhone()},
                {"Hospital:", doctor.getHospital()}
        };

        for (int i = 0; i < details.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(details[i][0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 25));
            label.setForeground(Color.WHITE);
            detailsPanel.add(label, gbc);

            gbc.gridx = 1;
            JLabel value = new JLabel(details[i][1]);
            value.setFont(new Font("Segoe UI", Font.PLAIN, 25));
            value.setForeground(Color.WHITE);
            detailsPanel.add(value, gbc);
        }

        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);

        JButton editProfileBtn = new JButton("Edit Profile");
        JButton changePasswordBtn = new JButton("Change Password");

        buttonPanel.add(editProfileBtn);
        buttonPanel.add(changePasswordBtn);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

}
