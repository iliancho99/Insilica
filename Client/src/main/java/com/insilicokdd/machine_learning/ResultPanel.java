package com.insilicokdd.machine_learning;

import com.insilicokdd.data.Model;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ResultPanel {
    private JPanel resultPanel;
    private JLabel result;
    private JLabel accuracyShowLbl;
    private JLabel accuracyLbl;
    private JLabel recallLbl;
    private JLabel recallShowLbl;
    private JLabel f1_measureShowLbl;
    private JLabel f1_measureLbl;
    private JLabel auprLbl;
    private JLabel auprShowLbl;
    private JLabel aurocLbl;
    private JLabel aurocShowLbl;
    private JLabel precisionLbl;
    private JLabel precisionShowLbl;

    private JLabel loadingLabel;
    private ImageIcon loading;

    public ResultPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc) {
        resultPanel = new JPanel();
        resultPanel.setLayout(gridBagLayout);
        resultPanel.setBackground(new Color(255, 255, 255));
        result = new JLabel("Result :");
        result.setFont(new Font("Verdana", Font.BOLD, 14));
        result.setForeground(new Color(0,0, 77));

        accuracyLbl = getLabel("");
        f1_measureLbl = getLabel("");
        recallLbl = getLabel("");
        auprLbl = getLabel("");
        aurocLbl = getLabel("");
        precisionLbl = getLabel("");


        ClassLoader classLoader = getClass().getClassLoader();
        loading = new ImageIcon(Objects.requireNonNull(classLoader.getResource("Gif/loader.gif")).getFile());
        loadingLabel = new JLabel("Training... ", loading, JLabel.CENTER);
        loadingLabel.setVisible(false);


        plotLayout.addobjects(result, resultPanel, gridBagLayout, gbc, 1, 1, 1, 0, 4, 1);

        plotLayout.addobjects(getLabelShow("Accuracy:" ), resultPanel, gridBagLayout, gbc, 1, 2, 0, 0, 1, 1);
        plotLayout.addobjects(accuracyLbl, resultPanel, gridBagLayout, gbc, 2, 2, 3, 0, 2, 1);

        plotLayout.addobjects(getLabelShow("Recall:"), resultPanel, gridBagLayout, gbc, 1, 3, 1, 0, 1, 1);
        plotLayout.addobjects(recallLbl, resultPanel, gridBagLayout, gbc, 2, 3, 3, 0, 2, 1);

        plotLayout.addobjects(getLabelShow("F1 Measure:"), resultPanel, gridBagLayout, gbc, 1, 4, 1, 0, 1, 1);
        plotLayout.addobjects(f1_measureLbl, resultPanel, gridBagLayout, gbc, 2, 4, 3, 0, 2, 1);

        plotLayout.addobjects(getLabelShow("Aupr:"), resultPanel, gridBagLayout, gbc, 1, 5, 1, 0, 1, 1);
        plotLayout.addobjects(auprLbl, resultPanel, gridBagLayout, gbc, 2, 5, 3, 0, 2, 1);

        plotLayout.addobjects(getLabelShow("Auroc:"), resultPanel, gridBagLayout, gbc, 1, 6, 1, 0, 1, 1);
        plotLayout.addobjects(aurocLbl, resultPanel, gridBagLayout, gbc, 2, 6, 3, 0, 2, 1);

        plotLayout.addobjects(getLabelShow("Precision:"), resultPanel, gridBagLayout, gbc, 1, 7, 1, 0, 1, 1);
        plotLayout.addobjects(precisionLbl, resultPanel, gridBagLayout, gbc, 2, 7, 3, 0, 2, 1);

        plotLayout.addobjects(loadingLabel, resultPanel, gridBagLayout, gbc, 4, 4, 1, 1, 1, 2);
    }

    private JLabel getLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(77, 25, 0));
        return label;
    }

    private JLabel getLabelShow(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(0, 0, 77));
        return label;
    }

    public JPanel getResultPanel() {
        return resultPanel;
    }

    public void clearResult() {
        accuracyLbl.setText("");
        recallLbl.setText("");
        f1_measureLbl.setText("");
        auprLbl.setText("");
        aurocLbl.setText("");
        precisionLbl.setText("");
    }

    public JLabel getAccuracyLbl() {
        return accuracyLbl;
    }

    public JLabel getRecallLbl() {
        return recallLbl;
    }

    public JLabel getF1MeasureLbl() {
        return f1_measureLbl;
    }

    public JLabel getAuprLbl() {
        return auprLbl;
    }

    public JLabel getAurocLbl() {
        return aurocLbl;
    }

    public JLabel getPrecisionLbl() {
        return precisionLbl;
    }

    public JLabel getLoadingLabel() { return loadingLabel; }

    JPanel getPanel() {
        return resultPanel;
    }
}
