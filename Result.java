package model;

public class Result {
	private String stockSymbol;
	private Double buyPrice;
	private Double sellPrice;
	private Double lssBuyPrice;
	private Double lssSellPrice;
	private Double oneDayIntensity;
	private Double fiveDayIntensity;
	private Double momentum;
	
	
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getLssBuyPrice() {
		return lssBuyPrice;
	}
	public void setLssBuyPrice(Double lssBuyPrice) {
		this.lssBuyPrice = lssBuyPrice;
	}
	public Double getLssSellPrice() {
		return lssSellPrice;
	}
	public void setLssSellPrice(Double lssSellPrice) {
		this.lssSellPrice = lssSellPrice;
	}
	public Double getOneDayIntensity() {
		return oneDayIntensity;
	}
	public void setOneDayIntensity(Double oneDayIntensity) {
		this.oneDayIntensity = oneDayIntensity;
	}
	public Double getFiveDayIntensity() {
		return fiveDayIntensity;
	}
	public void setFiveDayIntensity(Double fiveDayIntensity) {
		this.fiveDayIntensity = fiveDayIntensity;
	}
	public Double getMomentum() {
		return momentum;
	}
	public void setMomentum(Double momentum) {
		this.momentum = momentum;
	}
}
