package stockmarket.calulator;

import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Interface for the StockMarketCalculationService.
 *
 * @author Ryan Wishart
 */
public interface StockMarketCalculationService {

    BigDecimal calculateVolumeWeightedStockPrice(final Collection<Trade> tradesToCalculateFor);

    BigDecimal calculateGBCEAllShareIndexFromPrices(final Collection<BigDecimal> stockPrices);
}

