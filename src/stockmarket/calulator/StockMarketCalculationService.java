package stockmarket.calulator;

import stockmarket.stock.Stock;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Interface for the StockMarketCalculationService.
 *
 * @author Ryan Wishart
 */
public interface StockMarketCalculationService {

    BigDecimal calculateDividendYieldForAStock(Stock stock, BigDecimal price);

    BigDecimal calculatePERatioForAStock(Stock stock, BigDecimal price);

    BigDecimal calculateVolumeWeightedStockPrice(Collection<Trade> tradesToCalculateFor);

    BigDecimal calculateGBCEAllShareIndex(Collection<Stock> stocksToCalculateFor);
}

