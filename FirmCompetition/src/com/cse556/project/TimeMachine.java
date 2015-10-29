/*
 * This class is for the modeling control of this game.
*/
package com.cse556.project;

import java.util.HashMap;
import java.util.Map;

public class TimeMachine {
	private int stage = 0;
	private Map<String, Firm>firmList = null;
	private Market market;
	private ManuFactory factory;
	
	public TimeMachine(){
		
		/*Init the firms*/
		firmList = new HashMap<String, Firm>();
		firmList.put("MicroSoft", new Firm(stage));
		firmList.put("MicroSoft", new Firm(stage));
		firmList.put("MicroSoft", new Firm(stage));
		firmList.put("MicroSoft", new Firm(stage));
		/*Init the Market*/
		market = new Market(stage);
		factory = new ManuFactory(stage);
	}
	
	public void annualTrade(int stg){
		
	}
	
	public void competitionModeling(int stageNumber){
		
	}
	public static void main(String[] args) {//main process function
		
	}
}
