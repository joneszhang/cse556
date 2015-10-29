package com.cse556.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {
	private double basevolume; //annual market volume
	private double stableRate;  //the natural increasing rate of volume
	private int currentStage; //current stage
	private double currentVolume; //volume that is under processing.
	private Map<Integer, Double> annualVolume = null;
	
	public Market(int stg){
		currentStage = stg;
		stableRate = 1.15;
		basevolume = 50000000;
		annualVolume = new HashMap<Integer, Double>();
		annualVolume.put(0, basevolume);
	}
	
	public double getAnuualV(int stage){
		return annualVolume.get(stage);
	}
	
	public double getCurrentVolume(){
		return this.currentVolume;
	}
	
	public void naturalIncrease(int stg){
		currentStage = stg;
		currentVolume = basevolume * Math.pow(stableRate, currentStage);
	}
	
	public void finalAnnualVolume(double v){
		currentVolume = v;
		annualVolume.put(this.currentStage, currentVolume);
	}
}
