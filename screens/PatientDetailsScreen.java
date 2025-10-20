package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import database.HistoryDAO;
import utilities.CircularImagePanel;
import utilities.History;

// Class For Showing Patient Details
public class PatientDetailsScreen extends JPanel {

    public PatientDetailsScreen(int patientID, String patientName, int age, String profilePic, String dob, String gender, String contact, String bloodGroup, String allergies, String medications, String conditions, String address, JScrollPane scrlPane, JPanel searchPanel, JPanel searchPatients, JPanel overPanel) {
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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        topPanel.setOpaque(false);

        CircularImagePanel imagePanel;
        try {
            imagePanel = new CircularImagePanel(ImageIO.read(new File(profilePic)));
        } catch (IOException e) {
            imagePanel = new CircularImagePanel(null);
            System.err.println("Error loading image: " + e.getMessage());
        }
        topPanel.add(imagePanel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name: " + patientName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        JLabel ageLabel = new JLabel("Age: " + age);
        ageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ageLabel.setForeground(Color.WHITE);
        JLabel dobLabel = new JLabel("DOB: " + dob);
        dobLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dobLabel.setForeground(Color.WHITE);
        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        genderLabel.setForeground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(ageLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(dobLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(genderLabel);

        topPanel.add(infoPanel);

        mainPanel.add(topPanel);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 2, 30, 20));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        detailsPanel.setOpaque(false);

        detailsPanel.add(createLabel("Contact: " + contact));
        detailsPanel.add(createLabel("Blood Group: " + bloodGroup));
        detailsPanel.add(createLabel("Allergies: " + allergies));
        detailsPanel.add(createLabel("Existing Conditions: " + conditions));
        detailsPanel.add(createLabel("Medications: " + medications));
        detailsPanel.add(createLabel("Address: " + address));

        mainPanel.add(detailsPanel);

        JLabel visitLabel = new JLabel("Visit History");
        visitLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        visitLabel.setForeground(Color.WHITE);
        visitLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        visitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(visitLabel);

        List<History> history = HistoryDAO.getVisitHistoryByPatientID(patientID);

        if (history.isEmpty()) {
            JPanel noHistoryPanel = new JPanel();
            noHistoryPanel.setOpaque(false);
            noHistoryPanel.setPreferredSize(new Dimension(visitTableWidth(), 250));
            noHistoryPanel.setLayout(new BoxLayout(noHistoryPanel, BoxLayout.Y_AXIS));

            JLabel noHistoryLabel = new JLabel("No visit history available for this patient.");
            noHistoryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 24));
            noHistoryLabel.setForeground(Color.LIGHT_GRAY);
            noHistoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noHistoryLabel.setAlignmentY(Component.TOP_ALIGNMENT);

            noHistoryPanel.add(noHistoryLabel);
            noHistoryPanel.add(Box.createVerticalGlue());

            mainPanel.add(noHistoryPanel);
        } else {
            String[] columns = {"Date", "Disease", "Doctor", "Hospital"};
            String[][] data = new String[history.size()][columns.length];

            for (int i = 0; i < history.size(); i++) {
                History r = history.get(i);
                data[i][0] = r.getDate();
                data[i][1] = r.getDisease();
                data[i][2] = r.getDoctorName();
                data[i][3] = r.getHospital();
            }

            JTable visitTable = new JTable(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < visitTable.getColumnCount(); i++) {
                visitTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            visitTable.setFillsViewportHeight(true);
            visitTable.setRowHeight(30);
            visitTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            visitTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 24));
            visitTable.getTableHeader().setReorderingAllowed(false);

            JScrollPane tableScroll = new JScrollPane(visitTable);
            tableScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            tableScroll.setPreferredSize(new Dimension(visitTable.getPreferredSize().width, 250));

            mainPanel.add(tableScroll);
        }

        add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            overPanel.setVisible(false);
            searchPatients.remove(overPanel);
            scrlPane.setVisible(true);
            searchPanel.setVisible(true);

        });
    }

    private int visitTableWidth() {
        return 800;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}
