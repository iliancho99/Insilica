package com.insilicokdd.machine_learning;

import com.insilicokdd.buttons.MainPageBtn;
import com.insilicokdd.gui.LayoutPlot;
import com.insilicokdd.operational_mode.OperationalModePanel;
import com.insilicokdd.operational_mode.WorkflowPanel;
import com.insilicokdd.workers.SendDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BottomPanel {
    private JPanel bottomPanel;
    private ResultPanel resultPanel;
    private OperationalModePanel operationalModePanel;
    private String configChoosed;
    private DataSetDialog dataSetDialog;
    private ConfigurationPanel preferencePanel;
    private JButton executeBtn;
    private JButton clearBtn;

    BottomPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                ResultPanel resultPanel, OperationalModePanel operationalModePanel,
                DataSetDialog dataSetDialog, ConfigurationPanel preferencePanel) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(gridBagLayout);
        bottomPanel.setBackground(new Color(255, 255, 255));
        this.preferencePanel = preferencePanel;
        this.resultPanel = resultPanel;
        this.operationalModePanel = operationalModePanel;

        this.dataSetDialog = dataSetDialog;
        executeBtn = new JButton("Execute");

        executeBtn.setFont(new Font("Verdana", Font.BOLD, 13));
        executeBtn.setForeground(new Color(0, 0, 7));

        executeBtn.setBackground(new Color(255, 255, 255));
        executeBtn.addActionListener(this::executeBtnAction1);

        clearBtn = new JButton("Clear Result");

        clearBtn.setFont(new Font("Verdana", Font.BOLD, 13));
        clearBtn.setForeground(new Color(0, 0, 7));

        clearBtn.setBackground(new Color(255, 255, 255));
        clearBtn.addActionListener(this::clearBtnAction);


        plotLayout.addobjects(executeBtn, bottomPanel, gridBagLayout, gbc, 1, 2, 1, 5, 1, 1);
        plotLayout.addobjects(clearBtn, bottomPanel, gridBagLayout, gbc, 2, 2, 1, 5, 1, 1);

    }

    public void setConfig(String val) {
        configChoosed = val;
    }

    private void executeBtnAction1(ActionEvent evt) {
        File file = null;
        if (dataSetDialog.getJ() != null) {
            System.out.println(file);
            resultPanel.getLoadingLabel().setVisible(true);
            file = dataSetDialog.getJ().getSelectedFile();
            new SendDataSet(file, resultPanel, operationalModePanel.getSelectionPanel(),
                    configChoosed, preferencePanel, operationalModePanel.getWorkflowPanel(), executeBtn, clearBtn).execute();
            executeBtn.setEnabled(false);
            clearBtn.setEnabled(false);
        } else {
            JDialog selectFileWarning = getSelectFileWarningDialog();
            selectFileWarning.setVisible(true);
        }
    }

    private void clearBtnAction(ActionEvent evt) {
        preferencePanel.setMethod("");
        preferencePanel.getModelPathLabel().setText("");
        resultPanel.clearResult();
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
