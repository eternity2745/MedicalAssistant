package screens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchPatientsScreen extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JList<String> resultsList;
    private DefaultListModel<String> listModel;

    public SearchPatientsScreen() {
        setLayout(new BorderLayout());
        setOpaque(false);
        // setBackground(Color.WHITE);

        // --- Top search bar ---
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

        // --- List for results ---
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

        // --- Dummy Data (replace later with DB query) ---
        addDummyData();

        // --- Search Action ---
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                filterResults(query);
            }
        });

        // --- Click on list item to open PatientDetailsScreen ---
        resultsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double-click
                    String selected = resultsList.getSelectedValue();
                    if (selected != null) {
                        // open new screen with patient details
                        // JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(SearchPatientsScreen.this);
                        // topFrame.setContentPane(new PatientDetailsScreen(selected));
                        // topFrame.revalidate();
                    }
                }
            }
        });
    }

    // Dummy patient data (replace with SQL later)
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

    // Simple filter
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
