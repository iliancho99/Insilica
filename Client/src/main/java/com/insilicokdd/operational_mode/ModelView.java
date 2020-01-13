package com.insilicokdd.operational_mode;

import com.insilicokdd.data.Model;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class ModelView extends JPanel {
    private GridBagLayout gridBagLayout;
    private LayoutPlot plotLayout;
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private int deltaX;
    private boolean selected;
    private int deltaY;

    private BottomPanel bottomPanel;
    private Model model;

    ModelView(WorkflowPanel workflowPanel, Model model, BottomPanel bottomPanel, String riskVal) {
        setBounds(0, 0, 160, 140);
        setBackground(new Color(133, 255, 255, 11));
        setBorder(new TitledBorder("Model"));
        setOpaque(true);
        plotLayout = new LayoutPlot();
        gridBagLayout = new GridBagLayout();
        this.bottomPanel = bottomPanel;
        this.model = model;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        NumberFormat formatter = new DecimalFormat("#0.000");
        setLayout(gridBagLayout);

        JLabel nameLbl = getLabel(model.getName());
        JLabel accuracyLbl = getLabel(String.valueOf(formatter.format(model.getAccuracy())));
        JLabel recallLbl = getLabel(String.valueOf(formatter.format(model.getRecall())));
        JLabel f1MeasureLbl = getLabel(String.valueOf(formatter.format(model.getF1_measure())));
        JLabel auroc = getLabel(String.valueOf(formatter.format(model.getAuroc())));
        JLabel aupr = getLabel(String.valueOf(formatter.format(model.getAupr())));
        JLabel savedModelPath = getLabel(model.getSavedPathModel());


        plotLayout.addobjects(nameLbl, this, gridBagLayout, gbc, 1, 1, 0, 0, 4, 1);
        plotLayout.addobjects(new JSeparator(), this, gridBagLayout, gbc, 1, 2, 0, 0, 4, 1);

        plotLayout.addobjects(getLabel("Accuracy: "), this, gridBagLayout, gbc, 1, 3, 0, 0, 1, 1);
        plotLayout.addobjects(accuracyLbl, this, gridBagLayout, gbc, 2, 3, 0, 0, 1, 1);

        plotLayout.addobjects(getLabel("Recall: "), this, gridBagLayout, gbc, 1, 4, 0, 0, 1, 1);
        plotLayout.addobjects(recallLbl, this, gridBagLayout, gbc, 2, 4, 0, 0, 1, 1);

        plotLayout.addobjects(getLabel("F1_Measure: "), this, gridBagLayout, gbc, 1, 5, 0, 0, 1, 1);
        plotLayout.addobjects(f1MeasureLbl, this, gridBagLayout, gbc, 2, 5, 0, 0, 1, 1);

        plotLayout.addobjects(getLabel("Auroc: "), this, gridBagLayout, gbc, 1, 6, 0, 0, 1, 1);
        plotLayout.addobjects(auroc, this, gridBagLayout, gbc, 2, 6, 0, 0, 1, 1);


        plotLayout.addobjects(getLabel("Aupr: "), this, gridBagLayout, gbc, 1, 7, 0, 0, 1, 1);
        plotLayout.addobjects(aupr, this, gridBagLayout, gbc, 2, 7, 0, 0, 1, 1);

        plotLayout.addobjects(getLabel("Model path: "), this, gridBagLayout, gbc, 1, 8, 0, 0, 1, 1);
        plotLayout.addobjects(savedModelPath, this, gridBagLayout, gbc, 2, 8, 0, 1, 1, 1);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                screenX = e.getXOnScreen();
                screenY = e.getYOnScreen();

                if (e.getButton() == MouseEvent.BUTTON3 && !selected) {
                    setBackground(new Color(10, 255, 255, 11));
                    selected = true;
                    repaint();
                    workflowPanel.repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3 && selected) {
                    setBackground(new Color(133, 255, 255, 11));
                    selected = false;
                    repaint();
                    workflowPanel.repaint();
                }

                myX = getX();
                myY = getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                deltaX = e.getXOnScreen() - screenX;
                deltaY = e.getYOnScreen() - screenY;

                setLocation(myX + deltaX, myY + deltaY);
                e.getComponent().repaint();
                workflowPanel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }

        });
    }

    private JLabel getLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 12));
        label.setForeground(new Color(77, 25, 0));
        return label;
    }

    private JLabel getLabelShow(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        label.setForeground(new Color(0, 0, 77));
        return label;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }



    boolean isSelected() {
        return selected;
    }

    public JPanel getPanel() {
        return this;
    }

    boolean ifSelected() {
        return selected;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelView modelView = (ModelView) o;
        return model.equals(modelView.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }
}