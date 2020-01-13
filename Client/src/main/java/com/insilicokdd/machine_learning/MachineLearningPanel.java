package com.insilicokdd.machine_learning;

import com.insilicokdd.data.Model;
import com.insilicokdd.data.ModelListing;
import com.insilicokdd.gui.LayoutPlot;
import com.insilicokdd.operational_mode.OperationalModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MachineLearningPanel {
    private JPanel machineLearningPanel;
    private ResultPanel resultPanel;
    private BottomPanel bottomPanel;
    private DataSetDialog chooseDataSetDialog;
    private JSplitPane jSplitPane;
    private JScrollPane jScrollPane;
    private SelectionPanel selectionPanel;
    private ConfigurationPanel configurationPanel;
    private JSeparator jSeparator;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private ResultModelPanel resultModelPanel;

    public MachineLearningPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                         JButton selectDataSetBtn, OperationalModePanel operationalModePanel) {

        jScrollPane = new JScrollPane();

        resultPanel = new ResultPanel(gridBagLayout, plotLayout, gbc);
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, resultPanel.getPanel());
        jSplitPane.setEnabled(false);
        jSplitPane.setDividerSize(0);
        jSplitPane.setDividerLocation(200);

        chooseDataSetDialog = new DataSetDialog(gridBagLayout, plotLayout, gbc);

        configurationPanel = new ConfigurationPanel(gridBagLayout, plotLayout, gbc, chooseDataSetDialog);

        bottomPanel = new BottomPanel(gridBagLayout, plotLayout, gbc, resultPanel, operationalModePanel,
                chooseDataSetDialog, configurationPanel);

        selectionPanel = new SelectionPanel(gridBagLayout, plotLayout, gbc, configurationPanel, bottomPanel);

        jScrollPane.getViewport().add(selectionPanel.getPanel());
        jScrollPane.setVisible(true);

        jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator2 = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator3 = new JSeparator(SwingConstants.HORIZONTAL);

        machineLearningPanel = new JPanel();
        machineLearningPanel.setBackground(new Color(255, 255, 255));
        machineLearningPanel.setLayout(gridBagLayout);

        selectDataSetBtn.setForeground(new Color(0, 0, 77));
        selectDataSetBtn.setBackground(new Color(255, 255, 255));

        // Operational mode : Submenu : Upper
        plotLayout.addobjects(jSeparator2, machineLearningPanel, gridBagLayout, gbc, 1, 1, 0, 0, 5, 1);
        plotLayout.addobjects(selectDataSetBtn , machineLearningPanel, gridBagLayout, gbc, 1, 2, 1, 0, 1, 1);

        // Middle
        plotLayout.addobjects(jSplitPane, machineLearningPanel, gridBagLayout, gbc, 1, 3, 5, 0, 5, 1);

        // Bottom
        plotLayout.addobjects(jSeparator, machineLearningPanel, gridBagLayout, gbc, 1, 5, 5, 0, 5, 1);
        plotLayout.addobjects(configurationPanel.getPanel(), machineLearningPanel, gridBagLayout, gbc, 1, 6, 5, 0, 5, 1);
        plotLayout.addobjects(bottomPanel.getPanel(), machineLearningPanel, gridBagLayout, gbc, 1, 7, 5, 0, 5, 1);
        plotLayout.addobjects(jSeparator3, machineLearningPanel, gridBagLayout, gbc, 1, 8, 0, 0, 5, 1);

    }

    public void ChooseDataSetDialogBtnAction(ActionEvent evt) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Set location of the window to center.
        chooseDataSetDialog.getDialog().setLocation(dimension.width/2-machineLearningPanel.getSize().width/2, dimension.height/2-machineLearningPanel.getSize().height/2);
        chooseDataSetDialog.getDialog().pack();
        chooseDataSetDialog.getDialog().setLocationRelativeTo(null);
        chooseDataSetDialog.getDialog().setVisible(true);
    }

    public JPanel getPanel() {
        return machineLearningPanel;
    }
}
