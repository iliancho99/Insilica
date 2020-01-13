package com.insilicokdd.risk_mode;

import com.insilicokdd.gui.LayoutPlot;
import com.insilicokdd.operational_mode.OperationalModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RiskPanel {
    private JPanel riskPanel;
    private DataSetDialog chooseDataSetDialog;
    private OperationalModePanel operationalModePanel;
    private BottomPanel bottomPanel;
    private String query;

    private JLabel resultLbl;
    private JTextField motherAge;
    private JTextField patientAge;
    private JTextField sisterOnSetAge;
    private JTextField maternalAnutAge;
    private JTextField paternalAnutAge;
    private JTextField maternalGMAge;
    private JTextField paternalGMAge;
    private JTextField maternalHSAge;
    private JTextField paternalHSAge;

    public RiskPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                     JButton selectDataSetBtn, OperationalModePanel operationalModePanel) {

        riskPanel = new JPanel();
        riskPanel.setBackground(new Color(255, 255, 255));
        riskPanel.setLayout(gridBagLayout);
        this.operationalModePanel = operationalModePanel;

        this.query = query;
        chooseDataSetDialog = new DataSetDialog(gridBagLayout, plotLayout, gbc);

        bottomPanel = new BottomPanel(gridBagLayout, plotLayout, gbc, operationalModePanel, chooseDataSetDialog, this);

        selectDataSetBtn.setForeground(new Color(0, 0, 77));
        selectDataSetBtn.setBackground(new Color(255, 255, 255));

        motherAge = getJTextField();
        patientAge = getJTextField();
        sisterOnSetAge = getJTextField();
        maternalAnutAge = getJTextField();
        paternalAnutAge = getJTextField();
        maternalGMAge = getJTextField();
        paternalGMAge = getJTextField();
        maternalHSAge = getJTextField();
        paternalHSAge = getJTextField();

        resultLbl = getLabel("");

        plotLayout.addobjects(getJSeparator(), riskPanel, gridBagLayout, gbc, 1, 3, 5, 1, 5, 1);

        plotLayout.addobjects(getLabelShow("Patient age: " ), riskPanel, gridBagLayout, gbc, 1, 4, 0, 1, 1, 1);
        plotLayout.addobjects(patientAge, riskPanel, gridBagLayout, gbc, 2, 4, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Mother age: " ), riskPanel, gridBagLayout, gbc, 1, 5, 0, 1, 1, 1);
        plotLayout.addobjects(motherAge, riskPanel, gridBagLayout, gbc, 2, 5, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Sister age: " ), riskPanel, gridBagLayout, gbc, 1, 6, 0, 1, 1, 1);
        plotLayout.addobjects(sisterOnSetAge, riskPanel, gridBagLayout, gbc, 2, 6, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Maternal anut age: " ), riskPanel, gridBagLayout, gbc, 1, 7, 0, 1, 1, 1);
        plotLayout.addobjects(maternalAnutAge, riskPanel, gridBagLayout, gbc, 2, 7, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Paternal anut age: " ), riskPanel, gridBagLayout, gbc, 1, 8, 0, 1, 1, 1);
        plotLayout.addobjects(paternalAnutAge, riskPanel, gridBagLayout, gbc, 2, 8, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Maternal grandmother age: " ), riskPanel, gridBagLayout, gbc, 1, 9, 0, 1, 1, 1);
        plotLayout.addobjects(maternalGMAge, riskPanel, gridBagLayout, gbc, 2, 9, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Pathernal grandmother age: " ), riskPanel, gridBagLayout, gbc, 1, 10, 0, 1, 1, 1);
        plotLayout.addobjects(paternalGMAge, riskPanel, gridBagLayout, gbc, 2, 10, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Maternal half-sister age: "), riskPanel, gridBagLayout, gbc, 1, 11, 0, 1, 1, 1);
        plotLayout.addobjects(maternalHSAge, riskPanel, gridBagLayout, gbc, 2, 11, 1, 1, 1, 1);

        plotLayout.addobjects(getLabelShow("Paternal halfsister age: " ), riskPanel, gridBagLayout, gbc, 1, 12, 0, 1, 1, 1);
        plotLayout.addobjects(paternalHSAge, riskPanel, gridBagLayout, gbc, 2, 12, 1, 1, 1, 1);



        plotLayout.addobjects(getLabelShow("Risk :" ), riskPanel, gridBagLayout, gbc, 1, 13, 0, 0, 1, 1);
        plotLayout.addobjects(resultLbl, riskPanel, gridBagLayout, gbc, 2, 13, 3, 0, 2, 1);

        plotLayout.addobjects(getJSeparator(), riskPanel, gridBagLayout, gbc, 1, 14, 5, 1, 5, 1);

        plotLayout.addobjects(bottomPanel.getPanel(), riskPanel, gridBagLayout, gbc, 1, 15, 5, 0, 5, 1);


    }

    private JSeparator getJSeparator() {
        return new JSeparator(SwingConstants.HORIZONTAL);
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

    public String getQuery() {
        return buildQuery(patientAge, motherAge, sisterOnSetAge, maternalAnutAge, paternalAnutAge,
                        maternalGMAge, paternalGMAge, maternalHSAge, paternalHSAge);
    }

    private String buildQuery(JTextField patientAge, JTextField motherAge, JTextField daughterOnSetAge, JTextField maternalAnutAge,
                              JTextField paternalAnutAge, JTextField maternalGMAge, JTextField paternalGMAge,
                              JTextField maternalHSAge, JTextField paternalHSAge) {
        StringBuilder sb = new StringBuilder();
        if (!patientAge.getText().equals(""))
            sb.append(patientAge.getText()).append("/");
        else {
            sb.append("None").append("/");
        }
        if (!motherAge.getText().equals(""))
            sb.append(motherAge.getText()).append("/");
        else {
            sb.append("None").append("/");
        }
        if (!daughterOnSetAge.getText().equals(""))
            sb.append(daughterOnSetAge.getText()).append("/");
        else {
            sb.append("None").append("/");
        }
        if (!maternalAnutAge.getText().equals("")) {
            sb.append(maternalAnutAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        if (!paternalAnutAge.getText().equals("")) {
            sb.append(paternalAnutAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        if (!maternalGMAge.getText().equals("")) {
            sb.append(maternalGMAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        if (!paternalGMAge.getText().equals("")) {
            sb.append(paternalGMAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        if (!maternalHSAge.getText().equals("")) {
            sb.append(maternalHSAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        if (!paternalHSAge.getText().equals("")) {
            sb.append(paternalHSAge.getText()).append("/");
        } else {
            sb.append("None").append("/");
        }
        System.out.println(sb.toString() + " builder query");
        return sb.toString();
    }

//            plotLayout.addobjects(getLabelShow("Maternal anut age: " ), riskPanel, gridBagLayout, gbc, 1, 7, 0, 1, 1, 1);
//        plotLayout.addobjects(maternalAnutAge, riskPanel, gridBagLayout, gbc, 2, 7, 1, 1, 1, 1);
//
//        plotLayout.addobjects(getLabelShow("Paternal anut age: " ), riskPanel, gridBagLayout, gbc, 1, 8, 0, 1, 1, 1);
//        plotLayout.addobjects(paternalAnutAge, riskPanel, gridBagLayout, gbc, 2, 8, 1, 1, 1, 1);
//
//        plotLayout.addobjects(getLabelShow("Maternal grandmother age: " ), riskPanel, gridBagLayout, gbc, 1, 9, 0, 1, 1, 1);
//        plotLayout.addobjects(maternalGMAge, riskPanel, gridBagLayout, gbc, 2, 9, 1, 1, 1, 1);
//
//        plotLayout.addobjects(getLabelShow("Pathernal grandmother age: " ), riskPanel, gridBagLayout, gbc, 1, 10, 0, 1, 1, 1);
//        plotLayout.addobjects(paternalGMAge, riskPanel, gridBagLayout, gbc, 2, 10, 1, 1, 1, 1);
//
//        plotLayout.addobjects(getLabelShow("Maternal half-sister age: "), riskPanel, gridBagLayout, gbc, 1, 11, 0, 1, 1, 1);
//        plotLayout.addobjects(maternalHSAge, riskPanel, gridBagLayout, gbc, 2, 11, 1, 1, 1, 1);
//
//        plotLayout.addobjects(getLabelShow("Paternal halfsister age: " ), riskPanel, gridBagLayout, gbc, 1, 12, 0, 1, 1, 1);
//        plotLayout.addobjects(paternalHSAge, riskPanel, gridBagLayout, gbc, 2, 12, 1, 1, 1, 1);



    private JTextField getJTextField() {
        JTextField jField = new JTextField();
        jField.setSize(new Dimension(70, 50));
        return jField;
    }

    public JLabel getResultLbl() {
        return resultLbl;
    }


    public JPanel getRiskPanel() {
        return riskPanel;
    }

    public com.insilicokdd.operational_mode.SelectionPanel getSelectionPanel() {
        return operationalModePanel.getSelectionPanel();
    }


    public void ChooseDataSetDialogBtnAction(ActionEvent evt) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Set location of the window to center.
        chooseDataSetDialog.getDialog().setLocation(dimension.width/2-riskPanel.getSize().width/2, dimension.height/2-riskPanel.getSize().height/2);
        chooseDataSetDialog.getDialog().pack();
        chooseDataSetDialog.getDialog().setLocationRelativeTo(null);
        chooseDataSetDialog.getDialog().setVisible(true);
    }

    public JPanel getPanel() {
        return riskPanel;
    }
}
