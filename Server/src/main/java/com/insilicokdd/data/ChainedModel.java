package com.insilicokdd.data;

public class ChainedModel {
	private double outcome;
	private String path1;
	private double diagnosis;
	private String path2;
	
	public double getOutcome() {
		return outcome;
	}
	public void setOutcome(double outcome) {
		this.outcome = outcome;
	}
	public String getPath1() {
		return path1;
	}
	public void setPath1(String path1) {
		this.path1 = path1;
	}
	public double getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(double diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getPath2() {
		return path2;
	}
	public void setPath2(String path2) {
		this.path2 = path2;
	}
	@Override
	public String toString() {
		return "ChainedModel [outcome=" + outcome + ", path1=" + path1 + ", diagnosis=" + diagnosis + ", path2=" + path2
				+ "]";
	}
	
}
