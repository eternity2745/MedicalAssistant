package screens;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HomePage extends JPanel {

    public HomePage() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Main vertical panel inside scroll pane
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ----- Welcome Label -----
        JLabel welcomeLabel = new JLabel("Good Morning, Dr. John Doe");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        // ----- Cards Panel -----
        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(1, 4, 20, 0)); // 4 cards with spacing

        String[] cardTitles = {"Total Patients", "Appointments Today", "Pending Reports", "AI Analyses"};
        String[] cardValues = {"120", "5", "3", "7"};

        for (int i = 0; i < cardTitles.length; i++) {
            JPanel card = new JPanel();
            card.setBackground(new Color(255, 255, 255, 240));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createEmptyBorder(100, 20, 20, 20));

            JLabel title = new JLabel(cardTitles[i]);
            title.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            title.setForeground(Color.DARK_GRAY);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel value = new JLabel(cardValues[i]);
            value.setFont(new Font("Segoe UI", Font.BOLD, 34));
            value.setForeground(new Color(58, 123, 213));
            value.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(title);
            card.add(Box.createVerticalStrut(10));
            card.add(value);

            cardsPanel.add(card);
        }

        mainPanel.add(cardsPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        // ----- Recent Patients Table -----
        JLabel recentLabel = new JLabel("Recent Patients");
        recentLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        recentLabel.setForeground(Color.WHITE);
        recentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(recentLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        String[] columns = {"Name", "Age", "Gender", "Condition"};
        Object[][] data = {
            {"John Doe", 35, "Male", "Fever"},
            {"Jane Smith", 28, "Female", "Headache"},
            {"Alice Johnson", 42, "Female", "Diabetes"},
            {"Bob Brown", 50, "Male", "Hypertension"},
            {"Michael Scott", 45, "Male", "Cold"},
            {"John Doe", 35, "Male", "Fever"},
            {"Jane Smith", 28, "Female", "Headache"},
            {"Alice Johnson", 42, "Female", "Diabetes"},
            {"Bob Brown", 50, "Male", "Hypertension"},
            {"Michael Scott", 45, "Male", "Cold"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setPreferredScrollableViewportSize(new Dimension(600, 5));

        mainPanel.add(table);
        mainPanel.add(Box.createVerticalStrut(30));

        // ----- Quick Action Buttons -----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

        JButton addPatientBtn = new JButton("Add Patient");
        JButton aiAnalysisBtn = new JButton("AI Analysis");

        buttonPanel.add(addPatientBtn);
        buttonPanel.add(aiAnalysisBtn);

        mainPanel.add(buttonPanel);

        // ----- Scrollable container -----
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
