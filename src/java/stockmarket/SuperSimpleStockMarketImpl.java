package stockmarket;

import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.stock.Stock;
import stockmarket.stocklisting.StockListing;
import stockmarket.trade.Trade;
import stockmarket.tradedata.TradeDataService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Concrete implementation of a {@link SuperSimpleStockMarket}.
 *
 * @author Ryan Wishart
 */
class SuperSimpleStockMarketImpl implements SuperSimpleStockMarket {

    private TradeDataService tradeDataService;

    private StockMarketCalculationService stockMarketCalculationService;

    private StockListing stockListing;

    private static final int WEIGHTED_VOLUME_STOCK_PRICE_CALC_WINDOW = 15;


    /**
     * Constructor for the SuperSimpleStockMarketImpl
     *
     * @param tradeDataService               - an instance of TradeDataService
     * @param stockMarketCalculationService  - an instance of the StockMarketCalculationService
     * @param stockListing                   - an instance of a StockListing
     */
    SuperSimpleStockMarketImpl(final TradeDataService tradeDataService,
                               final StockMarketCalculationService stockMarketCalculationService,
                               final StockListing stockListing) {

        this.tradeDataService = tradeDataService;
        this.stockMarketCalculationService = stockMarketCalculationService;
        this.stockListing = stockListing;
    }

    @Override
    public BigDecimal calculateDividendYieldForStock(String stockSymbol,
                                                     final BigDecimal price) {

        validateStockSymbol(stockSymbol);

        Stock stock = stockListing.getListedStock(stockSymbol);
        return stock.calculateDividendYield(price);
    }

    @Override
    public BigDecimal calculatePERatioForStock(final String stockSymbol,
                                               final BigDecimal price) {

        validateStockSymbol(stockSymbol);

        Stock stock = stockListing.getListedStock(stockSymbol);
        return stock.calculatePERatio(price);
    }

    @Override
    public void recordTrade(final Trade trade) {

        validateStockSymbol(trade.getStockSymbol());

        tradeDataService.recordTrade(trade);
    }

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(final String stockSymbol) {

        validateStockSymbol(stockSymbol);

        LocalDateTime intervalEnd = LocalDateTime.now();
        LocalDateTime intervalStart = intervalEnd.minusMinutes(WEIGHTED_VOLUME_STOCK_PRICE_CALC_WINDOW);

        Set<Trade> tradesInInterval = tradeDataService.getTradesForStockInInterval(stockSymbol,
                intervalStart, intervalEnd);
        return stockMarketCalculationService.calculateVolumeWeightedStockPrice(tradesInInterval);
    }

    @Override
    public BigDecimal calculateGBCEAllShareIndex() {

        Collection<Stock> allStocks = stockListing.getAllListedStock();

        LocalDateTime intervalStart = LocalDateTime.now();
        LocalDateTime intervalEnd = intervalStart.minusMinutes(WEIGHTED_VOLUME_STOCK_PRICE_CALC_WINDOW);

        List<BigDecimal> stockPrices = allStocks.stream().map(stock -> {

            Set<Trade> tradesForStock = tradeDataService.getTradesForStockInInterval(stock.getStockSymbol(), intervalStart, intervalEnd);
            return stockMarketCalculationService.calculateVolumeWeightedStockPrice(tradesForStock);
        }).collect(Collectors.toList());

        return stockMarketCalculationService.calculateGBCEAllShareIndexFromPrices(stockPrices);
    }

    /**
     * Method to validate that the parameter stockSymbol is present in the stockListing.
     * <p/>
     *
     * @throws IllegalArgumentException - if the parameter stock symbol is not listed in the stockListing.
     * @param stockSymbol               - the stock symbol to validate in stockListing.
     */
    private void validateStockSymbol(String stockSymbol) {

        if (!stockListing.isListedStock(stockSymbol))
            throw new IllegalArgumentException(String.format("Invalid stock symbol provided. The parameter stock symbol (%s) is not " +
                    "registered at the stock exchange.", stockSymbol));
    }
}
