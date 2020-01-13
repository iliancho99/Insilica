package com.insilicokdd.operational_mode;

import com.insilicokdd.data.Model;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class SelectionPanel {
    private JPanel selectionPanel;
    private JDialog resultDialog;
    private WorkflowPanel workflowPanel;
    private BottomPanel bottomPanel;
    private Vector<Model> predictiveModels;
    private Vector<Model> diagnosisModels;
    private JList<Model> diagnosisList;
    private JList<Model> predictiveList;
    private static Preferences prefs;
    private static int idCounter;
    private String risk;


    static {
        idCounter = 0;
    }

    public static Preferences getPrefs() {
        return prefs;
    }

    /**
     * Creating the selection panel for the App
     *
     * @param gridBagLayout
     * @param plotLayout
     * @param gbc
     * @param workflowPanel
     * @param bottomPanel
     */
    public SelectionPanel(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc,
                          WorkflowPanel workflowPanel, BottomPanel bottomPanel) {
        predictiveModels = new Vector<>();
        diagnosisModels = new Vector<>();

        selectionPanel = new JPanel();
        selectionPanel.setLayout(gridBagLayout);
        selectionPanel.setBackground(new Color(255, 255, 255));

        this.workflowPanel = workflowPanel;
        this.bottomPanel = bottomPanel;

        prefs = Preferences.userNodeForPackage(com.insilicokdd.operational_mode.SelectionPanel.class);
        try {
            prefs.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        loadPreferences();


        predictiveList = getJList(predictiveModels, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                getModelView(workflowPanel, predictiveModels, bottomPanel, predictiveList.getSelectedIndex(), risk);
            }
        });

        diagnosisList = getJList(diagnosisModels, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                getModelView(workflowPanel, diagnosisModels, bottomPanel, diagnosisList.getSelectedIndex(), risk);
            }
        });

        int size = prefs.getInt("diagnosticListSize", 0) + prefs.getInt("predictiveListSize", 0);
        for (int i = 0; i < size; i++)
            setPreferences(prefs, diagnosisModels, predictiveModels, diagnosisList);


        Runtime.getRuntime().addShutdownHook(new Thread(new PersistHook(prefs, diagnosisModels, predictiveModels)));


        plotLayout.addobjects(getJTabbedPane(diagnosisList, predictiveList), selectionPanel, gridBagLayout, gbc, 1, 2, 1, 0, 4, 1);

    }

    /**
     * This function creates a new JList
     * @param model Vector<Model>. This is the first parameter of the function.
     * This is a set of models that represent the models in the business logic.
     * It takes a vector of models.
     * @return JList<Model>.
     */
    private JList<Model> getJList(Vector model, MouseListener mouseListener) {
        JList<Model> jlist = new JList<Model>(model);
        jlist.addMouseListener(mouseListener);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setBackground(Color.WHITE);
        jlist.setForeground(new Color(0, 0, 77));
        jlist.setPreferredSize(new Dimension(0, 180));
        return jlist;
    }

    /**
     *
     * @param diagnosisList JList<Model>. This is the first parameter of the function.
     * A set that represents the Diagnostic models of the business logic.
     * @param predictiveList JList<Model>. This is the second parameter of the function.
     * A set that represent the Predictive models of the business logic.
     * @return JTabbedPane.
     */
    private JTabbedPane getJTabbedPane(JList<Model> diagnosisList, JList<Model> predictiveList) {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(diagnosisList, "Diagnosis");
        jTabbedPane.add(predictiveList, "Predictive");
        jTabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
        jTabbedPane.setForegroundAt(0, new Color(0, 0, 77));
        jTabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
        jTabbedPane.setForegroundAt(1, new Color(0, 0, 77));
        return jTabbedPane;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    /**
     * This function creates a new ModelView and display it in the workflowPanel.
     *
     * @param workflowPanel This is the first parameter for the function.
     * It takes a workflowPanel object com.insilicokdd.operational_mode.workflowPanel
     * @param models This parameter is the second for the function.
     * It takes a set of trained models.
     * @param bottomPanel This is the third parameter for the function.
     * It takes BottomPanel object : com.insilico.kdd.operational_mode.SelectionPanel.class
     * @param index  This is the fourth parameter for the function.
     * The index is the current selected index from the JList.
     */
    private void getModelView(WorkflowPanel workflowPanel, List<Model> models, BottomPanel bottomPanel, int index, String risk) {
        Set<ModelView> modelViewSet = workflowPanel.getModelViewSet();
        ModelView modelView = new ModelView(workflowPanel, models.get(index), bottomPanel, risk);

        if (modelViewSet.contains(modelView))
            return;

        if (modelViewSet.size() >= 2) {
            modelViewSet.clear();
        }

        workflowPanel.getModelViewSet().add(modelView);
        workflowPanel.getModelViewsList(workflowPanel.getModelViewSet());
        workflowPanel.add(modelView);
        workflowPanel.revalidate();
        workflowPanel.repaint();
    }

    /**
     * This function load the user preferences from a xml file.
     */
    private void loadPreferences() {
        InputStream is = null;
        Preferences preferences = Preferences.userNodeForPackage(com.insilicokdd.operational_mode.SelectionPanel.class);
        Preferences.userNodeForPackage(com.insilicokdd.operational_mode.SelectionPanel.class);
        try {
            File f = new File("Preferences.xml");
            if (!f.exists()) return;
            is = new BufferedInputStream(new FileInputStream("Preferences.xml"));
//            String ans = makeXMLFile("Preferences.xml");
//            System.out.println(ans + " ans");
            Preferences.importPreferences(is);
        } catch (IOException | InvalidPreferencesFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function loads the user preferences.
     *
     * @param prefs Preferences object. This is the first parameter of the function.
     * @param diagnosisModels  Vector<Model>. This is the first parameter of the function.
     *  This is a set of  models that represent the diagnosis models in the business logic.
     * @param predictiveModels Vector<Model>. This is the first parameter of the function.
     *  This is a set of models that represent the predictive models in the business logic.
     * @param jList JList<Model>. This is the fourth parameter of the function. It is a set
     * of models that represent the models in the business logic.
     */
    private void setPreferences(Preferences prefs, Vector<Model> diagnosisModels,
                                Vector<Model> predictiveModels, JList<Model> jList) {
        int id = prefs.getInt("id_" + idCounter++, 0);
        Model model = new Model(
                prefs.getInt("id_" + idCounter, 0),
                prefs.get("name_" + id, ""),
                prefs.get("path_" + id, ""),
                prefs.getDouble("precision_" + id, 0.0),
                prefs.getDouble("accuracy_" + id, 0.0),
                prefs.getDouble("recall_" + id, 0.0),
                prefs.getDouble("aupr_" + id, 0.0),
                prefs.getDouble("f1_measure_" + id, 0.0),
                prefs.getDouble("auroc_" + id, 0.0),
                prefs.get("savedPathModel_" + id, ""),
                prefs.get("type_" + id, ""),
                prefs.get("param0_" + id, ""),
                prefs.get("param1_" + id, ""),
                prefs.get("mode_" + id, ""));

        if (model.getType().equals("Diagnostic")) diagnosisModels.add(model);
        else predictiveModels.add(model);
        jList.updateUI();
    }


    /**
     * A Shutdown hook that persist the state of the SelectionPanel.
     * The DefaultModelList's (predictiveList, diagnosisList) and List<Model> are persisted.
     * The functions persistPanelState and persistModels are called from this annonymous method.
     * OutputStream writes file to "prefs.xml" on the file system. This file contains
     * the last state of the app.
     */
    private static class PersistHook implements Runnable {
        private final Preferences prefs;
        private final Vector<Model> diagnosisModelList;
        private final Vector<Model> predictiveModelList;

        PersistHook(Preferences prefs, Vector<Model> diagnosisModelList,
                    Vector<Model> predictiveModelList) {
            this.prefs = prefs;
            this.diagnosisModelList = diagnosisModelList;
            this.predictiveModelList = predictiveModelList;
        }

        private void persist(Preferences prefs, Vector<Model> models) {
            for (int i = 0; i < models.size(); i++) {
                Model model = models.get(i);
                int id = model.getId();
                prefs.putInt("id_" + id, id);
                prefs.put("name_" + id, model.getName());
                prefs.put("path_" + id, model.getPath());
                prefs.putDouble("precision_" + id, model.getPrecision());
                prefs.putDouble("accuracy_" + id, model.getAccuracy());
                prefs.putDouble("recall_" + id, model.getRecall());
                prefs.putDouble("aupr_" + id, model.getAupr());
                prefs.putDouble("f1_measure_" + id, model.getF1_measure());
                prefs.putDouble("auroc_" + id, model.getAuroc());
                if (model.getSavedPathModel() != null) {
                    prefs.put("savedPathModel_" + id, model.getSavedPathModel());
                    System.out.println("Saved path is null");
                }
                prefs.put("type_" + id, model.getType());
                prefs.put("param0_" + id, model.getParam0());
                prefs.put("param1_" + id, model.getParam1());
                prefs.put("mode_" + id, model.getMode());
            }
        }

        /**
         * This function persist the current state of the app by saving the
         * user preferences in a .xml file named "Preferences.xml"
         */
        private void persistState() {
            int size = diagnosisModelList.size() + predictiveModelList.size();
            prefs.putInt("idCounter", size);
            prefs.putInt("diagnosticListSize", diagnosisModelList.size());
            persist(prefs, diagnosisModelList);
            prefs.putInt("predictiveListSize", predictiveModelList.size());
            persist(prefs, predictiveModelList);
            if (!prefs.getBoolean("isPersisted", false)) {
                prefs.putBoolean("isPersisted", true);
            }
            try {
                OutputStream output = new FileOutputStream("Preferences.xml");
                prefs.exportSubtree(output);
                output.close();
            } catch (IOException | BackingStoreException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            persistState();
        }

    }

    public Vector<Model> getPredictiveModels() {
        return predictiveModels;
    }

    public Vector<Model> getDiagnosisModels() {
        return diagnosisModels;
    }

    public JList<Model> getDiagnosisJList() {
        return diagnosisList;
    }

    public JList<Model> getPredictiveList() {
        return predictiveList;
    }

    public JPanel getPanel() {
        return selectionPanel;
    }

    public JDialog getResultDialog() { return resultDialog; }
}
