package com.insilicokdd.data;


public class Model {
	private static final int id = getNextId();
	private static int nextId;
	private String name;
	private String path;
	private double precision;
	private double accuracy;
	private double recall;
	private double aupr;
	private double f1_measure;
	private double auroc;
	private String type;
	private String param0;
	private String param1;
	private String mode;

	public static int getNextId() {
		return nextId++;
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
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getAupr() {
		return aupr;
	}
	public void setAupr(double aupr) {
		this.aupr = aupr;
	}
	
	public double getF1_measure() {
		return f1_measure;
	}
	public void setF1_measure(double f1_measure) {
		this.f1_measure = f1_measure;
	}
	public double getAuroc() {
		return auroc;
	}
	public void setAuroc(double auroc) {
		this.auroc = auroc;
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

	public static int getId() {
		return id;
	}

}
