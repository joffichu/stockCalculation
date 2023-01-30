package service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Result;

public class createFile {
	public static void genTxt(List<Result> resultList) throws IOException {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			DecimalFormat df2 = new DecimalFormat("#.##");
		    Date date = new Date(); 
			File myObj = new File("C:\\Downloads\\stock_"+formatter.format(date)+".txt");
			
			PrintWriter writer = new PrintWriter(myObj, "UTF-8");
			for(Result s:resultList) {
				writer.println("Stock: "+s.getStockSymbol());
				writer.println(" Buy       Sell       Break Buy     Break Sell     1 Day Indensity     5 Day Indensity     Momentum");
				writer.println("------    -------    -----------   ------------   -----------------   -----------------   ----------");
				if(df2.format(s.getBuyPrice()).length()<=5) {
					writer.println(df2.format(s.getBuyPrice())+"      "+df2.format(s.getSellPrice())+"        "
						+df2.format(s.getLssBuyPrice())+"          "+df2.format(s.getLssSellPrice())
						+"           "+df2.format(s.getOneDayIntensity())+"               "+df2.format(s.getFiveDayIntensity())
						+"            "+df2.format(s.getMomentum()));
				}
				if(df2.format(s.getBuyPrice()).length()==6) {
					writer.println(df2.format(s.getBuyPrice())+"     "+df2.format(s.getSellPrice())+"      "
						+df2.format(s.getLssBuyPrice())+"        "+df2.format(s.getLssSellPrice())
						+"            "+df2.format(s.getOneDayIntensity())+"               "+df2.format(s.getFiveDayIntensity())
						+"            "+df2.format(s.getMomentum()));
				}
				if(df2.format(s.getBuyPrice()).length()==7) {
					writer.println(df2.format(s.getBuyPrice())+"   "+df2.format(s.getSellPrice())+"       "
						+df2.format(s.getLssBuyPrice())+"       "+df2.format(s.getLssSellPrice())
						+"            "+df2.format(s.getOneDayIntensity())+"              "+df2.format(s.getFiveDayIntensity())
						+"            "+df2.format(s.getMomentum()));
				}
				writer.println("\r\n");
			}
			writer.close();
			
		} catch (IOException e) {
			throw e;
		}
	}
}
