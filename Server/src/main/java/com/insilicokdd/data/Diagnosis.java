package com.insilicokdd.data;

public class Diagnosis {
	private Double diagnosis;
	private Double outcome;
	private String path;
	private String type;
	
	public Double getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(Double diagnosis) {
		this.diagnosis = diagnosis;
	}
	public Double getOutcome() {
		return outcome;
	}
	public void setOutcome(Double outcome) {
		this.outcome = outcome;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Diagnosis [diagnosis=" + diagnosis + ", outcome=" + outcome + ", path=" + path + ", type=" + type + "]";
	}
	
}
