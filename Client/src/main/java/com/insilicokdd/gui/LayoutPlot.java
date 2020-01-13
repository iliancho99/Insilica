package com.insilicokdd.gui;

import java.awt.*;

public class LayoutPlot {
    public void addobjects(Component componente, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, double weightx, double weighty, int gridwidth, int gridheigth){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheigth;

        layout.setConstraints(componente, gbc);
        yourcontainer.add(componente);
    }

}
