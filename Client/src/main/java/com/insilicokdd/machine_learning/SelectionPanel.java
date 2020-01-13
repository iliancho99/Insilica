package com.insilicokdd.machine_learning;

import com.insilicokdd.buttons.ModelChoosedBtn;
import com.insilicokdd.data.ModelBluePrint;
import com.insilicokdd.data.ModelListing;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

class SelectionPanel {
    private final JList<ModelBluePrint> diagnosisList;
    private final JList<ModelBluePrint> predictiveList;
    private JPanel selectionPanel;
    private ConfigurationPanel configurationPanel;
    private BottomPanel bottomPanel;
    private JDialog modelPathDialog;
    private JTextField modelPathTextField;
    private ModelBluePrint selectedModel;

    /**
     * @param model The model from which the url will be build for the api call
     * @return url with parameters for the api call
     */
    private String buildUrl(ModelBluePrint model) {
        StringBuilder sb = new StringBuilder();
        sb.append(model.getMode()).append("/").append(modelPathTextField.getText()).append("/").append(model.getPath0()).append("/").append(model.getPath1());
        return sb.toString();
    }

    SelectionPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                   ConfigurationPanel preferencePanel, BottomPanel bottomPanel) {
        this.configurationPanel = preferencePanel;
        selectionPanel = new JPanel();
        selectionPanel.setBackground(new Color(255, 255, 255));
        selectionPanel.setLayout(gridBagLayout);
        this.bottomPanel = bottomPanel;
        modelPathDialog = new JDialog();
        modelPathDialog.setLayout(gridBagLayout);

        ModelListing modelListing = new ModelListing();
        DefaultListModel<ModelBluePrint> diagnosisAlgorithms = getDefaultListModel(modelListing.getDiagnosisList());
        DefaultListModel<ModelBluePrint> predictiveAlgorithms = getDefaultListModel(modelListing.getPredictiveLits());

        ModelChoosedBtn saveModelPath = new ModelChoosedBtn("OK", e -> {
            preferencePanel.getModelPathLabel().setText(modelPathTextField.getText());
            bottomPanel.setConfig(buildUrl(selectedModel));
            modelPathDialog.dispose();
        }, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (modelPathTextField.getText().equals("Name of the model")) {
                    modelPathTextField.setText(" ");
                }
            }
        });

        diagnosisList = getJList(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (diagnosisList.isSelectedIndex(diagnosisList.getSelectedIndex())) {
                    openModelPathDialog(modelPathDialog);
                    ModelBluePrint model = diagnosisList.getSelectedValue();
                    selectedModel = diagnosisList.getSelectedValue();
                    String url = "";
                    if (saveModelPath.getModel().isPressed())
                        url = buildUrl(model);

                    configurationPanel.setMethod(diagnosisList.getModel().getElementAt(diagnosisList.getSelectedIndex()).toString());
                    bottomPanel.setConfig(url);
                }
            }
        }, diagnosisAlgorithms);

        predictiveList = getJList(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openModelPathDialog(modelPathDialog);
                if (predictiveList.isSelectedIndex(predictiveList.getSelectedIndex())) {
                    openModelPathDialog(modelPathDialog);
                    ModelBluePrint model = predictiveList.getSelectedValue();
                    selectedModel = predictiveList.getSelectedValue();
                    String url = "";
                    if (saveModelPath.getModel().isPressed())
                        url = buildUrl(model);

                    configurationPanel.setMethod(predictiveList.getModel().getElementAt(predictiveList.getSelectedIndex()).toString());
                    bottomPanel.setConfig(url);
                }
            }
        }, predictiveAlgorithms);

        plotLayout.addobjects(getJTabbedPane(diagnosisList, predictiveList), selectionPanel, gridBagLayout, gbc, 1, 2, 1, 3, 1, 2);

        modelPathTextField = new JTextField("Name of the model");
        modelPathTextField.setForeground(new Color(0, 0, 77));
        modelPathTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (modelPathTextField.getText().equals("Name of the model")) {
                    modelPathTextField.setText("");
                }
            }
        });

        plotLayout.addobjects(modelPathTextField, modelPathDialog, gridBagLayout, gbc, 1, 1, 0, 0, 1, 1);
        plotLayout.addobjects(saveModelPath, modelPathDialog, gridBagLayout, gbc, 1, 2, 0, 0, 1, 1);

    }

    /**
     * @return new JList object
     * @Param DefaultModelList
     * @Param MouseListener
     */
    private JList<ModelBluePrint> getJList(MouseListener mouseListener, DefaultListModel model) {
        JList<ModelBluePrint> jlist = new JList<ModelBluePrint>(model);
        jlist.addMouseListener(mouseListener);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setBackground(new Color(255, 255, 255));
        jlist.setForeground(new Color(0, 0, 77));
        return jlist;
    }

    private boolean openModelPathDialog(JDialog modelPathDialog) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        modelPathDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // Set location of the window to center.
        modelPathDialog.setBackground(new Color(255, 255, 255));
        modelPathDialog.setTitle("Model name");
        modelPathDialog.setLocation(dimension.width / 2 - selectionPanel.getSize().width / 2, dimension.height / 2 - selectionPanel.getSize().height / 2);
        modelPathDialog.setResizable(false);
        modelPathDialog.pack();
        modelPathDialog.setLocationRelativeTo(null);
        modelPathDialog.setVisible(true);
        return true;
    }

    private DefaultListModel getDefaultListModel(List<ModelBluePrint> list) {
        DefaultListModel<ModelBluePrint> model = new DefaultListModel();
        for (ModelBluePrint val : list) {
            model.addElement(val);
        }
        return model;
    }

    private JTabbedPane getJTabbedPane(JList<ModelBluePrint> diagnosisList, JList<ModelBluePrint> predictiveList) {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(diagnosisList, "Diagnosis");
        jTabbedPane.add(predictiveList, "Predictive");
        jTabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
        jTabbedPane.setForegroundAt(0, new Color(0, 0, 77));
        jTabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
        jTabbedPane.setForegroundAt(1, new Color(0, 0, 77));
        return jTabbedPane;
    }

    JPanel getPanel() {
        return selectionPanel;
    }
}
