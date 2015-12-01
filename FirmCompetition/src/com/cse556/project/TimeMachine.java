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
		firms.get(0).initiate(0, 2, 5, 50);
		firms.get(1).initiate(0, 5, 2, 50);
		//firms.get(2).initiate(0, 2, 2, 300);
		//firms.get(3).initiate(0, 2, 2, 300);
	}
	
	public void annualTrade(int stg){
		
	}
	
	public void competitionModeling(int stageNumber){
		for(int i = 0; i < stageNumber; ++i)
		{
			stage = i;
			//make market naturally increase

			for(int cnt = 0;cnt < firms.size(); ++cnt) 
			{
				firms.get(cnt).makePrepare(stage);//every firm make decision for this stage
			}
			
			//double[] innopair = NeTest();
			market.naturalIncrease(stage);
			factory.stageRenew(stage);
			//double r1 = innopair[0] / firms.get(0).firmData().getInvestment();
			//double r2 = innopair[1] / firms.get(0).firmData().getInvestment();
			firms.get(0).makeDecision(0.4, 0.5, 0.5);
			firms.get(1).makeDecision(0.4, 0, 1);

			this.calculateData();//calculate the results of competition
			for(int cnt = 0;cnt < firms.size(); ++cnt) 
			{
				firms.get(cnt).stageRecord();
				firms.get(cnt).printToFile();
			}
			//end this stage
		}

	}
	
	public double[] NeTest(){
		factory.stageRenew(stage + 1);
		double mta1 = firms.get(0).add_max() + 1;
		double mta2 = firms.get(1).add_max() + 1;
		TestItem[][] umatrix = new TestItem[(int) mta1][(int) mta2];
		for(int i = 0;i < mta1; ++i){
			for(int j = 0;j < mta2; ++j){
				double ex1 = firms.get(0).firmData().getIndex_Ex() + firms.get(0).ex_Add(i);
				double ht1 = firms.get(0).firmData().getIndex_Ht() + i;
				double ex2 = firms.get(1).firmData().getIndex_Ex() + firms.get(1).ex_Add(j);
				double ht2 = firms.get(1).firmData().getIndex_Ht() + j;
				umatrix[i][j] = this.predictData(ht1, ex1, ht2, ex2, market.getStageVolume(this.stage + 1));
				//System.out.println(i+"::"+j+"==" + umatrix[i][j].r1 + "&&" + umatrix[i][j].r2);
			}
		}
		List<int[]> nemark = new ArrayList<int[]>();
		for(int i = 0;i < mta1; ++i){
			int m2 = 0;
			for(int j = 0;j < mta2; ++j){
				if(umatrix[i][j].r2 >= umatrix[i][m2].r2) m2 = j;
			}
			int m1 = 0;
			for(int cnt = 0;cnt < mta1; ++cnt){
				if(umatrix[cnt][m2].r1 >= umatrix[m1][m2].r1) m1 = cnt;
			}
			if(m1 == i){
				int[] re = {m1,m2};
				nemark.add(re);
				System.out.println("stage"+stage+"__"+"ne: "+m1+"::"+m2);
			}
		}
		int ht_add = nemark.get(0)[0];
		int ht_add1 = nemark.get(0)[1];
		double[] innoPair = {firms.get(0).calInnoV(ht_add),firms.get(1).calInnoV(ht_add) };
		return innoPair;
		
	}
	public TestItem predictData(double ht1,double ex1,double ht2,double ex2, double volume){
		double a1 = 1;
		double a2 = 0.02;
		
		double cost1 = factory.produceCost(ht1);
		double cost2 = factory.produceCost(ht2);
		double alpha1 = ex1 / (ex1 + ex2);
		double alpha2 = ex2 / (ex1 + ex2);
		double bc1 = 1 - 1 / Math.pow(1.025, ht1)*(1 - 0.9*alpha1);
		double bc2 = 1 - 1 / Math.pow(1.025, ht2)*(1 - 0.9*alpha2);
		double p1 = ((bc1 / (bc1+bc2)) * volume  + a2* cost1) / (2 * a2);
		double sv1 = a1 * (bc1 / (bc1+bc2)) * volume - a2 * p1;
		double p2 = ((bc2 / (bc1+bc2)) * volume  + a2* cost2) / (2 * a2);
		double sv2 = a1 * (bc2 / (bc1+bc2)) * volume - a2 * p2;
		double pro1 = (p1 - cost1) * sv1;
		double pro2 = (p2 - cost2) * sv2;
		TestItem result = new TestItem(pro1, pro2);
		return result;
	}
	
	public void calculateData(){
		double exi_S = getSum("exi");
		//calculate the brand competitiveness of each firm
		double hti = 0;
		double exi = 0;
		//double price = 0;
		double price;
		double brandComp = 0;
		double sumOfComp = 0;
		double alpha = 0; //another expression of ex_index
		double a1 = 1;
		double a2 = 0.02;

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
		for(int cnt = 0;cnt < firms.size(); ++cnt){//calculate the sales volume of each firm
			double ratio = firms.get(cnt).firmData().getBrandComp() / sumOfComp;

			//double a = -2 * a2;
			//double b1 = ratio * marketV;
			//double d = -a2 * firms.get(cnt).firmData().getProd_cost();

			price = (ratio*marketV + a2*firms.get(cnt).firmData().getProd_cost()) / (2 * a2);
			firms.get(cnt).firmData().setProd_price(price);

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
	public class TestItem{
		public double r1 = 0;
		public double r2 = 0;
		TestItem(double rr1, double rr2){
			r1 = rr1;
			r2 = rr2;
		}
	}
	public static void main(String[] args) {//main process function
		int stage = 30;
		TimeMachine machine = new TimeMachine();
		machine.initGame();
		machine.competitionModeling(stage);
	}
}