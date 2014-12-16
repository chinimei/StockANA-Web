package web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import au.com.bytecode.opencsv.CSVReader;
import dto.StockDto;
import dto.StockResultDto;

/**
 * Servlet implementation class Stock
 */
public class Stock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final int DATE_INDEX = 0;
	public static final int END_PRICE_INDEX = 4;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stockNumber = request.getParameter("stock");
		StockResultDto ori = fetchData("d:\\stock\\origin\\" + stockNumber + ".csv", DATE_INDEX, END_PRICE_INDEX);
		StockResultDto sm1 = fetchData("d:\\stock\\smooth1\\" + stockNumber + ".csv", DATE_INDEX, 1);
		StockResultDto sm2 = fetchData("d:\\stock\\smooth2\\" + stockNumber + ".csv", DATE_INDEX, 1);
		List<StockResultDto> resultDtos = new ArrayList<StockResultDto>();
		resultDtos.add(ori);
		resultDtos.add(sm1);
		resultDtos.add(sm2);
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.write(JSON.encode(resultDtos));
		writer.close();

	}
	
	private StockResultDto fetchData(String fileName, int dateIndex, int priceIndex) throws FileNotFoundException {
		StockResultDto resultDto = new StockResultDto();
		CSVReader reader = new CSVReader(new FileReader(new File(fileName)), ',');
		List<StockDto> stockData = new ArrayList<StockDto>();
		try {
			String[] line = reader.readNext();
			double min = 10000d, max = 0d;
			while ((line = reader.readNext()) != null) {
				stockData.add(new StockDto(line[dateIndex], ((int)(Double.valueOf(line[priceIndex])*100))/100d));
				min = min > ((int)(Double.valueOf(line[priceIndex])*100))/100d ? ((int)(Double.valueOf(line[priceIndex])*100))/100d : min;
				max = max < ((int)(Double.valueOf(line[priceIndex])*100))/100d ? ((int)(Double.valueOf(line[priceIndex])*100))/100d : max;
			}
			resultDto.setStockDtos(stockData);
			resultDto.setMin(min);
			resultDto.setMax(max);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultDto;
	}

}
