package com.insilicokdd.gui;

import com.insilicokdd.buttons.MainPageBtn;
import com.insilicokdd.buttons.MainPageBtnSub;
import com.insilicokdd.machine_learning.MachineLearningPanel;
import com.insilicokdd.operational_mode.OperationalModePanel;
import com.insilicokdd.risk_mode.RiskPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;


public class GUI extends JFrame implements Runnable {
    private OperationalModePanel operationalModePanel;
    private MachineLearningPanel machineLearningPanel;
    private RiskPanel riskPanel;

    GUI() {

    }


    private JFrame getJframeApp(JPanel jPanelApp) {
        JFrame jframe = new JFrame();
        jframe = new JFrame();
        jframe.setTitle(" ");
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.getContentPane().add(jPanelApp);
        return jframe;
    }

    private JPanel getjPanelApp(GridBagLayout gridBagLayout) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(gridBagLayout);
        jPanel.setBackground(new Color(255, 255, 255));
        return jPanel;
    }

    private GridBagConstraints getGridBagConstrains() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void operationalModeBtnAction(ActionEvent evt) {
        machineLearningPanel.getPanel().setVisible(true);
        operationalModePanel.getPanel().setVisible(false);
        riskPanel.getPanel().setVisible(false);
    }

    private void machineLearningBtnAction(ActionEvent evt) {
        operationalModePanel.getPanel().setVisible(true);
        machineLearningPanel.getPanel().setVisible(false);
        riskPanel.getPanel().setVisible(false);
    }

    private void riskBtnAction(ActionEvent evt) {
        operationalModePanel.getPanel().setVisible(false);
        machineLearningPanel.getPanel().setVisible(false);
        riskPanel.getPanel().setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new GUI());
    }

    @Override
    public void run() {
        LayoutPlot plotLayout = new LayoutPlot();
        GridBagLayout gridBagLayout = new GridBagLayout();

        JPanel jPanelApp = getjPanelApp(gridBagLayout);
        JFrame jFrameApp  = getJframeApp(jPanelApp);

        GridBagConstraints gbc = getGridBagConstrains();

        operationalModePanel = new OperationalModePanel(gridBagLayout, plotLayout, gbc);

        machineLearningPanel = new MachineLearningPanel(gridBagLayout, plotLayout, gbc,
                new MainPageBtnSub("Choose dataset", evt1 ->
                        machineLearningPanel.ChooseDataSetDialogBtnAction(evt1)), operationalModePanel);

        riskPanel = new RiskPanel(gridBagLayout, plotLayout, gbc, new MainPageBtnSub("Choose dataset", evt ->
                riskPanel.ChooseDataSetDialogBtnAction(evt)), operationalModePanel);

        // Set location of the window to center.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrameApp.setLocation(dimension.width / 2 - jFrameApp .getSize().width / 2, dimension.height / 2 - jFrameApp .getSize().height / 2);

        try {
            MainPageBtn machineLearningModeBtn = new MainPageBtn("Buttons/ML_LAST.png",
                    "Machine Learning", this::operationalModeBtnAction,90 , 70);


            MainPageBtn operationalModeBtn = new MainPageBtn("Buttons/bio3.png",
                    "Operat" +
                            "ional Mode", this::machineLearningBtnAction, 70, 70);

            MainPageBtn riskBtn = new MainPageBtn("Buttons/risk_icon.png",
                    "Risk Evaluation", this::riskBtnAction,  70, 70);

            MainPageBtn runBtn = new MainPageBtn(
                    "Buttons/dna_icon.png",
                    "Bio Tools", 70 , 70);

            // Main Pannel
            plotLayout.addobjects(riskBtn, jPanelApp, gridBagLayout, gbc, 4, 1, 2, 0, 1, 1);

            plotLayout.addobjects(operationalModeBtn, jPanelApp, gridBagLayout, gbc, 3, 1, 2, 0, 1, 1);

            plotLayout.addobjects(machineLearningModeBtn, jPanelApp, gridBagLayout, gbc, 2, 1, 2, 0, 1, 1);

            plotLayout.addobjects(runBtn, jPanelApp, gridBagLayout, gbc, 1, 1, 2, 0, 1, 1);

            plotLayout.addobjects(machineLearningPanel.getPanel(), jPanelApp, gridBagLayout, gbc, 1, 3, 1, 1, 5, 1);
            plotLayout.addobjects(operationalModePanel.getPanel(), jPanelApp, gridBagLayout, gbc, 1, 3, 1, 1, 5, 1);
            plotLayout.addobjects(riskPanel.getPanel(), jPanelApp, gridBagLayout, gbc, 1, 3, 1, 1, 5, 1);

            machineLearningPanel.getPanel().setVisible(true);
            operationalModePanel.getPanel().setVisible(false);
            riskPanel.getPanel().setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

        jFrameApp.setResizable(false);
        jFrameApp.pack();
        jFrameApp.setLocationRelativeTo(null);
        jFrameApp.setVisible(true);
    }
}
