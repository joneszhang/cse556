package com.cse556.project;

import java.util.HashMap;
import java.util.Map;

public class Firm {
	Map<Integer, StageData> annual_Data = null; //list of annual data of this firm
	int currentStage = 0;//current stage of game
	
	public Firm(int stg){//empty initialization function
		annual_Data = new HashMap<Integer, StageData>();
	}
	
	public Firm(int stg, double i_tech, double i_ex, double i_invest){
		annual_Data = new HashMap<Integer, StageData>();
		StageData initData = new StageData();
		initData.setStage(stg);
		initData.setIndex_Ht(i_tech);
		initData.setIndex_Ex(i_ex);
		initData.setInvestment(i_invest);
		annual_Data.put(currentStage, initData);
		
	}
	
	public void stageInitiate(int stage){// set the data for every stage
		
	}
	
	public void calDatas(){ // calculate the datas of this year
		
	}
	
	public boolean stageRecord(){//record the result for every stage
		return true;
	}
	
}
