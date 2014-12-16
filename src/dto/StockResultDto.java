package dto;

import java.util.List;

public class StockResultDto {
	private List<StockDto> stockDtos;
	private double min;
	private double max;

	public List<StockDto> getStockDtos() {
		return stockDtos;
	}

	public void setStockDtos(List<StockDto> stockDtos) {
		this.stockDtos = stockDtos;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

}
