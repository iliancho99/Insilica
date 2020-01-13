package com.insilicokdd.machine_learning;

import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;

// OperationalPanel : Sub-panel
public class ConfigurationPanel {
    private JPanel preferencesPanel;
    private JLabel methodLbl;
    private JLabel datasetLbl;
    private JSeparator jSeparator;
    private JLabel modelSelectedLbl;
    private JLabel methodSelectLbl;
    private JLabel modelPathLabel;
    private DataSetDialog dataSetDialog;

    public ConfigurationPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc, DataSetDialog dataSetDialog) {
        preferencesPanel = new JPanel();
        preferencesPanel.setLayout(gridBagLayout);
        preferencesPanel.setBackground(new Color(255, 255, 255));
        JLabel preferensLbl = new JLabel("Configuration: ");
        preferensLbl.setFont(new Font("Verdana", Font.BOLD, 14));
        preferensLbl.setForeground(new Color(77, 25, 0));
        methodLbl = getLabel("Method: ");
        this.dataSetDialog = dataSetDialog;
        modelSelectedLbl = getLabel("");
        JLabel datasetLbl = getLabel("Dataset: ");
        datasetLbl = getLabel("");

        jSeparator = new JSeparator();
        modelPathLabel = getLabel(" ");
        methodSelectLbl = getLabel("");

        jSeparator = new JSeparator();

        jSeparator = new JSeparator();

        preferencesPanel.setVisible(true);
        JLabel fileLbl = getLabel("");
        if (dataSetDialog.getJ() != null) {
            fileLbl = getLabel(dataSetDialog.getJ().getSelectedFile().toString());
        }
        plotLayout.addobjects(preferensLbl, preferencesPanel, gridBagLayout, gbc, 1, 1, 1, 0, 4, 1);

        plotLayout.addobjects(datasetLbl, preferencesPanel, gridBagLayout, gbc, 3, 4, 5, 0, 3, 1);
        plotLayout.addobjects(datasetLbl, preferencesPanel, gridBagLayout, gbc, 1, 4, 0, 0, 1, 1);

        // Model Select
        plotLayout.addobjects(getLabelShow("Method: "), preferencesPanel, gridBagLayout, gbc, 1, 2, 0, 0, 1, 1);
        plotLayout.addobjects(methodSelectLbl, preferencesPanel, gridBagLayout, gbc, 2, 2, 1, 0, 1, 1);

        // Model Path Select
        plotLayout.addobjects(getLabelShow("Model name: "), preferencesPanel, gridBagLayout, gbc, 1, 3, 0, 0, 1, 1);
        plotLayout.addobjects(modelPathLabel, preferencesPanel, gridBagLayout, gbc, 2, 3, 1, 0, 1, 1);


        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 7, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 8, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 9, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 10, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 11, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 12, 5, 0, 3, 1);
        plotLayout.addobjects(getLabelShow("\n"), preferencesPanel, gridBagLayout, gbc, 1, 13, 5, 0, 3, 1);
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

    void setMethod(String modelName) {
        methodSelectLbl.setText(modelName);
    }

    public JLabel getModelPathLabel() {
        return modelPathLabel;
    }

    public JLabel getDatasetLbl() {
        return datasetLbl;
    }

    public JPanel getPanel() {
        return preferencesPanel;
    }

}
