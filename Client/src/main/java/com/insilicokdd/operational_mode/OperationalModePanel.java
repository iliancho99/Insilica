package com.insilicokdd.operational_mode;

import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;

public class OperationalModePanel {
    private JPanel operationalModePanel;
    private WorkflowPanel workflowPanel;
    private SelectionPanel selectionPanel;
    private ExecutePanel executePanel;
    private BottomPanel bottomPanel;
    private JSplitPane jSplitPane;
    private JScrollPane jScrollPane;
    private DataSetDialog dataSetDialog;

    public OperationalModePanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc) {
        operationalModePanel = new JPanel();

        jScrollPane = new JScrollPane();

        bottomPanel = new BottomPanel(gridBagLayout, plotLayout, gbc);

        workflowPanel = new WorkflowPanel(bottomPanel);
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, workflowPanel);

        dataSetDialog = new DataSetDialog(gridBagLayout, plotLayout, gbc);

        executePanel = new ExecutePanel(gridBagLayout, plotLayout, gbc, bottomPanel, dataSetDialog, workflowPanel);

        selectionPanel = new SelectionPanel(gridBagLayout, plotLayout, gbc, workflowPanel, bottomPanel);


        operationalModePanel.setBackground(new Color(255, 255, 255));
        operationalModePanel.setLayout(gridBagLayout);

        jSplitPane.setDividerSize(0);
        jSplitPane.setEnabled(false);

        jSplitPane.setDividerLocation(200);

        jScrollPane.getViewport().add(selectionPanel.getPanel());
        jScrollPane.setVisible(true);
        JButton selectDataSetBtn = new JButton("Choose patient data");

        selectDataSetBtn.setForeground(new Color(0, 0, 77));
        selectDataSetBtn.setBackground(new Color(255, 255, 255));


        selectDataSetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                chooseDataSetDialog(evt);
            }
        });

        JSeparator jSeparator2 = new JSeparator(SwingConstants.HORIZONTAL);
        plotLayout.addobjects(jSeparator2, operationalModePanel, gridBagLayout, gbc, 1, 1, 0, 0, 5, 1);
        plotLayout.addobjects(selectDataSetBtn, operationalModePanel, gridBagLayout, gbc, 1, 2, 0, 0, 1, 1);

        plotLayout.addobjects(jSplitPane, operationalModePanel, gridBagLayout, gbc, 1, 3, 5, 1, 1, 9);
        plotLayout.addobjects(bottomPanel.getPanel(), operationalModePanel, gridBagLayout, gbc, 1, 14, 5, 1, 1, 1);
        plotLayout.addobjects(executePanel.getPanel(), operationalModePanel, gridBagLayout, gbc, 1, 15, 5, 1, 1, 1);

    }

    public void chooseDataSetDialog(ActionEvent evt) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Set location of the window to center.
        dataSetDialog.getDialog().setLocation(dimension.width/2-operationalModePanel.getSize().width/2, dimension.height/2-operationalModePanel.getSize().height/2);
        dataSetDialog.getDialog().pack();
        dataSetDialog.getDialog().setLocationRelativeTo(null);
        dataSetDialog.getDialog().setVisible(true);
    }

    public WorkflowPanel getWorkflowPanel() {
        return workflowPanel;
    }

    public SelectionPanel getSelectionPanel() {
        return selectionPanel;
    }

    public BottomPanel getBottomPanel() { return bottomPanel; }

    public JPanel getPanel() {
        return operationalModePanel;
    }
}
