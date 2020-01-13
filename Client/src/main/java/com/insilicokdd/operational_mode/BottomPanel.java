package com.insilicokdd.operational_mode;

import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;

public class BottomPanel {
    private JPanel bottomPanel;
    private JLabel firstModelLbl;
    private JLabel secondModelLbl;
    private JSeparator jSeparator;
    private JLabel diagnosis;
    private JLabel outcome;
    private JLabel risk;
    private String configChoosed;
    private ResultDialog resultDialog;
    private GridBagLayout gridBagLayout;
    private LayoutPlot plotLayout;
    private GridBagConstraints gbc;

    public BottomPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(gridBagLayout);
        bottomPanel.setBackground(new Color(255, 255, 255));
        JLabel firstModelShowLbl = getLabel("First Model: ");
        JLabel secondModelShowLbl = getLabel("Second Model: ");

        this.gridBagLayout = gridBagLayout;
        this.plotLayout = plotLayout;
        this.gbc = gbc;
        firstModelLbl = getLabel("");
        secondModelLbl = getLabel("KMeans");
        diagnosis = getLabelShow("");
        outcome = getLabelShow("");
        risk = getLabelShow("");



        plotLayout.addobjects(new JSeparator(), bottomPanel, gridBagLayout, gbc, 1, 1, 1, 0, 5, 1);

        plotLayout.addobjects(getLabel("Diagnosis: "), bottomPanel, gridBagLayout, gbc, 1, 4, 0, 1, 1, 1);
        plotLayout.addobjects(getLabel("Outcome: "), bottomPanel, gridBagLayout, gbc, 1, 5, 0, 1, 1, 1);
        plotLayout.addobjects(getLabel("Risk: "), bottomPanel, gridBagLayout, gbc, 1, 6, 0, 1, 1, 1);
        plotLayout.addobjects(outcome, bottomPanel, gridBagLayout, gbc, 2, 5, 0, 1, 1, 1);
        plotLayout.addobjects(diagnosis, bottomPanel, gridBagLayout, gbc, 2, 4, 5, 1, 3, 1);
        plotLayout.addobjects(risk, bottomPanel, gridBagLayout, gbc, 2, 6, 0, 1, 1, 1);

    }

    private JLabel getLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(0, 0, 77));
        return label;
    }


    private JLabel getLabelShow(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(77, 25, 0));
        return label;
    }

    void clearResult() {
        outcome.setText("");
        diagnosis.setText("");

    }

    public void setRisk(String riskVal) {
        risk.setText(riskVal);
    }

    private class ResultDialog {
        private JFrame resultDialog;
        private JLabel diagnosisLbl;
        private JLabel outcomeLbl;

        ResultDialog(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc, JPanel bottomPanel,
                     String diagnosis, String outcome) {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            resultDialog = new JFrame();
            resultDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resultDialog.setTitle("Result");
            resultDialog.setLayout(gridBagLayout);

            resultDialog.setLocation(dimension.width/2-bottomPanel.getSize().width/2,
                    dimension.height/2-bottomPanel.getSize().height/2);

            int dialogWidth = dimension.width / 8; // a quarter of the screen size
            int dialogHeight = dimension.height / 8;
            int dialogX = dimension.width / 2 - dialogWidth / 2; //position right in the middle of the screen
            int dialogY = dimension.height / 2 - dialogHeight / 2;

            resultDialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);

            resultDialog.setLocationRelativeTo(null);

            resultDialog.setVisible(true);

            diagnosisLbl = getLabelShow(diagnosis);
            outcomeLbl = getLabelShow(outcome);

            plotLayout.addobjects(getLabel("Diagnosis: "), resultDialog, gridBagLayout, gbc, 1, 1, 0, 0, 1, 1);
            plotLayout.addobjects(diagnosisLbl, resultDialog, gridBagLayout, gbc, 2, 1, 0, 0, 1, 1);
            plotLayout.addobjects(getLabel("Outcome: "), resultDialog, gridBagLayout, gbc, 1, 2, 0, 0, 1, 1);
            plotLayout.addobjects(outcomeLbl, resultDialog, gridBagLayout, gbc, 2, 2, 0, 0, 1, 1);

        }

        private JLabel getLabel(String labelText) {
            JLabel label = new JLabel(labelText);
            label.setFont(new Font("Verdana", Font.PLAIN, 15));
            label.setForeground(new Color(77, 25, 0));
            return label;
        }

        private JLabel getLabelShow(String labelText) {
            JLabel label = new JLabel(labelText);
            if (labelText.equals("Malignant"))
                label.setForeground(Color.RED);
            else
                label.setForeground(new Color(0, 102, 0));
            label.setFont(new Font("Verdana", Font.PLAIN, 15));
            return label;
        }

    }

    public void setAlgorithmChoosed(String val) {
        configChoosed = val;
    }

    public String getAlgorithmChoosed() {
        return configChoosed;
    }

    public JLabel getDiagnosis() { return diagnosis; }

    public JLabel getOutcome() { return outcome; }

    public JPanel getPanel() {
        return bottomPanel;
    }

    public void showResult(String diagnosis, String outcome) {
        ResultDialog resultDialog = new ResultDialog(gridBagLayout, plotLayout, gbc, bottomPanel, diagnosis, outcome);

    }


}
