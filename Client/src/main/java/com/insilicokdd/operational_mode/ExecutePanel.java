package com.insilicokdd.operational_mode;

import com.insilicokdd.gui.LayoutPlot;
import com.insilicokdd.workers.SendDataSet;
import com.insilicokdd.workers.SendDataSetOp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

class ExecutePanel {
    private JPanel executePanel;
    private WorkflowPanel workflowPanel;
    private BottomPanel bottomPanel;
    private DataSetDialog dataSetDialog;
    private JButton analyzeBtn;
    private JButton clearBtn;

    ExecutePanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                        BottomPanel bottomPanel, DataSetDialog dataSetDialog, WorkflowPanel workflowPanel) {
        executePanel = new JPanel();
        executePanel.setLayout(gridBagLayout);
        executePanel.setBackground(new Color(255, 255, 255));
        this.bottomPanel = bottomPanel;
        this.dataSetDialog = dataSetDialog;
        this.workflowPanel = workflowPanel;

        analyzeBtn = new JButton("Analyze");
        analyzeBtn.setFont(new Font("Verdana", Font.BOLD, 13));
        analyzeBtn.setForeground(new Color(0, 0, 7));

        analyzeBtn.setBackground(new Color(255, 255, 255));
        analyzeBtn.addActionListener(this::executeBtnAction);

        clearBtn = new JButton("Clear Workflow");
        clearBtn.setFont(new Font("Verdana", Font.BOLD, 13));
        clearBtn.setForeground(new Color(0, 0, 7));

        clearBtn.setBackground(new Color(255, 255, 255));
        clearBtn.addActionListener(this::clearBtnAction);


        plotLayout.addobjects(analyzeBtn, executePanel, gridBagLayout, gbc, 1, 7, 1, 1, 1, 1);
        plotLayout.addobjects(clearBtn, executePanel, gridBagLayout, gbc, 2, 7, 1, 1, 1, 1);
    }

    private void executeBtnAction(ActionEvent evt) {
        File file = null;
        if (dataSetDialog.getJ() != null) {
            System.out.println(file);
            // set loading gif
            file = dataSetDialog.getJ().getSelectedFile();
            analyzeBtn.setEnabled(false);
            clearBtn.setEnabled(false);
            new SendDataSetOp(bottomPanel, file, analyzeBtn, clearBtn).execute();
        } else {
            JDialog selectFileWarning = getSelectFileWarningDialog();
            selectFileWarning.setVisible(true);
        }
    }

    private void clearBtnAction(ActionEvent evt) {
        bottomPanel.getDiagnosis().setText("");
        bottomPanel.getOutcome().setText("");
        bottomPanel.setRisk("");
        workflowPanel.clearModelViewList();
    }

    private JDialog getSelectFileWarningDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        JDialog selectFileWarningDialog = new JDialog();
        selectFileWarningDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        selectFileWarningDialog.setBackground(new Color(255, 255, 255));
        selectFileWarningDialog.setTitle("Warning");
        selectFileWarningDialog.setLocation(dimension.width/2-executePanel.getSize().width/2, dimension.height/2-executePanel.getSize().height/2);
        selectFileWarningDialog.setResizable(false);
        selectFileWarningDialog.add(new JLabel("Please select a patient data"));
        selectFileWarningDialog.pack();
        selectFileWarningDialog.setLocationRelativeTo(null);
        return selectFileWarningDialog;
    }

    public JPanel getPanel() {
        return executePanel;
    }
}
