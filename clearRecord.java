package stock;

import java.util.List;

import db.dbConnection;
import service.readCSV;

public class clearRecord {
	public static void main(String[] args) {
		try {
			List<String> arg = readCSV.findFileList();
			String date = "2020-09-30";
			for (String stock : arg) {
				dbConnection.deleteRecordPassTwoMonths(stock, date);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
