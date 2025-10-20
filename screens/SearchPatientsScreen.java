package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import database.PatientDAO;
import utilities.Patient;

public class SearchPatientsScreen extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JList<String> resultsList;
    private DefaultListModel<String> listModel;
    private JPanel overlayPanel;
    private JScrollPane scrollPane;
    private JPanel searchPanel;

    public SearchPatientsScreen() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // 🔍 Search Bar Panel
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Patient:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 30));
        searchField.setFont(new Font("Arial", Font.BOLD, 20));
        searchButton = new JButton("Search");

        searchLabel.setFont(new Font("Arial", Font.BOLD, 30));
        searchLabel.setForeground(Color.WHITE);
        searchPanel.setOpaque(false);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // 📋 List of Results
        listModel = new DefaultListModel<>();
        resultsList = new JList<>(listModel);
        resultsList.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 5));
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsList.setOpaque(false);
        resultsList.setBackground(new Color(0, 0, 0, 0));
        resultsList.setForeground(Color.WHITE);
        resultsList.setFont(new Font("Arial", Font.PLAIN, 22));

        scrollPane = new JScrollPane(resultsList);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        // 🩺 Overlay for details
        overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setVisible(false);

        // 🔘 Action: Search button click
        searchButton.addActionListener(e -> searchPatients());

        // 🔍 Action: Enter key triggers search
        searchField.addActionListener(e -> searchPatients());

        // 🖱️ Double-click patient -> open details
        resultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = resultsList.getSelectedIndex();
                if (selectedIndex != -1 && e.getClickCount() == 2) {
                    String selectedValue = listModel.getElementAt(selectedIndex);
                    Patient patient = (Patient) resultsList.getClientProperty(selectedValue);

                    if (patient != null) {
                        openPatientDetails(patient);
                    }
                }
            }
        });

        // Initial Load: all patients
        loadAllPatients();
    }

    /** Fetches all patients from the database initially */
    private void loadAllPatients() {
        listModel.clear();
        List<Patient> patients = PatientDAO.getAllPatients();

        if (patients.isEmpty()) {
            listModel.addElement("⚠️ No patients in the system yet.");
            return;
        }

        for (Patient p : patients) {
            String label = String.format("%s - %s (%d, %s)",
                    p.getID(), p.getName(), p.getAge(), p.getGender());
            listModel.addElement(label);
            resultsList.putClientProperty(label, p); // store actual Patient object
        }
    }

    /** Filters patients based on query input */
    private void searchPatients() {
        String query = searchField.getText().trim().toLowerCase();
        listModel.clear();

        if (query.isEmpty()) {
            loadAllPatients();
            return;
        }

        List<Patient> results = PatientDAO.searchPatientsScreen(query);

        if (results.isEmpty()) {
            listModel.addElement("⚠️ No matching patients found.");
            return;
        }

        for (Patient p : results) {
            String label = String.format("%s - %s (%d, %s)",
                    p.getID(), p.getName(), p.getAge(), p.getGender());
            listModel.addElement(label);
            resultsList.putClientProperty(label, p);
        }
    }

    /** Opens detailed patient screen overlay */
    private void openPatientDetails(Patient patient) {
        overlayPanel.removeAll();

        PatientDetailsScreen detailsScreen = new PatientDetailsScreen(
                patient.getName(),
                patient.getAge(),
                patient.getProfilePic(),
                patient.getDob(),
                patient.getGender(),
                patient.getPhone(),
                patient.getBloodGroup(),
                patient.getAllergies(),
                patient.getMedications(),
                patient.getCondition(),
                patient.getAddress(),
                scrollPane,
                searchPanel,
                this,
                overlayPanel
        );

        overlayPanel.add(detailsScreen, BorderLayout.CENTER);
        overlayPanel.setVisible(true);
        overlayPanel.revalidate();
        overlayPanel.repaint();

        scrollPane.setVisible(false);
        searchPanel.setVisible(false);
        add(overlayPanel, BorderLayout.CENTER);
    }
}
