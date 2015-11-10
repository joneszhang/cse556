package com.cse556.project;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Market {
	private double basevolume; //annual market volume
	private double stableRate;  //the natural increasing rate of volume
	private int currentStage; //current stage
	private double currentVolume; //volume that is under processing.
	private Map<Integer, Double> annualVolume = null;
	
	public Market(int stg){
		currentStage = stg;
		stableRate = 1.15;
		basevolume = 30;
		annualVolume = new HashMap<Integer, Double>();
		annualVolume.put(0, basevolume);
	}
	
	public double getAnuualV(int stage){
		return annualVolume.get(stage);
	}
	
	public double getCurrentVolume(){
		return this.currentVolume;
	}
	
	public void naturalIncrease(int stg){
		currentStage = stg;
		currentVolume = basevolume * Math.pow(stableRate, currentStage);//有问题
	}

	public void makeExcel(int stg){
		String marketFile = "Market.xls";//Excel FileName
		String title[]={"Stage","Volume"};//title including parameters

		try {

			WritableWorkbook book= Workbook.createWorkbook(new File(marketFile));
			WritableSheet sheet=book.createSheet("MarketData",0);

			for(int i = 0;i <= 1; i++)    //title
				sheet.addCell(new Label(i,0,title[i]));

			for(int i = 0;i <= stg; i++)    //context
			{
				this.naturalIncrease(i);
				for(int j = 0;j <= 1; j++)
				{
					switch (j)
					{
						case 0:
							sheet.addCell(new Label(j, i + 1 , Integer.toString(i)));
							break;

						case 1:
							sheet.addCell(new Label(j, i + 1 , Double.toString(this.getCurrentVolume())));
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
	
}
