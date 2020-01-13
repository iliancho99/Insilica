package com.insilicokdd.data;


import com.insilicokdd.operational_mode.SelectionPanel;

import java.util.Objects;

public class Model {
    private int id;
    private static int nextId;
    private String name;
    private String path;
    private double precision;
    private double accuracy;
    private double recall;
    private double aupr;
    private double f1_measure;
    private double auroc;
    private String savedPathModel;
    private String type;
    private String param0;
    private String param1;
    private String mode;


    {
        boolean isPersisted = SelectionPanel.getPrefs().getBoolean("isPersisted", false);
        System.out.println("persistance " + isPersisted);
        if (!isPersisted) {
            nextId = 0;
        } else  {
            nextId = SelectionPanel.getPrefs().getInt("idCounter", 0);
        }
    }

    public Model(int id, String name, String path, double precision, double accuracy, double recall,
                 double aupr, double f1_measure, double auroc, String savedPathModel, String type, String param0,
                 String param1, String mode) {
        this.id = id;
        this.name = name;
        this.precision = precision;
        this.accuracy = accuracy;
        this.recall = recall;
        this.aupr = aupr;
        this.f1_measure = f1_measure;
        this.auroc = auroc;
        this.path = path;
        this.savedPathModel = savedPathModel;
        this.type = type;
        this.param0 = param0;
        this.param1 = param1;
        this.mode = mode;
    }

    public Model(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrecision() {
        return precision;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getRecall() {
        return recall;
    }

    public double getAupr() {
        return aupr;
    }

    public double getF1_measure() {
        return f1_measure;
    }

    public double getAuroc() {
        return auroc;
    }

    public String getPath() { return path; }

    public String getSavedPathModel() {
        return savedPathModel;
    }

    public void setSavedPathModel(String val) {
        savedPathModel = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String val) {
        type = val;
    }

    public String getParam0() {
        return param0;
    }

    public void setParam0(String param0) {
        this.param0 = param0;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override public String toString() {
        return name;
    }

    public void setId() {
        id = nextId++;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id == model.id &&
                Double.compare(model.precision, precision) == 0 &&
                Double.compare(model.accuracy, accuracy) == 0 &&
                Double.compare(model.recall, recall) == 0 &&
                Double.compare(model.aupr, aupr) == 0 &&
                Double.compare(model.f1_measure, f1_measure) == 0 &&
                Double.compare(model.auroc, auroc) == 0 &&
                Objects.equals(name, model.name) &&
                Objects.equals(path, model.path) &&
                Objects.equals(savedPathModel, model.savedPathModel) &&
                Objects.equals(type, model.type) &&
                Objects.equals(param0, model.param0) &&
                Objects.equals(param1, model.param1) &&
                Objects.equals(mode, model.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, precision, accuracy, recall, aupr, f1_measure, auroc, savedPathModel, type, param0, param1, mode);
    }
}
