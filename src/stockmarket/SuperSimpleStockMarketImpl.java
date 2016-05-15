package stockmarket;

import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.stock.Stock;
import stockmarket.trade.Trade;
import stockmarket.trade.service.TradeDataService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Implementation of a {@link SuperSimpleStockMarket}.
 *
 * @author Ryan Wishart
 */
class SuperSimpleStockMarketImpl implements SuperSimpleStockMarket {

    private TradeDataService tradeDataService;

    private StockMarketCalculationService stockMarketCalculationService;

    private Map<String, Stock> listedStock;


    SuperSimpleStockMarketImpl(TradeDataService tradeDataService,
                                      StockMarketCalculationService stockMarketCalculationService,
                                      Map<String, Stock> listedStock) {

        this.tradeDataService = tradeDataService;
        this.stockMarketCalculationService = stockMarketCalculationService;
        this.listedStock = listedStock;
    }

    @Override
    public BigDecimal calculateDividendYieldForStock(Stock stock, BigDecimal price) {

        return stockMarketCalculationService.calculateDividendYieldForAStock(stock, price);
    }

    @Override
    public BigDecimal calculatePERatioForStock(Stock stock, BigDecimal price) {

        return stockMarketCalculationService.calculatePERatioForAStock(stock, price);
    }

    @Override
    public void recordTrade(Trade tradeToRecord) {

        tradeDataService.recordTrade(tradeToRecord);
    }

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(Stock stock) {

        LocalDateTime intervalEnd = LocalDateTime.now();
        LocalDateTime intervalStart = intervalEnd.minus(15, MINUTES);

        Collection<Trade> tradesInInterval = tradeDataService.getTradesForStockInInterval(stock.getStockSymbol(),
                intervalStart, intervalEnd);
        return stockMarketCalculationService.calculateVolumeWeightedStockPrice(tradesInInterval);
    }

    @Override
    public BigDecimal calculateGBCEAllShareIndex() {

        updateStockPrices();
        return stockMarketCalculationService.calculateGBCEAllShareIndex(listedStock.values());
    }

    private void updateStockPrices() {

        listedStock.values().stream().forEach(stock -> stock.setStockPrice(calculateVolumeWeightedStockPrice(stock)));
    }
}
