package com.cse556.project;

public class ManuFactory {
	private double produceTech;  //the producing tech of the manufacuring companies.
	private int currentStage;
	private double baseCost;
	
	public ManuFactory(int stg){
		currentStage = stg;
		baseCost = 5000;
		produceTech = 6;
	}
	
	
	public double produceCost(double techIndex){//calculate the costs
		double cost = 0;
		cost  = baseCost + (techIndex - produceTech);
		return cost;
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
	
}
