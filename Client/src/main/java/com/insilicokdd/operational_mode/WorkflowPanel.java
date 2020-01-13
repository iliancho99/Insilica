package com.insilicokdd.operational_mode;

import com.insilicokdd.data.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkflowPanel extends JPanel {
    private ModelView firstSelected;
    private ModelView secondSelected;
    private List<ModelView> modelViewsList;
    private Set<ModelView> modelViewSet;
    private BottomPanel bottomPanel;
    private Line2D.Double line;

    public WorkflowPanel(BottomPanel bottomPanel) {
        modelViewsList = new ArrayList<>();
        modelViewSet = new HashSet<>();
        this.bottomPanel = bottomPanel;
        setBackground(new Color(255, 255, 255));
        setLayout(null);
        setVisible(true);
    }

    @Override
    protected void paintChildren(Graphics g) {
        getSelection(modelViewsList, g);
        super.paintChildren(g);
    }

    private void getSelection(List<ModelView> modelViews, Graphics g) {
        if (modelViews.size() == 1) {
            String url = buildUrl(modelViews.get(0).getModel());
            bottomPanel.setAlgorithmChoosed(url);
        }
        if (modelViews.size() > 1) {
            for (int i = 0; i < modelViews.size(); i += 2) {
                ModelView modelViewOne = modelViews.get(i);
                ModelView modelViewTwo = modelViews.get(i + 1);

                Point p1 = new Point((modelViewOne.getLocation().x + modelViewOne.getWidth() / 2) - 80 , modelViewOne.getLocation().y + modelViewOne.getHeight() / 2);
                Point p2 = new Point((modelViewTwo.getLocation().x + modelViewTwo.getWidth() / 2) + 78, modelViewTwo.getLocation().y + modelViewTwo.getHeight() / 2);

                Graphics2D g2 = (Graphics2D) g;

                if (modelViewOne.isSelected() && modelViewTwo.isSelected()) {
                    String url = buildUrlTwo(modelViewOne.getModel(), modelViewTwo.getModel());
                    bottomPanel.setAlgorithmChoosed(url);
                } else if (modelViewOne.isSelected()) {
                    String url = buildUrl(modelViewOne.getModel());
                    bottomPanel.setAlgorithmChoosed(url);
                    firstSelected = modelViewOne;
                    secondSelected = modelViewTwo;
                } else if (modelViewTwo.isSelected()) {
                    String url = buildUrl(modelViewTwo.getModel());
                    bottomPanel.setAlgorithmChoosed(url);
                    firstSelected = modelViewTwo;
                    secondSelected = modelViewOne;
                }

                g2.setStroke(new BasicStroke(1));
                g2.setColor(new Color(169, 169, 169));

                if (modelViewOne == firstSelected && modelViewTwo == secondSelected && (modelViewOne.isSelected() && modelViewTwo.isSelected())) {
                    line = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
                    drawArrowHead((Graphics2D) g, getArrowHeadPolygon(), line);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                else if (modelViewOne == secondSelected && modelViewTwo == firstSelected && (modelViewOne.isSelected() && modelViewTwo.isSelected())) {
                    line = new Line2D.Double(p2.x, p2.y, p1.x, p1.y);
                    drawArrowHead((Graphics2D) g, getArrowHeadPolygon(), line);
                    g2.drawLine(p2.x, p2.y, p1.x, p1.y);
                }

            }
        }

    }

    private Polygon getArrowHeadPolygon() {
        Polygon arrowHead = new Polygon();
        arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);
        return arrowHead;
    }

    private void drawArrowHead(Graphics2D g2d, Polygon arrowHead, Line2D.Double line) {
        if (line == null) return;
        AffineTransform tx = new AffineTransform();
        tx.setToIdentity();
        double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle - Math.PI / 2d));

        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(tx);
        g.fill(arrowHead);
        g.dispose();
    }


    private String buildUrlTwo(Model model1, Model model2) {
        StringBuilder sb = new StringBuilder();
        String param0 = "";
        String param1 = "";
        String savePath = "";
        String savePath1 = "";

        if (model1.getType().equals("Diagnostic") && model2.getType().equals("Predictive")) {
            param0 = model1.getParam0();
            param1 = model2.getParam1();
        } else if (model1.getType().equals("Predictive") && model2.getType().equals("Diagnostic")) {
            param0 = model2.getParam0();
            param1 = model1.getParam1();
        }
        if (model1.getType().equals("Diagnostic") && model2.getType().equals("Predictive")) {
            sb.append(model1.getMode()).append("/").append(model1.getSavedPathModel())
                    .append("/").append(model2.getSavedPathModel()).append("/").append(param0).append("/").append(param1);
        } else if (model1.getType().equals("Predictive") && model2.getType().equals("Diagnostic")) {
            sb.append(model1.getMode()).append("/").append(model2.getSavedPathModel())
                    .append("/").append(model1.getSavedPathModel()).append("/").append(param0).append("/").append(param1);
        }
        System.out.println(sb.toString() + " URL in 2 selections");
        return sb.toString();
    }

    private String buildUrl(Model model) {
        StringBuilder sb = new StringBuilder();
        if (model.getType().equals("Predictive")) {
            sb.append(model.getMode()).append("/").append("0").append("/").append(model.getSavedPathModel())
                    .append("/").append(model.getParam0()).append("/").append(model.getParam1());
        } else if (model.getType().equals("Diagnostic")){
            sb.append(model.getMode()).append("/").append(model.getSavedPathModel()).append("/").append("0").append("/")
                    .append(model.getParam0()).append("/").append(model.getParam1());
        }

        System.out.println(sb + " URL");
        return sb.toString();
    }

    List<ModelView> getModelViewsList(Set<ModelView> hs) {
        modelViewsList = new ArrayList<>(hs);
        return modelViewsList;
    }

    void clearModelViewList() {
        modelViewsList.clear();
        modelViewSet.clear();
        this.removeAll();
        this.repaint();
        this.updateUI();
    }

    Set<ModelView> getModelViewSet() {
        return modelViewSet;
    }

}
