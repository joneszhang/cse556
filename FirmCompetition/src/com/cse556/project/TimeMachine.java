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
	//test
	
	public TimeMachine(){
		
		/*Init the firms*/
		firms = new ArrayList<Firm>();
		firms.add(new Firm("jpelectronic", this));	
		firms.add(new Firm("tommyinnovation", this));
		//firms.add(new Firm("zszhang", this));
		//firms.add(new Firm("haishenglin", this));
		/*Init the Market*/
		market = new Market(stage);
		factory = new ManuFactory(stage);
	}
	
	public void initGame(){
		firms.get(0).initiate(0, 2, 2, 300);
		firms.get(1).initiate(0, 2, 2, 300);
		//firms.get(2).initiate(0, 2, 2, 300);
		//firms.get(3).initiate(0, 2, 2, 300);
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
			firms.get(0).makeDecision(0.3, 0.7);
			firms.get(1).makeDecision(0.7, 0.3);
			//firms.get(2).makeDecision(0.5, 0.5);
			//firms.get(3).makeDecision(0.4, 0.6);
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
		//double price = 0;
		double price;
		double brandComp = 0;
		double sumOfComp = 0;
		double alpha = 0; //another expression of ex_index
		double a1 = 1;
		double a2 = 0.01;
		double a3 = 2000;


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
			//price = firms.get(cnt).firmData().getProd_price();
			//double sellV = ratio * marketV;
			double a = -2 * a2;
			double b1 = ratio * marketV;
			double d = -a2 * firms.get(cnt).firmData().getProd_cost();

			//sellVol =  ratio * marketV - a2 * price;
			//因为profit = sellVol*(price - produceCost),对price求导：使profit最大化，就会得出这个公式
			price = (ratio * marketV + a2 * firms.get(cnt).firmData().getProd_cost()) / (2 * a2);
			//price = (-b1/(3*a)) + (1/(3*a*Math.pow(2, 1/3)))*Math.pow((-2*b1*b1*b1-27*a*a*d + Math.pow(-4*Math.pow(b1,6) + Math.pow(-2*b1*b1*b1-27*a*a*d, 2),1/2)), 1/3)-(b1*b1)*Math.pow(2, 1/3)/(3*a*Math.pow(-2*b1*b1*b1-27*a*a*d + Math.pow(-4*Math.pow(b1,6) + Math.pow(-2*b1*b1*b1-27*a*a*d, 2),1/2), 1/3));
			firms.get(cnt).firmData().setProd_price(price);

			//double sellV = a1 * ratio * marketV - a2 * price + a3 * brandComp / price;
			double sellV = a1 * ratio * marketV - a2 * price;
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


		int stage = 10;
		TimeMachine machine = new TimeMachine();
		machine.initGame();
		machine.competitionModeling(stage);


		/*
		int stage = 30;
		ManuFactory factory = new ManuFactory(0);
		factory.makeExcel(stage);
		Market market = new Market(0);
		market.makeExcel(stage);
		*/

	}

}
