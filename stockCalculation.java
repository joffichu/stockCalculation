package stock;

import java.util.ArrayList;
import java.util.List;

import db.dbConnection;
import model.Result;
import model.Stock;
import service.createFile;
import service.downloadCSV;
import service.readCSV;

public class stockCalculation {
	
	public static void main(String[] args){
		try{
			List<String> arg = readCSV.findFileList();
			downloadCSV.download(arg);
			readCSV.readFile(arg);
			List<Result> rList = new ArrayList<Result>();
			for (String stock : arg) {
				List<Stock> s = dbConnection.retrieveStockData(stock);
				Result result = calucution(s, stock);
				dbConnection.updateFiveDayMovement(stock, result.getFiveDayIntensity());
				
				ArrayList<Double> fList = dbConnection.retrieveFiveDayMovement(stock);
				Double momentum = fList.get(0) - fList.get(3);
				result.setMomentum(momentum);
				
				rList.add(result);
			}
			createFile.genTxt(rList);
			System.out.println("Txt Generated!");
		}catch(Exception e){ 
			e.printStackTrace();
		} 
	}
	
	public static Result calucution(List<Stock> s, String stock) throws Exception {
		Result result = new Result();
		try {
			Double todayHigh = s.get(0).getHigh();
			Double todayLow = s.get(0).getLow();
			Double todayClose = s.get(0).getClose();
			Double doubleX = 2*((todayHigh + todayLow)/2);
			Double buyLSSPrice = doubleX - todayLow;
			Double sellLSSPrice = doubleX - todayHigh;
			
			Double buyPrice = (movement(s,"D") + buyPrice(s,"L") + sellLSSPrice)/3;
			Double sellPrice = (movement(s,"U") + buyPrice(s,"H") + buyLSSPrice)/3;
			
			Double oneDayIntensity = ((todayClose-todayLow)*100)/(todayHigh-todayLow);
			Double maxHigh = dbConnection.maxDayHigh(stock);
			Double minLow = dbConnection.minDayLow(stock);
			Double x = maxHigh - s.get(0).getOpen();
			Double y = s.get(4).getClose() - minLow;
			Double fiveDayIntensity = (x+y)/(maxHigh-minLow); 
			
			result.setStockSymbol(stock.toUpperCase());
			result.setBuyPrice(buyPrice);
			result.setSellPrice(sellPrice);
			result.setLssBuyPrice(buyLSSPrice);
			result.setLssSellPrice(sellLSSPrice);
			result.setOneDayIntensity(oneDayIntensity);
			result.setFiveDayIntensity(fiveDayIntensity);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public static Double movement(List<Stock> list, String UD) {
		Double movementValue = 0.0d;
		Double dayOne = list.get(0).getHigh();
		Double dayTwo = list.get(1).getHigh();
		Double dayThree = list.get(2).getHigh();
		Double today = list.get(0).getLow();
		Double pDayOne = list.get(1).getLow();
		Double pDayTwo = list.get(2).getLow();
		Double pDayThree = list.get(3).getLow();

		Double dayOneValue = dayOne-pDayOne;
		Double dayTwoValue = dayTwo-pDayTwo;
		Double dayThreeValue = dayThree-pDayThree;
		
		if(UD.equals("U")) {
			movementValue = today+((dayOneValue+dayTwoValue+dayThreeValue)/3);
		}
		
		if(UD.equals("D")) {
			movementValue = today-((dayOneValue+dayTwoValue+dayThreeValue)/3);
		}
		
		return movementValue;
	}
	
	public static Double buyPrice(List<Stock> list, String HL) {
		Double buyPrice = 0.0d;
		Double dayOne = list.get(0).getHigh();
		Double dayTwo = list.get(1).getHigh();
		Double dayThree = list.get(2).getHigh();
		Double dayFour = list.get(3).getHigh();
		Double dayOneValue = dayOne-dayTwo;
		Double dayTwoValue = dayTwo-dayThree;
		Double dayThreeValue = dayThree-dayFour;
		
		if(HL.equals("H")) {
			buyPrice = dayOne+((dayOneValue+dayTwoValue+dayThreeValue)/3);
		}
		
		if(HL.equals("L")) {
			buyPrice = dayOne-((dayOneValue+dayTwoValue+dayThreeValue)/3);
		}
		
		return buyPrice;
	}
}
