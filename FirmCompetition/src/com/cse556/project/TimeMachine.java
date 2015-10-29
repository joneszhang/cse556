/*
 * This class is for the modeling control of this game.
*/
package com.cse556.project;

import java.util.ArrayList;
import java.util.List;

public class TimeMachine {
	private int stage = 0;
	private List<Firm> firms = null;
	public Market market;
	public ManuFactory factory;
	
	public TimeMachine(){
		
		/*Init the firms*/
		firms = new ArrayList<Firm>();
		firms.add(new Firm("jpelectronic", this));	
		firms.add(new Firm("tommyinnovation", this));
		firms.add(new Firm("auspyindustry", this));
		firms.add(new Firm("haisenit", this));
		/*Init the Market*/
		market = new Market(stage);
		factory = new ManuFactory(stage);
	}
	
	public void initGame(){
		firms.get(0).initiate(0, 6, 4, 2000);
		firms.get(1).initiate(0, 5, 5, 2000);
		firms.get(2).initiate(0, 2, 8, 2000);
		firms.get(3).initiate(0, 7, 3, 2000);
	}
	
	public void annualTrade(int stg){
		
	}
	
	public void competitionModeling(int stageNumber){
		for(int i = 0; i < stageNumber; ++i){
			stage = i;
			//make market naturally increase
			market.naturalIncrease(stage);
			for(int cnt = 0;cnt < firms.size(); ++cnt){
				firms.get(i).makePrepare(stage);//every firm make decision for this stage
			}
			for(int cnt = 0;cnt < firms.size(); ++cnt){
				firms.get(i).makeDecision();//every firm make decision for this stage
			}
			this.calculateData();//calculate the results of competition
			for(int cnt = 0;cnt < firms.size(); ++cnt){//record the results of this stage
				firms.get(cnt).stageRecord();
			}
			//end this stage
		}
	}
	
	
	public void calculateData(){
		double hti_E = getExpectation("hti");
		double exi_E = getExpectation("exi");
		double price_E = getExpectation("price");
		//calculate the brand competitiveness of each firm
		double hti = 0;
		double exi = 0;
		double price = 0;
		double brandComp = 0;
		double sumOfComp = 0;
		for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate brand competitiveness
			hti = firms.get(cnt).firmData().getIndex_Ht();
			exi = firms.get(cnt).firmData().getIndex_Ex();
			price = firms.get(cnt).firmData().getProd_price();
			brandComp = 0.2 * Math.pow((hti / hti_E) - 1, 2) 
					+ 0.5 * (exi / exi_E - 1) 
					+ 0.3 * (price / price_E - 1);
			firms.get(cnt).firmData().setBrandComp(brandComp);
			sumOfComp += brandComp;
		}
		//calculate saleVolume of the whole market
		double marketV = market.getCurrentVolume();
		double sumTechAd = 0;
		for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate the salevolume of each firm
			double ratio = firms.get(cnt).firmData().getBrandComp() / sumOfComp;
			//calculate the basic volume according to marketVolume last stage.
			double sellV = ratio * marketV;
			//add the effect of tech advance
			sellV += firms.get(cnt).firmData().getSellVol();
			sumTechAd += firms.get(cnt).firmData().getSellVol();
			firms.get(cnt).firmData().setSellVol(sellV);
			//get the final volume of the market this year.
			market.finalAnnualVolume(sumTechAd + market.getCurrentVolume());
		}
		
		
	}
	
	public double getExpectation(String index){//calculate the expectations of data
		double expectation = 0;
		if(index.equals("hti")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				expectation += firms.get(cnt).firmData().getIndex_Ht();
			}
			expectation /= firms.size();
		}
		else if(index.equals("exi")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				expectation += firms.get(cnt).firmData().getIndex_Ex();
			}
			expectation /= firms.size();
		}
		else if(index.equals("price")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				expectation += firms.get(cnt).firmData().getProd_price();
			}
			expectation /= firms.size();
		}
		return expectation;
		
	}
	public static void main(String[] args) {//main process function
		TimeMachine machine = new TimeMachine();
		machine.initGame();
		machine.competitionModeling(1);
	}
}
