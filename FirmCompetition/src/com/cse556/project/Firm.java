package com.cse556.project;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.text.DecimalFormat;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Firm {
	Map<Integer, StageData> annual_Data = null; //list of annual data of this firm
	int currentStage = 0;//current stage of game
	private String firmName;
	private StageData currentData = null;
	private TimeMachine informer = null;
	
	//parameters for tech-index calculation
	private double beta=0.8;
	
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
		this.currentData = initData;
	}
	
	public void makePrepare(int stg){
		this.currentStage = stg;
		if(0 == currentStage)//for the beginning, no preparation need to be done.
			return;
		
		StageData newData = new StageData(this.firmName);
		currentData = newData;
		currentData.setStage(this.currentStage);//set the stage of data
		
		//set the investment amount
		currentData.setInvestment(annual_Data.get(this.currentStage - 1).getProfit()
				* 0.2);
		
		//calculate the progress in tech index and the current index of Tech index
		double ti = annual_Data.get(currentStage - 1).getTechAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ht();
		currentData.setIndex_Ht(ti);
		//calculate experience index
		double ex = annual_Data.get(currentStage - 1).getExAd() 
				+ annual_Data.get(this.currentStage - 1).getIndex_Ex();
		currentData.setIndex_Ex(ex);;
		
	}
	
	public void makeDecision(double ratio, double ratio1){// set the data for every stage
		
		currentData.setStage(currentStage);
		
		double profit_lastyear = currentData.getInvestment();
		//decide investment on high tech
		currentData.setInvest_Inno(profit_lastyear * ratio);
		//decide investment on experience
		currentData.setInvest_Qual(profit_lastyear * (ratio1));
		
		//calculate the productive cost of this stage
		currentData.setProd_cost(informer.factory.produceCost(currentData.getIndex_Ht()));
		
		//decide the price of this stage.
				currentData.setProd_price(currentData.getProd_cost() * 1.5);
		
		//calculate the increase in sell volume due to dech increase
		//double addsell = (currentStage > 0)?informer.market.getCurrentVolume() * 0.005 
			//	* annual_Data.get(currentStage - 1).getTechAd() : 0;
		//currentData.setSellVol(addsell);
	}
	
	
	public StageData firmData(){
		return this.currentData;
	}
	
	public boolean stageRecord(){//record the result for every stage
		double prof = (currentData.getProd_price() - currentData.getProd_cost())
				* currentData.getSellVol();
		currentData.setProfit(prof);
		
		//record the advance in tech
		double innov = this.currentData.getInvest_Inno();
		double ti_add=0;
		for(int i = 0;;++i){
			innov -=
				this.beta 
				* Math.pow(this.currentData.getIndex_Ht()+ti_add, 2)
				/ 2;
			if(innov>=0)
				ti_add += 1;
			else
				break;
		}
		this.currentData.setTechAd(ti_add);
		//record the advance in experience
		double ex = this.currentData.getInvest_Qual();
		double ex_add = 0;
		for(int i = 0;;++i){
			if(ex > this.beta 
					* Math.pow(this.currentData.getIndex_Ex() + ex_add, 2)
					/ 2){
				ex_add += 1;
				ex -= this.beta 
						* Math.pow(this.currentData.getIndex_Ex() + ex_add, 2)
						/ 2;
			}else{
				ex_add += ex /( this.beta 
						* Math.pow(this.currentData.getIndex_Ex()+ex_add, 2)
						/ 2);
				break;
			}
			
		}
		this.currentData.setExAd(ex_add);
		annual_Data.put(currentStage, currentData);
		return true;
	}
	
	public void printToFile(){
		String title[] = {"stage", "Investment", "Inno_Invest", "Ex_Invest",
				"Ht_Index", "Ex_Index", "Ht_Add", "Ex_Add", "Compititiveness",
				"price", "productCost", "sellVolume", "profit"};
		try{
			WritableWorkbook book= Workbook.createWorkbook(new File(this.firmName+".xls"));
			WritableSheet sheet = book.createSheet("first",0);
			//write in table
			for(int i =0;i < title.length; ++i){
				sheet.addCell(new Label(i,0,title[i]));
			}
			for(int j = 0;j < this.annual_Data.size();++j){
				StageData data = annual_Data.get(j);
				DecimalFormat df = new DecimalFormat("0.000");
				sheet.addCell(new Label(0,j+1,String.valueOf(data.getStage())));//stage
				sheet.addCell(new Label(1,j+1,df.format(data.getInvestment())));//invest
				sheet.addCell(new Label(2,j+1,df.format(data.getInvest_Inno())));
				sheet.addCell(new Label(3,j+1,df.format(data.getInvest_Qual())));
				sheet.addCell(new Label(4,j+1,df.format(data.getIndex_Ht())));
				sheet.addCell(new Label(5,j+1,df.format(data.getIndex_Ex())));
				sheet.addCell(new Label(6,j+1,df.format(data.getTechAd())));
				sheet.addCell(new Label(7,j+1,df.format(data.getExAd())));
				sheet.addCell(new Label(8,j+1,df.format(data.getBrandComp())));
				sheet.addCell(new Label(9,j+1,df.format(data.getProd_price())));
				sheet.addCell(new Label(10,j+1,df.format(data.getProd_cost())));
				sheet.addCell(new Label(11,j+1,df.format(data.getSellVol())));
				sheet.addCell(new Label(12,j+1,df.format(data.getProfit())));
			}
			book.write();
			book.close();
		}catch(Exception e){
			
		}
		
	}
	
}
