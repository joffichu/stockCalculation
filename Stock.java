package model;

import java.sql.Date;

public class Stock {
	private Date date;
	private Double high;
	private Double low;
	private Double open;
	private Double close;
	private Double adjClose;
	private String volume;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getAdjClose() {
		return adjClose;
	}
	public void setAdjClose(Double adjClose) {
		this.adjClose = adjClose;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
}
