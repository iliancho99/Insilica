package com.insilicokdd.data;

public class Diagnosis {
    private Double outcome;
    private String path1;
    private Double diagnosis;
    private String path2;
    private String type1;
    private String type2;

    public Double getOutcome() {
        return outcome;
    }

    public void setOutcome(Double outcome) {
        this.outcome = outcome;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public Double getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Double diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "outcome=" + outcome +
                ", path1='" + path1 + '\'' +
                ", diagnosis=" + diagnosis +
                ", path2='" + path2 + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                '}';
    }
}
