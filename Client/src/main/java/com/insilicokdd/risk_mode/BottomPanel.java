package com.insilicokdd.risk_mode;



import com.insilicokdd.gui.LayoutPlot;
import com.insilicokdd.operational_mode.OperationalModePanel;
import com.insilicokdd.workers.SendDataSetRisk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BottomPanel {
    private JPanel bottomPanel;
    private OperationalModePanel operationalModePanel;
    private String configChoosed;
    private DataSetDialog dataSetDialog;
    private RiskPanel riskPanel;
    private String query;

    private JButton executeBtn;

    BottomPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                OperationalModePanel operationalModePanel, DataSetDialog dataSetDialogRiskPanel, RiskPanel riskPanel) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(gridBagLayout);
        bottomPanel.setBackground(new Color(255, 255, 255));
        this.riskPanel = riskPanel;
        this.query = query;

        this.operationalModePanel = operationalModePanel;

        this.dataSetDialog = dataSetDialog;
        executeBtn = new JButton("Execute");

        executeBtn.setFont(new Font("Verdana", Font.BOLD, 13));
        executeBtn.setForeground(new Color(0, 0, 7));

        executeBtn.setBackground(new Color(255, 255, 255));
        executeBtn.addActionListener(this::executeBtnAction1);


        plotLayout.addobjects(executeBtn, bottomPanel, gridBagLayout, gbc, 1, 15, 1, 5, 1, 1);

    }

    public void setConfig(String val) {
        configChoosed = val;
    }

    private void executeBtnAction1(ActionEvent evt) {
            new SendDataSetRisk(riskPanel, operationalModePanel.getSelectionPanel(),
                    configChoosed, operationalModePanel.getWorkflowPanel(), executeBtn, operationalModePanel.getBottomPanel()).execute();
    }

    public JPanel getPanel() {
        return bottomPanel;
    }

    private JDialog getSelectFileWarningDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        JDialog selectFileWarningDialog = new JDialog();
        selectFileWarningDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // Set location of the window to center.
        selectFileWarningDialog.setBackground(new Color(255, 255, 255));
        selectFileWarningDialog.setTitle("Warning");
        selectFileWarningDialog.setLocation(dimension.width/2-bottomPanel.getSize().width/2, dimension.height/2-bottomPanel.getSize().height/2);
        selectFileWarningDialog.setResizable(false);
        selectFileWarningDialog.add(new JLabel("Please select a file"));
        selectFileWarningDialog.pack();
        selectFileWarningDialog.setLocationRelativeTo(null);
        return selectFileWarningDialog;
    }
}