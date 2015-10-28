package com.cse556.project;

public class Firm {
	private double invest_Inno;			//innovation invest
	private double invest_Qual;         //quality invest 
	private double investment;			//total invest
	
	private double index_Ht;       //high-tech index
	private double techAd;         //advance of tech index per year.
	private double index_Ex;     //experience index
	
	private double prod_price;				//product price
	private double prod_cost;				//product cost
	
	private double brandComp;			//brand competitive
	private int sellVol;				//product sales volume
	private double profit;				//final profit
	
	public void initiate(double invest0, double ht0, double ex0){
		
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

	public double getBrandComp() {
		return brandComp;
	}

	public void setBrandComp(double brandComp) {
		this.brandComp = brandComp;
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

	public int getSellVol() {
		return sellVol;
	}

	public void setSellVol(int sellVol) {
		this.sellVol = sellVol;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}
	
	
}
