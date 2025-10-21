package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import database.HistoryDAO;
import utilities.History;

public class PendingReportsWindow extends JFrame {
    private JTable reportsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public PendingReportsWindow() {
        setTitle("Pending Reports");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(15, 25, 50));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Pending Reports", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setOpaque(false);
        searchField = new JTextField(25);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");

        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        searchBtn.addActionListener(e -> searchReports());
        refreshBtn.addActionListener(e -> loadPendingReports());

        searchPanel.add(new JLabel("Search by Patient ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        String[] columns = {"Patient ID", "Doctor ID", "Disease", "Hospital", "Date", "Completed"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        reportsTable = new JTable(tableModel);
        reportsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reportsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        reportsTable.setRowHeight(28);
        reportsTable.setBackground(Color.WHITE);
        reportsTable.setSelectionBackground(new Color(180, 200, 255));

        JScrollPane scrollPane = new JScrollPane(reportsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton submitBtn = new JButton("Mark as Completed");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitBtn.setBackground(new Color(0, 150, 100));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.addActionListener(e -> markAsCompleted());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(submitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadPendingReports();

        setVisible(true);
    }

    private void loadPendingReports() {
        tableModel.setRowCount(0);
        List<History> pendingReports = HistoryDAO.getPendingReports();

        if (pendingReports.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No pending reports found!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (History record : pendingReports) {
                tableModel.addRow(new Object[]{
                    record.getPatientID(),
                    record.getDoctorID(),
                    record.getDisease(),
                    record.getHospital(),
                    record.getDate(),
                    record.getCompleted()
                });
            }
        }
    }

    private void searchReports() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadPendingReports();
            return;
        }

        tableModel.setRowCount(0);
        List<History> results = HistoryDAO.searchPendingReports(keyword);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No reports found for given Patient ID!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (History record : results) {
                tableModel.addRow(new Object[]{
                    record.getPatientID(),
                    record.getDoctorID(),
                    record.getDisease(),
                    record.getHospital(),
                    record.getDate(),
                    record.getCompleted()
                });
            }
        }
    }

    private void markAsCompleted() {
        int[] selectedRows = reportsTable.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one report!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int row : selectedRows) {
            int patientID = (int) tableModel.getValueAt(row, 0);
            int doctorID = (int) tableModel.getValueAt(row, 1);
            HistoryDAO.markReportCompleted(patientID, doctorID);
        }

        JOptionPane.showMessageDialog(this, "Selected reports marked as completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        loadPendingReports();
    }
}
