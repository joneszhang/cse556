package com.cse556.project;

public class ManuFactory {
	private double produceTech;  //the producing tech of the manufacuring companies.
	private int currentStage;
	private double baseCost;
	//constant parameters of formulas
	private double k = 20000;
	private double d = 20;
	private double p = 100;
	private double c = 1.5;
	private double a = 8;
	private double b = 0.4;
	private double m = 1;
	private double e = 500;

	public ManuFactory(int stg){
		currentStage = stg;
		baseCost = 5000;
		produceTech = 6;
	}
	
	
	public double produceCost(double techIndex){//calculate the costs
		double exCost = 0;
		exCost = m * techIndex / this.produceTech;
		exCost = Math.pow(exCost, 2);
		double cost = this.baseCost + exCost;
		return cost;
	}
	
	public void stageRenew(int stg){
		currentStage = stg;
		this.produceTech = p / (1+ Math.pow(c, (a - b*currentStage)));
		this.baseCost = k / (this.produceTech + d) + e;
	}
	
	
	/*getters and setters*/
	public int getCurrentStage() {
		return currentStage;
	}


	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}


	public double getProduceTech() {
		return produceTech;
	}

	public void setProduceTech(double produceTech) {
		this.produceTech = produceTech;
	}

	public double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(double baseCost) {
		this.baseCost = baseCost;
	}
	
}
