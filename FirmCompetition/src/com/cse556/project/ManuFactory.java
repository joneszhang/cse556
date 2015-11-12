package com.cse556.project;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

public class ManuFactory {
	private double produceTech;  //the producing tech of the manufacuring companies.
	private int currentStage;
	private double baseCost;
	//constant parameters of formulas
	private double k = 10000;
	private double d = 20;
	private double p = 100;
	private double c = 1.5;
	private double a = 8;
	private double b = 0.4;
	private double m = 2;
	private double e = 100;
	
	public ManuFactory(int stg){
		currentStage = stg;
		baseCost = 5000;
		produceTech = 6;
	}
	
	
	public double produceCost(double techIndex){//calculate the costs
		double exCost = 0;
		exCost = m * techIndex / this.produceTech;
		exCost = Math.pow(exCost, 2);
		double cost = this.baseCost + exCost;
		//System.out.print(this.baseCost+"\n");
		return cost;
	}
	
	public void stageRenew(int stg){
		currentStage = stg;
		this.produceTech = p / (1+ Math.pow(c, (a - b*currentStage)));
		this.baseCost = k / (this.produceTech + d) + e;
	}

	public void makeExcel(int stg){
		String factoryFile = "ManuFactory.xls";//Excel FileName
		String title[]={"Stage","ProduceTechIndex","ProduceCost"};//title including parameters


		try {

			WritableWorkbook book= Workbook.createWorkbook(new File(factoryFile));
			WritableSheet sheet=book.createSheet("ManuFactoryData",0);

			for(int i = 0;i <= 2; i++)    //title
				sheet.addCell(new Label(i,0,title[i]));

			for(int i = 0;i <= stg; i++)    //context
			{
				this.stageRenew(i);
				for(int j = 0;j <= 2; j++)
				{
					switch (j)
					{
						case 0:
							sheet.addCell(new Label(j, i + 1 , Integer.toString(i)));
							break;

						case 1:
							sheet.addCell(new Label(j, i + 1 , Double.toString(this.getProduceTech())));
							break;

						case 2:
							sheet.addCell(new Label(j, i + 1, Double.toString(this.produceCost(this.getProduceTech()))));
							break;
					}

				}
			}

			//input data
			book.write();
			//close file
			book.close();
		}
		catch(Exception e) { }
	}
	
	
	/*getters and setters*/
	public int getCurrentStage() {
		return currentStage;
	}


	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}


	public double getProduceTech() {
		return produceTech;
	}

	public void setProduceTech(double produceTech) {
		this.produceTech = produceTech;
	}
	
}
