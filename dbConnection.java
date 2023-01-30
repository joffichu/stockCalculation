package db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Stock;

public class dbConnection {
	public static Connection connectDB(){  
		try{  
			String url = "jdbc:mysql://localhost:3306/stock";
			String username = "root";
			String password = "password";

			try {
				Connection connection = DriverManager.getConnection(url, username, password);
			    return connection;
			} catch (SQLException e) {
			    throw new IllegalStateException("Cannot connect the database!", e);
			} 
		}catch(Exception e){ 
			throw e;
		} 
	}
	
	public static List<Stock> retrieveStockData(String stockSymblo) throws Exception{
		
		List<Stock> sList = new ArrayList<Stock>();
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("SELECT * FROM stock."+stockSymblo+" ORDER BY date DESC LIMIT 5;");
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) { 
				Stock s = new Stock();
				s.setDate(rs.getDate("Date")); 
				s.setOpen(rs.getDouble("Open")); 
				s.setHigh(rs.getDouble("High")); 
				s.setLow(rs.getDouble("Low")); 
				s.setClose(rs.getDouble("Close")); 
				sList.add(s);
			}
		}catch(Exception e){ 
			throw e;
		}
		return sList;
	}
	
	public static List<Stock> retrieveStockDataT(String stockSymblo, String date) throws Exception{
		
		List<Stock> sList = new ArrayList<Stock>();
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM stock."+stockSymblo+" WHERE date <= '"+date+"' ORDER BY date DESC LIMIT 5 ";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) { 
				Stock s = new Stock();
				s.setDate(rs.getDate("Date")); 
				s.setOpen(rs.getDouble("Open")); 
				s.setHigh(rs.getDouble("High")); 
				s.setLow(rs.getDouble("Low")); 
				s.setClose(rs.getDouble("Close")); 
				sList.add(s);
			}
		}catch(Exception e){ 
			throw e;
		}
		return sList;
	}
	
	public static void updateFiveDayMovementT(String stockSymblo, Double fiveDayMovement, String date) throws Exception{
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("UPDATE stock."+stockSymblo+" SET FiveDayMovement = "+fiveDayMovement+" ");
			sql += ("WHERE Date = '"+date+"'");
			int rs = st.executeUpdate(sql);
			if(rs==0) {
				System.out.println("Cannot update!");
			}
		}catch(Exception e){ 
			throw e;
		}
	}
	
	public static ArrayList<Double> retrieveFiveDayMovement(String stockSymblo) throws Exception{
		ArrayList<Double> ar = new ArrayList<Double>();
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("SELECT FiveDayMovement FROM stock."+stockSymblo+" ORDER BY date DESC LIMIT 5;");
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) { 
				ar.add(rs.getDouble("FiveDayMovement"));
			}
		}catch(Exception e){ 
			throw e;
		}
		return ar;
	}
	
	private static void createTable(String stockSymblo) throws SQLException {
		Connection con = dbConnection.connectDB();
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + stockSymblo
	            + "  (Date           	VARCHAR(10),"
	            + "   Open            	DOUBLE,"
	            + "   High          	DOUBLE,"
	            + "   Low				DOUBLE,"
	            + "   Close				DOUBLE,"
	            + "   AdjClose			DOUBLE,"
	            + "   Volume			INT,"
	            + "   FiveDayMovement	DOUBLE)";

	    Statement stmt = con.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public static void insertNewRecord(String stockSymblo, Stock stock) throws Exception{
		try{  
			createTable(stockSymblo);
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = "INSERT INTO stock."+stockSymblo+" ";
			sql+= "SELECT * FROM (SELECT '"+stock.getDate()+"' AS '1', '"+stock.getOpen()+"' AS '2', '"+stock.getHigh()+"' AS '3', '"+stock.getLow()+"' AS '4', ";
			sql += "'"+stock.getClose()+"' AS '5', '"+stock.getAdjClose()+"' AS '6', '"+stock.getVolume()+"' AS '7', null) AS tmp ";
			sql += "WHERE NOT EXISTS ( SELECT date FROM stock."+stockSymblo+" WHERE date ='"+stock.getDate()+"') LIMIT 1;";
			int rs = st.executeUpdate(sql);
			if(rs==0) {
				System.out.println("Insert "+stockSymblo+" record exist!");
			}
		}catch(Exception e){ 
			throw e;
		}
	}
	
	public static void updateFiveDayMovement(String stockSymblo, Double fiveDayMovement) throws Exception{
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			cal.add(Calendar.DATE, -3);
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			cal.add(Calendar.DATE, -2);
		}else {
			cal.add(Calendar.DATE, -1);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = cal.getTime(); 
		
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("UPDATE stock."+stockSymblo+" SET FiveDayMovement = "+fiveDayMovement+" ");
			sql += ("WHERE Date = '"+formatter.format(date)+"'");
			int rs = st.executeUpdate(sql);
			if(rs==0) {
				System.out.println("Cannot update "+stockSymblo+"!");
			}
		}catch(Exception e){ 
			throw e;
		}
	}
	
	public static Double maxDayHigh(String stockSymblo) throws Exception{
		Double maxHigh = null;
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("SELECT MAX(high) AS High FROM stock."+stockSymblo+" ORDER BY date DESC LIMIT 5;");
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) { 
				maxHigh = rs.getDouble("High");
			}
		}catch(Exception e){ 
			throw e;
		}
		return maxHigh;
	}
	
	public static Double minDayLow(String stockSymblo) throws Exception{
		Double minLow = null;
		try{  
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = ("SELECT MIN(low) AS Low FROM stock."+stockSymblo+" ORDER BY date DESC LIMIT 5;");
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) { 
				minLow = rs.getDouble("Low");
			}
		}catch(Exception e){ 
			throw e;
		}
		return minLow;
	}
	
	public static void deleteRecordPassTwoMonths(String stockSymblo, String date) throws Exception{
		try{  
			createTable(stockSymblo);
			Connection con = dbConnection.connectDB();
			Statement st = con.createStatement();
			String sql = "DELETE FROM stock."+stockSymblo+" WHERE date <= '"+date+"'";
			int rs = st.executeUpdate(sql);
			if(rs == 0) {
				System.out.println("Cannot delete "+stockSymblo+" record!");
			}
		}catch(Exception e){ 
			throw e;
		}
	}
}
