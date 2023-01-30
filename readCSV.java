package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import db.dbConnection;
import model.Stock;

public class readCSV {
	
	public static void readFile(List<String> args) throws Exception { 
		for (String stockSymbol : args) {
			String filePath = "C:\\Downloads\\CSV\\"+stockSymbol.toUpperCase()+".csv";
			String line = "";
			try {  
				//parsing a CSV file into BufferedReader class constructor  
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String header = br.readLine();
				while ((line = br.readLine()) != null) {  
					Stock stock = new Stock();
					String[] tmp = line.split(",");
					Date date=Date.valueOf(tmp[0]);
					stock.setDate(date);
					stock.setOpen(Double.parseDouble(tmp[1]));
					stock.setHigh(Double.parseDouble(tmp[2]));
					stock.setLow(Double.parseDouble(tmp[3]));
					stock.setClose(Double.parseDouble(tmp[4]));
					stock.setAdjClose(Double.parseDouble(tmp[5]));
					stock.setVolume(tmp[6]);
					dbConnection.insertNewRecord(stockSymbol, stock);
				}  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
	}
	
	public static List<String> findFileList() throws IOException {
		List<String> args = new ArrayList<String>();
		File directoryPath = new File("C:\\Downloads\\CSV");
		File filesList[] = directoryPath.listFiles();
		for(File file : filesList) {
			args.add(file.getName().replace(".csv", ""));
		}
		return args;
	}
}
