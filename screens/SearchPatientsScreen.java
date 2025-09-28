package screens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Class for searching patient records Screen
public class SearchPatientsScreen extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JList<String> resultsList;
    private DefaultListModel<String> listModel;
    private JPanel overlayPanel;

    public SearchPatientsScreen() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Patient:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 30));
        searchField.setFont(new Font("Arial", Font.BOLD, 20));
        searchButton = new JButton("Search");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 30));
        searchLabel.setForeground(Color.WHITE);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setOpaque(false);

        add(searchPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        resultsList = new JList<>(listModel);
        resultsList.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 5));
        resultsList.setSize(300, 40);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsList.setOpaque(false);
        resultsList.setBackground(new Color(0,0,0,0)); 
        resultsList.setForeground(Color.WHITE);
        resultsList.setFont(new Font("Arial", Font.PLAIN, 24));
        JScrollPane scrollPane = new JScrollPane(resultsList);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        addDummyData();

        overlayPanel = new JPanel();
        overlayPanel.setLayout(new BorderLayout());
        overlayPanel.setVisible(false);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                filterResults(query);
            }
        });
        resultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = resultsList.getSelectedIndex();
                if ((selectedIndex != -1) && (e.getClickCount() == 2)) {
                    overlayPanel.removeAll();
                    // Example data for demonstration
                    String patientName = "John Doe";
                    String dob = "1990-05-15";
                    String gender = "Male";
                    String contact = "9876543210";
                    String bloodGroup = "O+";

                    changeScreen(patientName, dob, gender, contact, bloodGroup, scrollPane, searchPanel);

                }
            }
        });
    }

    private void changeScreen(String patientName, String dob, String gender, String contact, String bloodGroup, JScrollPane scrollPane, JPanel searchPanel) {

        PatientDetailsScreen patientDetails = new PatientDetailsScreen(
                            patientName, dob, gender, contact, bloodGroup, scrollPane, searchPanel, this, overlayPanel
                    );
        overlayPanel.add(patientDetails, BorderLayout.CENTER);
        overlayPanel.setVisible(true);
        overlayPanel.revalidate();
        overlayPanel.repaint();
        scrollPane.setVisible(false);
        searchPanel.setVisible(false);
        add(overlayPanel, BorderLayout.CENTER);
    }

    private void addDummyData() {
        listModel.addElement("P001 - Rahul Sharma (32, Male)");
        listModel.addElement("P002 - Anjali Verma (28, Female)");
        listModel.addElement("P003 - Amit Kumar (40, Male)");
        listModel.addElement("P004 - Neha Singh (25, Female)");
        listModel.addElement("P001 - Rahul Sharma (32, Male)");
        listModel.addElement("P002 - Anjali Verma (28, Female)");
        listModel.addElement("P003 - Amit Kumar (40, Male)");
        listModel.addElement("P004 - Neha Singh (25, Female)");
        listModel.addElement("P001 - Rahul Sharma (32, Male)");
        listModel.addElement("P002 - Anjali Verma (28, Female)");
        listModel.addElement("P003 - Amit Kumar (40, Male)");
        listModel.addElement("P004 - Neha Singh (25, Female)");
    }

    private void filterResults(String query) {
        listModel.clear();
        if (query.isEmpty()) {
            addDummyData();
            return;
        }

        if ("rahul".contains(query)) listModel.addElement("P001 - Rahul Sharma (32, Male)");
        if ("anjali".contains(query)) listModel.addElement("P002 - Anjali Verma (28, Female)");
        if ("amit".contains(query)) listModel.addElement("P003 - Amit Kumar (40, Male)");
        if ("neha".contains(query)) listModel.addElement("P004 - Neha Singh (25, Female)");
    }
}
