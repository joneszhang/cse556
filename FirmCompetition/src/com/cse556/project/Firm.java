package com.cse556.project;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class Firm {
	Map<Integer, StageData> annual_Data = null; //list of annual data of this firm
	int currentStage = 0;//current stage of game
	private String firmName;
	private StageData currentData = null;
	private TimeMachine informer = null;
	
	public Firm(String name, TimeMachine machine){//empty initialization function
		annual_Data = new HashMap<Integer, StageData>();
		firmName = name;
		informer = machine;
	}
	
	public void initiate(int stg, double i_tech, double i_ex, double i_invest){
		annual_Data = new HashMap<Integer, StageData>();
		StageData initData = new StageData();
		initData.setStage(stg);
		initData.setIndex_Ht(i_tech);
		initData.setIndex_Ex(i_ex);
		initData.setInvestment(i_invest);
		annual_Data.put(currentStage, initData);
	}
	
	public void makePrepare(int stg){
		currentData = new StageData();//set the data for new stage
		this.currentStage = stg;
		currentData.setStage(this.currentStage);//set the stage of data
		
		if(0 == currentStage)//for the beginning, no preparation need to be done.
			return;
		//calculate the progress in tech index and the current index of Tech index
		double ti = annual_Data.get(currentStage - 1).getTechAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ht();
		currentData.setIndex_Ht(ti);
		//calculate experience index
		double ex = annual_Data.get(currentStage - 1).getExAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ht();
		currentData.setIndex_Ht(ex);
		
		//calculate the productive cost of this stage
		currentData.setProd_cost(informer.factory.produceCost(ti));
		
		//calculate the increase in sell volume due to dech increase
		double addsell = informer.market.getCurrentVolume() * 0.005 
				* annual_Data.get(currentStage - 1).getTechAd() ;
		currentData.setSellVol(addsell);
		
	}
	
	public void makeDecision(){// set the data for every stage
		
		if(0 == currentStage)
			return;
		currentData.setStage(currentStage);
		double profit_lastyear = annual_Data.get(this.currentStage - 1).getProfit();
		//decide investment on high tech
		currentData.setInvest_Inno(profit_lastyear * 0.6);
		//decide investment on experience
		currentData.setInvest_Qual(profit_lastyear * 0.4);
		
		//decide the price of this stage.
		currentData.setProd_price(8000);
		
	}
	
	
	public StageData firmData(){
		return this.currentData;
	}
	
	public boolean stageRecord(){//record the result for every stage
		return true;
	}
	
}
