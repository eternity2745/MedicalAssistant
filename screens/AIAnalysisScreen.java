package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import utilities.AIAnalysisManager;

public class AIAnalysisScreen extends JPanel {

    private JTextField patientIdField;
    private JTextArea symptomInputArea;
    private JButton uploadReportBtn, analyzeBtn;
    private JTextArea resultArea;

    public AIAnalysisScreen() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Gradient background panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 25, 70), 0, h, new Color(30, 50, 100));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                super.paintComponent(g);
            }
        };
        gradientPanel.setLayout(new BoxLayout(gradientPanel, BoxLayout.Y_AXIS));
        gradientPanel.setOpaque(false);
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 50));

        // ----- Patient ID input -----
        JLabel patientIdLabel = new JLabel("Enter Patient ID:");
        patientIdLabel.setForeground(Color.WHITE);
        patientIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        patientIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        patientIdField = new JTextField();
        patientIdField.setMaximumSize(new Dimension(400, 30));
        patientIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        patientIdField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(150, 150, 150)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        patientIdField.setAlignmentX(Component.CENTER_ALIGNMENT);

        gradientPanel.add(patientIdLabel);
        gradientPanel.add(Box.createVerticalStrut(10));
        gradientPanel.add(patientIdField);
        gradientPanel.add(Box.createVerticalStrut(20));

        // ----- Symptoms input -----
        JLabel symptomsLabel = new JLabel("Patient Symptoms:");
        symptomsLabel.setForeground(Color.WHITE);
        symptomsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        symptomsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        symptomInputArea = new JTextArea();
        symptomInputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        symptomInputArea.setLineWrap(true);
        symptomInputArea.setWrapStyleWord(true);
        // symptomInputArea.setPreferredSize(new Dimension(400, 80));
        // symptomInputArea.setMaximumSize(new Dimension(400, 80));
        symptomInputArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(150, 150, 150)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        // symptomInputArea.setBackground(new Color(255, 255, 255, 220));
        symptomInputArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane symptomScroll = new JScrollPane(symptomInputArea);
        symptomScroll.setPreferredSize(new Dimension(800, 100));
        symptomScroll.setMaximumSize(new Dimension(800, 100));
        symptomScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        symptomScroll.setOpaque(false);
        symptomScroll.getViewport().setOpaque(false);

        gradientPanel.add(symptomsLabel);
        gradientPanel.add(Box.createVerticalStrut(10));
        gradientPanel.add(symptomScroll);
        gradientPanel.add(Box.createVerticalStrut(20));

        // ----- Buttons -----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        uploadReportBtn = new JButton("Upload Report");
        analyzeBtn = new JButton("Analyze Symptoms");

        buttonPanel.add(uploadReportBtn);
        buttonPanel.add(analyzeBtn);

        gradientPanel.add(buttonPanel);
        // gradientPanel.add(Box.createVerticalStrut(0));

        // ----- Results area -----
        JLabel resultsLabel = new JLabel("Analysis Result:");
        resultsLabel.setForeground(Color.WHITE);
        resultsLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(150, 150, 150)),
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
        ));
        // resultArea.setBackground(new Color(255, 255, 255, 220));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setPreferredSize(new Dimension(450, 150));
        resultScroll.setOpaque(false);
        resultScroll.getViewport().setOpaque(false);

        gradientPanel.add(resultsLabel);
        gradientPanel.add(Box.createVerticalStrut(10));
        gradientPanel.add(resultScroll);

        // ----- Scrollable container -----
        JScrollPane scrollPane = new JScrollPane(gradientPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // ----- Button Actions -----
        uploadReportBtn.addActionListener(e -> {
            String patientId = patientIdField.getText().trim();
            if (patientId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient ID!");
                return;
            }

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Patient Report PDF");
            chooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File pdfFile = chooser.getSelectedFile();
                resultArea.setText("Analyzing file: " + pdfFile.getName() + "...\nPlease wait...");

                new Thread(() -> {
                     try {
                        AIAnalysisManager ai = new AIAnalysisManager();
                        String result = ai.analyzeDocument(pdfFile.getAbsolutePath());

                        SwingUtilities.invokeLater(() -> {
                            resultArea.setText("Analysis for patient ID: " + patientId + "\n\n" + result);
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        SwingUtilities.invokeLater(() -> 
                            resultArea.setText("Error during AI analysis:\n" + ex.getMessage())
                        );
                    }
                }).start();
            }

        });

        analyzeBtn.addActionListener(e -> {
            String patientId = patientIdField.getText().trim();
            String symptoms = symptomInputArea.getText().trim();

            if (patientId.isEmpty() || symptoms.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient ID and Symptoms!");
                return;
            }

            resultArea.setText("Analyzing patient ID: " + patientId + "...\n\nPlease wait...");

            // Run AI analysis in a background thread so UI doesn't freeze
            new Thread(() -> {
                try {
                    AIAnalysisManager ai = new AIAnalysisManager();
                    String result = ai.analyzeSymptoms(symptoms);

                    SwingUtilities.invokeLater(() -> {
                        resultArea.setText("Analysis for patient ID: " + patientId + "\n\n" + result);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> 
                        resultArea.setText("Error during AI analysis:\n" + ex.getMessage())
                    );
                }
                }).start();
                });
    }
}
