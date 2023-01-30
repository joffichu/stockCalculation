package stock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import service.downloadCSV;
import service.readCSV;
import service.updateFiveDayMovementForDate;

public class addNewStock {
	public static void main(String[] args) {
		try {
			List<String> someList = new ArrayList<String>();
			someList.addAll(Arrays.asList("STOCK1", "STOCK2", "STOCK3", "STOCK4"));
			downloadCSV.downloadForNewStock(someList);
			readCSV.readFile(someList);
			
			for(int i = 1;i<11;i++) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -i);
				Date tmp = cal.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
				String date = formatter.format(tmp);
				updateFiveDayMovementForDate.updateFiveDayMovementForNewStock(someList, date);
			}
			System.out.println("DONE");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
