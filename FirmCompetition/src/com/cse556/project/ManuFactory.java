package com.cse556.project;

public class ManuFactory {
	private double produceTech;  //the producing tech of the manufacuring companies.
	private int currentStage;
	
	public ManuFactory(int stg){
		currentStage = stg;
	}
	
	
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
