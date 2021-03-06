package com.cse556.project;


public class StageData{
	private double invest_Inno = 0;			//innovation invest
	private double invest_Qual = 0;         //quality invest 
	private double investment = 0;			//total invest
	
	private double index_Ht = 0;       //high-tech index
	private double techAd = 0;         //advance of tech index per year.
	private double index_Ex = 0;     //experience index
	private double exAd = 0;  //advance of experience index
	private double brandComp = 0; //competitiveness of the firm

	private double prod_price = 0;				//product price
	private double prod_cost = 0;				//product cost
	
	private double sellVol = 0;				//product sales volume
	private double profit = 0;				//final profit
	private int stage = 0;
	private String firmName = null;
	
	private double totalAssets = 0;
	
	public void resetData(){
		this.invest_Inno = 0;
		this.invest_Qual = 0;
		this.investment = 0;
		this.index_Ht = 0;
		this.index_Ex = 0;
		this.techAd = 0;
		this.prod_price = 0;
		this.prod_cost = 0;
		this.sellVol = 0;
		this.profit = 0;
		this.stage = 0;
		this.exAd = 0;
	}
	
	public StageData(String name){
		this.firmName = name;
	}
	
	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public double getExAd() {
		return exAd;
	}
	
	
	public double getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public void setExAd(double exAd) {
		this.exAd = exAd;
	}
	
	public double getBrandComp() {
		return brandComp;
	}

	public void setBrandComp(double brandComp) {
		this.brandComp = brandComp;
	}
	
	public double getInvest_Inno() {
		return invest_Inno;
	}
	public void setInvest_Inno(double invest_Inno) {
		this.invest_Inno = invest_Inno;
	}
	public double getInvest_Qual() {
		return invest_Qual;
	}
	public void setInvest_Qual(double invest_Qual) {
		this.invest_Qual = invest_Qual;
	}
	public double getInvestment() {
		return investment;
	}
	public void setInvestment(double investment) {
		this.investment = investment;
	}
	public double getIndex_Ht() {
		return index_Ht;
	}
	public void setIndex_Ht(double index_Ht) {
		this.index_Ht = index_Ht;
	}
	public double getTechAd() {
		return techAd;
	}
	public void setTechAd(double techAd) {
		this.techAd = techAd;
	}
	public double getIndex_Ex() {
		return index_Ex;
	}
	public void setIndex_Ex(double index_Ex) {
		this.index_Ex = index_Ex;
	}
	public double getProd_price() {
		return prod_price;
	}
	public void setProd_price(double prod_price) {
		this.prod_price = prod_price;
	}
	public double getProd_cost() {
		return prod_cost;
	}
	public void setProd_cost(double prod_cost) {
		this.prod_cost = prod_cost;
	}
	public double getSellVol() {
		return sellVol;
	}
	public void setSellVol(double sellVol) {
		this.sellVol = sellVol;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	
}
