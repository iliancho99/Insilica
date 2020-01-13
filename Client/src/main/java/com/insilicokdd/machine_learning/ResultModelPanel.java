package com.insilicokdd.machine_learning;

import com.insilicokdd.buttons.MainPageBtn;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ResultModelPanel {
    private JPanel resultModelPanel;
    private JLabel featuresLbl;

   ResultModelPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc) {
        this.resultModelPanel = new JPanel();
        resultModelPanel.setLayout(gridBagLayout);
        resultModelPanel.setBackground(new Color(255, 255, 255));
        JLabel showFeaturesLbl = getLabel("Features: ");
        featuresLbl = new JLabel("\n");
        featuresLbl.setFont(new Font("Verdana", Font.PLAIN, 15));
       MainPageBtn logo = null;
       try {
           logo = new MainPageBtn(
                   "Images/logo.png",
                   "Insilico Kdd", 70, 70);
           logo.setPreferredSize(new Dimension(140, 90));
       } catch (IOException e) {
           e.printStackTrace();
       }

       plotLayout.addobjects(showFeaturesLbl, resultModelPanel, gridBagLayout, gbc, 1, 1, 1, 0, 4, 1);
       plotLayout.addobjects(featuresLbl, resultModelPanel, gridBagLayout, gbc, 2, 2, 1, 0, 1, 1);
       plotLayout.addobjects(new JLabel("\n"), resultModelPanel, gridBagLayout, gbc, 2, 3, 1, 0, 1, 1);
       plotLayout.addobjects(logo, resultModelPanel, gridBagLayout, gbc, 3, 1, 0, 0, 1, 1);

    }

    private JLabel getLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(0, 0, 77));
        return label;
    }

    public JPanel getPanel() {
        return resultModelPanel;
    }

    public JLabel getShowFeaturesLbl() { return featuresLbl; }
}
