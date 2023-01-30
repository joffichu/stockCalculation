package service;

import java.util.List;

import db.dbConnection;
import model.Result;
import model.Stock;
import stock.stockCalculation;

public class updateFiveDayMovementForDate {
	public static void updateFiveDayMovementForNewStock(List<String> arg, String date) {
		try {
			Result result = new Result();
			for (String stock : arg) {
				List<Stock> s = dbConnection.retrieveStockDataT(stock, date);
				
				Double todayHigh = s.get(0).getHigh();
				Double todayLow = s.get(0).getLow();
				Double todayClose = s.get(0).getClose();
				Double doubleX = 2*(todayHigh + todayLow + todayClose);
				Double buyLSSPrice = doubleX - todayLow;
				Double sellLSSPrice = doubleX - todayHigh;
				
				Double buyPrice = (todayLow + stockCalculation.movement(s,"D") + stockCalculation.buyPrice(s,"L") + sellLSSPrice)/4;
				Double sellPrice = (todayHigh + stockCalculation.movement(s,"U") + stockCalculation.buyPrice(s,"H") + buyLSSPrice)/4;
				
				Double oneDayIntensity = ((todayClose-todayLow)*100)/(todayHigh-todayLow);
				Double maxHigh = dbConnection.maxDayHigh(stock);
				Double minLow = dbConnection.minDayLow(stock);
				Double x = maxHigh - s.get(0).getOpen();
				Double y = s.get(4).getClose() - minLow;
				Double fiveDayIntensity = (x+y)/((maxHigh-minLow)*2);
				
				result.setStockSymbol(stock.toUpperCase());
				result.setBuyPrice(buyPrice);
				result.setSellPrice(sellPrice);
				result.setLssBuyPrice(buyLSSPrice);
				result.setLssSellPrice(sellLSSPrice);
				result.setOneDayIntensity(oneDayIntensity);
				result.setFiveDayIntensity(fiveDayIntensity);
				
				
				dbConnection.updateFiveDayMovementT(stock, result.getFiveDayIntensity(), date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
