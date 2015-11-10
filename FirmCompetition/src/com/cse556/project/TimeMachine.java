/*
 * This class is for the modeling control of this game.
*/
package com.cse556.project;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.io.File.*;
import jxl.*;
import jxl.biff.DoubleHelper;
import jxl.write.*;

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
		/*Init the Market*/
		market = new Market(stage);
		factory = new ManuFactory(stage);
	}
	
	public void initGame(){
		firms.get(0).initiate(0, 2, 2, 600);
		firms.get(1).initiate(0, 2, 2, 600);
	}
	
	public void annualTrade(int stg){
		
	}
	
	public void competitionModeling(int stageNumber){
		for(int i = 0; i < stageNumber; ++i){
			stage = i;
			//make market naturally increase
			market.naturalIncrease(stage);
			factory.stageRenew(stage);
			for(int cnt = 0;cnt < firms.size(); ++cnt){
				firms.get(cnt).makePrepare(stage);//every firm make decision for this stage
			}
			firms.get(0).makeDecision(0.1, 0.9);
			firms.get(1).makeDecision(0.9, 0.1);
			this.calculateData();//calculate the results of competition
			for(int cnt = 0;cnt < firms.size(); ++cnt){//record the results of this stage
				firms.get(cnt).stageRecord();
				firms.get(cnt).printToFile();
			}
			//end this stage
		}
		for(int cnt = 0;cnt < firms.size(); ++cnt){//record the results of this stage
			firms.get(cnt).printToFile();
		}
		
	}
	
	
	public void calculateData(){
		double hti_S = getSum("hti");
		double exi_S = getSum("exi");
		double price_S = getSum("price");
		//calculate the brand competitiveness of each firm
		double hti = 0;
		double exi = 0;
		double price = 0;
		double brandComp = 0;
		double sumOfComp = 0;
		double alpha = 0; //another expression of ex_index
		for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate brand competitiveness
			hti = firms.get(cnt).firmData().getIndex_Ht();
			exi = firms.get(cnt).firmData().getIndex_Ex();
			alpha = exi / exi_S;
			brandComp = 1 - 1 / Math.pow(1.025, hti)*(1 - 0.9*alpha);//get brand competitiveness
			firms.get(cnt).firmData().setBrandComp(brandComp);
			sumOfComp += brandComp;
		}
		//calculate saleVolume of the whole market
		double marketV = market.getCurrentVolume();
		for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate the salevolume of each firm
			double ratio = firms.get(cnt).firmData().getBrandComp() / sumOfComp;
			//calculate the basic volume according to marketVolume last stage.
			double sellV = ratio * marketV;
			firms.get(cnt).firmData().setSellVol(sellV);
		}
		
		
	}
	
	public double getSum(String index){//calculate the expectations of data
		double sum = 0;
		if(index.equals("hti")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				sum += firms.get(cnt).firmData().getIndex_Ht();
			}
		}
		else if(index.equals("exi")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				sum += firms.get(cnt).firmData().getIndex_Ex();
			}
		}
		else if(index.equals("price")){
			for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate expectation of tech index
				sum += firms.get(cnt).firmData().getProd_price();
			}
		}
		return sum;
		
	}
	
	public static void main(String[] args) {//main process function


		TimeMachine machine = new TimeMachine();
		machine.initGame();
		machine.competitionModeling(10);
/*
		int stage = 5;
		ManuFactory factory = new ManuFactory(0);
		factory.makeExcel(stage);
		Market market = new Market(0);
		market.makeExcel(stage);
		*/
	}

}
