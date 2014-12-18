(function($) {
	var height = 500;
	
	convertPriceToY = function(price, max, min) {
		var y = height - (price - min)*height/(max-min);
		return y;
	};
	
	drawCanvas = function(canvas, data) {
		var min = data.min;
		var max = data.max;
		var stocks = data.stockDtos;
		var ctx = canvas.getContext("2d");

		ctx.clearRect ( 0 , 0 , canvas.width, canvas.height );
		
		stocks.reverse();
		
		ctx.beginPath();
		ctx.moveTo(0, convertPriceToY(stocks[0].price, max, min));
		for (var i=1;i<stocks.length;i++) {
			ctx.lineTo(i, convertPriceToY(stocks[i].price, max, min));
		}
		
		ctx.strokeStyle = '#ff0000';
		ctx.stroke();
	};
	
	$(document).ready(function() {
		$("#selectStock").click(function() {
			$.ajax({
				type : "POST",
				url : "Stock",
				data : {
					stock : $("#stockNumber").val()
				}
			}).done(function(data) {
				var stockData = $.parseJSON(data);
				drawCanvas($("#origin")[0], stockData[0]);
				drawCanvas($("#smooth1")[0], stockData[1]);
				drawCanvas($("#smooth2")[0], stockData[2]);
			});
		});
	});
})(jQuery);