package dto;


public class StockDto {

	private String date;
	private double price;

	public StockDto(String date, double price) {
		super();
		this.date = date;
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
