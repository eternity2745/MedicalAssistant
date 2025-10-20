package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.DoctorDAO;
import database.PatientDAO;
import utilities.Doctor;
import utilities.DoctorSession;
import utilities.Patient;

public class HomePage extends JPanel {

    private DefaultTableModel searchTableModel;
    private JTable searchTable;

    public HomePage() {
        Doctor doctor = DoctorSession.getCurrentDoctor();
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Good Morning, Dr. " + doctor.getName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(1, 4, 20, 0));

        String[] cardTitles = {"Total Patients", "Appointments Today", "Pending Reports", "AI Analyses"};
        String[] cardValues = {
                String.valueOf(doctor.getTotalPatients()),
                String.valueOf(doctor.getAppointments()),
                String.valueOf(doctor.getPending()),
                String.valueOf(doctor.getAIAnalysis())
        };

        for (int i = 0; i < cardTitles.length; i++) {
            JPanel card = new JPanel();
            card.setBackground(new Color(255, 255, 255, 240));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createEmptyBorder(100, 20, 20, 20));
            card.setPreferredSize(new Dimension(200, 300));
            cardsPanel.setLayout(new GridLayout(1, 4, 20, 0));
            cardsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

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

        JLabel recentLabel = new JLabel("Recent Patients");
        recentLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        recentLabel.setForeground(Color.WHITE);
        recentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(recentLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        List<Object[]> recentPatients = DoctorDAO.getRecentPatients(doctor.getID());

        if (recentPatients.isEmpty()) {
            JLabel noPatientsLabel = new JLabel("No recent patients yet.");
            noPatientsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            noPatientsLabel.setForeground(Color.LIGHT_GRAY);
            noPatientsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(noPatientsLabel);
        } else {
            String[] columns = {"Name", "Age", "Gender", "Condition"};
            Object[][] data = recentPatients.toArray(new Object[0][]);

            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JTable table = new JTable(model);
            table.setRowHeight(25);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 24));
            table.getTableHeader().setReorderingAllowed(false);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            table.setShowGrid(true);
            table.setGridColor(Color.BLACK);
            table.setPreferredScrollableViewportSize(new Dimension(600, table.getRowHeight() * Math.min(data.length, 5) + table.getTableHeader().getPreferredSize().height));

            JScrollPane tableScroll = new JScrollPane(table);
            tableScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            mainPanel.add(tableScroll);
        }

        mainPanel.add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

        JButton addPatientBtn = new JButton("Add Patient");
        JButton aiAnalysisBtn = new JButton("AI Analysis");
        addPatientBtn.setFocusPainted(false);
        aiAnalysisBtn.setFocusPainted(false);

        addPatientBtn.addActionListener(e -> {
            Color btnColor = new Color(58, 123, 213);

            JDialog selectionDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Add Patient", Dialog.ModalityType.APPLICATION_MODAL);
            selectionDialog.setSize(300, 150);
            selectionDialog.setLocationRelativeTo(this);

            JPanel selectionPanel = new JPanel();
            selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
            selectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JButton newPatientBtn = new JButton("New Patient");
            JButton existingPatientBtn = new JButton("Existing Patient");

            newPatientBtn.setBackground(btnColor);
            newPatientBtn.setForeground(Color.WHITE);
            newPatientBtn.setFocusPainted(false);
            newPatientBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            existingPatientBtn.setBackground(btnColor);
            existingPatientBtn.setForeground(Color.WHITE);
            existingPatientBtn.setFocusPainted(false);
            existingPatientBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            newPatientBtn.addActionListener(ev -> {
                selectionDialog.dispose();

                JDialog formDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Add New Patient", Dialog.ModalityType.APPLICATION_MODAL);
                formDialog.setSize(400, 600);
                formDialog.setLocationRelativeTo(this);

                JPanel content = new JPanel();
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JTextField nameField = new JTextField();
                JTextField ageField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField genderField = new JTextField();
                JTextField dobField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField allergiesField = new JTextField();
                JTextField medicationsField = new JTextField();
                JTextField bloodGroupField = new JTextField();
                JTextField conditionField = new JTextField();
                JTextField addressField = new JTextField();

                content.add(createLabeledField("Name:", nameField));
                content.add(createLabeledField("Age:", ageField));
                content.add(createLabeledField("Email:", emailField));
                content.add(createLabeledField("Gender:", genderField));
                content.add(createLabeledField("DOB (YYYY-MM-DD):", dobField));
                content.add(createLabeledField("Phone:", phoneField));
                content.add(createLabeledField("Allergies:", allergiesField));
                content.add(createLabeledField("Medications:", medicationsField));
                content.add(createLabeledField("Blood Group:", bloodGroupField));
                content.add(createLabeledField("Condition:", conditionField));
                content.add(createLabeledField("Address:", addressField));

                content.add(Box.createVerticalStrut(20));

                JButton submitBtn = new JButton("Submit");
                submitBtn.setBackground(btnColor);
                submitBtn.setForeground(Color.WHITE);
                submitBtn.setFocusPainted(false);
                submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

                submitBtn.addActionListener(ev2 -> {
                    Patient patient = new Patient();
                    patient.setName(nameField.getText());
                    patient.setAge(Integer.parseInt(ageField.getText()));
                    patient.setEmail(emailField.getText());
                    patient.setGender(genderField.getText());
                    patient.setDob(dobField.getText());
                    patient.setPhone(phoneField.getText());
                    patient.setAllergies(allergiesField.getText());
                    patient.setMedications(medicationsField.getText());
                    patient.setBloodGroup(bloodGroupField.getText());
                    patient.setCondition(conditionField.getText());
                    patient.setAddress(addressField.getText());

                    boolean success = PatientDAO.registerPatient(patient);
                    if (success) {
                        JOptionPane.showMessageDialog(formDialog, "✅ Patient added successfully!");
                        formDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(formDialog, "❌ Failed to add patient. Check for duplicate email or missing fields.");
                    }
                });

                content.add(submitBtn);
                formDialog.setContentPane(new JScrollPane(content));
                formDialog.setVisible(true);
            });


    existingPatientBtn.addActionListener(ev -> {
        selectionDialog.dispose();

        JDialog searchDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Search Existing Patient", Dialog.ModalityType.APPLICATION_MODAL);
        searchDialog.setSize(650, 500);
        searchDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Search Section ---
        JTextField searchField = new JTextField();
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(58, 123, 213));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(new JLabel("Enter Patient Name or Email:"));
        panel.add(searchField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchBtn);
        panel.add(Box.createVerticalStrut(20));

        // --- Table Section ---
        String[] searchColumns = {"ID", "Name", "Age", "Gender", "Medications", "Condition"};
        searchTableModel = new DefaultTableModel(searchColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchTable = new JTable(searchTableModel);
        searchTable.setRowHeight(25);
        searchTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane searchTableScroll = new JScrollPane(searchTable);
        searchTableScroll.setPreferredSize(new Dimension(600, 200));
        panel.add(searchTableScroll);
        panel.add(Box.createVerticalStrut(20));

        // --- Update Section ---
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setBorder(BorderFactory.createTitledBorder("Update Selected Patient"));
        updatePanel.setVisible(false); // hidden initially

        JTextField ageUpdateField = new JTextField();
        JTextField medicationUpdateField = new JTextField();
        JTextField diseaseUpdateField = new JTextField();

        ageUpdateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        medicationUpdateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        diseaseUpdateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        updatePanel.add(new JLabel("Update Age:"));
        updatePanel.add(ageUpdateField);
        updatePanel.add(Box.createVerticalStrut(10));

        updatePanel.add(new JLabel("Update Medications:"));
        updatePanel.add(medicationUpdateField);
        updatePanel.add(Box.createVerticalStrut(10));

        updatePanel.add(new JLabel("Update Disease:"));
        updatePanel.add(diseaseUpdateField);
        updatePanel.add(Box.createVerticalStrut(20));

        JButton updateBtn = new JButton("Update Patient");
        updateBtn.setBackground(new Color(58, 123, 213));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFocusPainted(false);
        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        updatePanel.add(updateBtn);

        panel.add(updatePanel);

        // ----- Search Action -----
        searchBtn.addActionListener(ev2 -> {
            String keyword = searchField.getText().trim();
            searchTableModel.setRowCount(0);
            if (!keyword.isEmpty()) {
                List<Patient> results = PatientDAO.searchPatients(keyword);
                for (Patient p : results) {
                    searchTableModel.addRow(new Object[]{
                            p.getID(),
                            p.getName(),
                            p.getAge(),
                            p.getGender(),
                            p.getMedications(),
                            p.getCondition()
                    });
                }
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(searchDialog, "No patients found.");
                } else {
                    updatePanel.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(searchDialog, "Please enter a name or email to search.");
            }
        });

        searchTable.getSelectionModel().addListSelectionListener(selevt -> {
            if (!selevt.getValueIsAdjusting() && searchTable.getSelectedRow() != -1) {
                updatePanel.setVisible(true);
            }
        });

        // ----- Update Action -----
        updateBtn.addActionListener(ev3 -> {
            int selectedRow = searchTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(searchDialog, "Please select a patient from the table.");
                return;
            }

            int patientID = (int) searchTableModel.getValueAt(selectedRow, 0);
            String newAge = ageUpdateField.getText().trim();
            String newMedications = medicationUpdateField.getText().trim();
            String newDisease = diseaseUpdateField.getText().trim();

            Patient patient = PatientDAO.getPatientByID(patientID);
            if (!newAge.isEmpty()) patient.setAge(Integer.parseInt(newAge));
            if (!newMedications.isEmpty()) patient.setMedications(newMedications);
            if (!newDisease.isEmpty()) patient.setCondition(newDisease);

            boolean success = PatientDAO.updatePatient(patient);
            if (success) {
                JOptionPane.showMessageDialog(searchDialog, "✅ Patient updated successfully!");
                searchTableModel.setValueAt(patient.getAge(), selectedRow, 2);
                searchTableModel.setValueAt(patient.getMedications(), selectedRow, 4);
                searchTableModel.setValueAt(patient.getCondition(), selectedRow, 5);
            } else {
                JOptionPane.showMessageDialog(searchDialog, "❌ Failed to update patient.");
            }
        });

        searchDialog.setContentPane(panel);
        searchDialog.setVisible(true);
    });


            selectionPanel.add(newPatientBtn);
            selectionPanel.add(Box.createVerticalStrut(15));
            selectionPanel.add(existingPatientBtn);

            selectionDialog.setContentPane(selectionPanel);
            selectionDialog.setVisible(true);
        });

        buttonPanel.add(addPatientBtn);
        buttonPanel.add(aiAnalysisBtn);
        mainPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    // Helper method for labeled fields
    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        return panel;
    }
}
