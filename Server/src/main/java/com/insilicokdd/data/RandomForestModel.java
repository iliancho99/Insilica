package com.insilicokdd.data;

public class RandomForestModel {
	private String path;
	private String name;
	private double precision;
	private double recall;
	private double f1_measure;
	private double aupr;
	private double auroc;
	private double sensitivity;
	private double accuracy;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getF1_measure() {
		return f1_measure;
	}
	public void setF1_measure(double f1_measure) {
		this.f1_measure = f1_measure;
	}
	public double getAupr() {
		return aupr;
	}
	public void setAupr(double aupr) {
		this.aupr = aupr;
	}
	public double getAuroc() {
		return auroc;
	}
	public void setAuroc(double auroc) {
		this.auroc = auroc;
	}
	public double getSensitivity() {
		return sensitivity;
	}
	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	@Override
	public String toString() {
		return "RandomForestModel [path=" + path + ", name=" + name + ", precision=" + precision + ", recall=" + recall
				+ ", f1_measure=" + f1_measure + ", aupr=" + aupr + ", auroc=" + auroc + ", sensitivity=" + sensitivity
				+ ", accuracy=" + accuracy + "]";
	}
	
}
