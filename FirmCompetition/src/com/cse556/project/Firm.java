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
		StageData initData = new StageData(firmName);
		initData.setStage(stg);
		initData.setIndex_Ht(i_tech);
		initData.setIndex_Ex(i_ex);
		initData.setInvestment(i_invest);
		annual_Data.put(currentStage, initData);
		this.currentData = initData;
	}
	
	public void makePrepare(int stg){
		
		if(0 == currentStage)//for the beginning, no preparation need to be done.
			return;
		//set the investment amount
		currentData.setInvestment(annual_Data.get(this.currentStage - 1).getProfit());
		currentData = new StageData(firmName);//set the data for new stage
		this.currentStage = stg;
		currentData.setStage(this.currentStage);//set the stage of data
		//calculate the progress in tech index and the current index of Tech index
		double ti = annual_Data.get(currentStage - 1).getTechAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ht();
		currentData.setIndex_Ht(ti);
		//calculate experience index
		double ex = annual_Data.get(currentStage - 1).getExAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ht();
		currentData.setIndex_Ht(ex);
		
	}
	
	public void makeDecision(){// set the data for every stage
		
		currentData.setStage(currentStage);
		
		double profit_lastyear = currentData.getInvestment();
		//decide investment on high tech
		currentData.setInvest_Inno(profit_lastyear * 0.6);
		//decide investment on experience
		currentData.setInvest_Qual(profit_lastyear * 0.4);
		
		//decide the price of this stage.
		currentData.setProd_price(8000);
		
		//calculate the productive cost of this stage
		currentData.setProd_cost(informer.factory.produceCost(currentData.getIndex_Ht()));
		
		//calculate the increase in sell volume due to dech increase
		double addsell = (currentStage > 0)?informer.market.getCurrentVolume() * 0.005 
				* annual_Data.get(currentStage - 1).getTechAd() : 0;
		currentData.setSellVol(addsell);
	}
	
	
	public StageData firmData(){
		return this.currentData;
	}
	
	public boolean stageRecord(){//record the result for every stage
		double prof = (currentData.getProd_price() - currentData.getProd_cost())
				* currentData.getSellVol() - currentData.getInvestment();
		currentData.setSellVol(prof);
		
		//record the advance in tech
		double ti_add = this.currentData.getInvest_Inno()/this.currentData.getInvest_Inno();
		this.currentData.setTechAd(ti_add);
		//record the advance in experience
		double ex_add = this.currentData.getInvest_Qual() /this.currentData.getInvest_Qual() ;
		this.currentData.setExAd(ex_add);
		return true;
	}
	
}
