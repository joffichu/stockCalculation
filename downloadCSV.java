package service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class downloadCSV {
	public static void download(List<String> args) throws IOException{
		try {
			for (String stockSymbol : args) {
				Calendar cal = Calendar.getInstance();
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
					cal.add(Calendar.DATE, -3);
				}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					cal.add(Calendar.DATE, -2);
				}else {
					cal.add(Calendar.DATE, -1);
				}
				
				Date from = cal.getTime();
				Timestamp fromTs=new Timestamp(from.getTime());
				fromTs.setHours(0);
				fromTs.setMinutes(0);
				fromTs.setSeconds(0);
				String fromS = fromTs.getTime()+"";
				fromS = fromS.substring(0, fromS.length()-3);
				
				Date to = new Date();
				Timestamp toTs=new Timestamp(to.getTime());
				toTs.setHours(0);
				toTs.setMinutes(0);
				toTs.setSeconds(0);
				String toS = toTs.getTime()+"";
				toS = toS.substring(0, toS.length()-3);
				
				String dwUrl = "https://query1.finance.yahoo.com/v7/finance/download/"+stockSymbol
						+ "?period1="+fromS
						+"&period2="+toS+"&interval=1d&events=history&includeAdjustedClose=true";
				String PATH = "C:\\Downloads\\CSV\\";
				String FILENAME = stockSymbol+".csv";
				
				File file = new File(PATH + FILENAME);
			    URL url = new URL(dwUrl);
			    FileUtils.copyURLToFile(url, file);
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static void downloadForNewStock(List<String> args) throws IOException{
		try {
			for (String stockSymbol : args) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -30);
				Date from = cal.getTime();
				Timestamp fromTs=new Timestamp(from.getTime());
				fromTs.setHours(0);
				fromTs.setMinutes(0);
				fromTs.setSeconds(0);
				String fromS = fromTs.getTime()+"";
				fromS = fromS.substring(0, fromS.length()-3);
				
				Date to = new Date();
				Timestamp toTs=new Timestamp(to.getTime());
				toTs.setHours(0);
				toTs.setMinutes(0);
				toTs.setSeconds(0);
				String toS = toTs.getTime()+"";
				toS = toS.substring(0, toS.length()-3);
				
				String dwUrl = "https://query1.finance.yahoo.com/v7/finance/download/"+stockSymbol
						+ "?period1="+fromS
						+"&period2="+toS+"&interval=1d&events=history&includeAdjustedClose=true";
				String PATH = "C:\\Downloads\\CSV\\";
				String FILENAME = stockSymbol+".csv";
				
				File file = new File(PATH + FILENAME);
			    URL url = new URL(dwUrl);
			    FileUtils.copyURLToFile(url, file);
			}
		} catch (IOException e) {
			throw e;
		}
	}
}

