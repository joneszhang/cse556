package com.cse556.project;

public class Market {
	private double volume; //annual market volume
	private double annual_growth; //the total anual growth of volume
	private double stableRate;  //the natural increasing rate of volume
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getAnnual_growth() {
		return annual_growth;
	}
	public void setAnnual_growth(double annual_growth) {
		this.annual_growth = annual_growth;
	}
	public double getStableRate() {
		return stableRate;
	}
	public void setStableRate(double stableRate) {
		this.stableRate = stableRate;
	}
	
}
