package stockmarket.calulator;

import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

/**
 * Interface for the StockMarketCalculationService.
 * <p/>
 * The service provides a mechanism to calculate volume weighted stock prices and the GBCE all share index.
 *
 * @author Ryan Wishart
 */
public interface StockMarketCalculationService {

    /**
     * Method to calculate the volume weighted stock price based on a collection of trades for a particular stock.
     *
     * @param tradesToCalculateFor - Set of {@link Trade}s for a particular stock. All must have the same stockSymbol value.
     * @return                     - BigDecimal representing the volume weighted stock price.
     */
    BigDecimal calculateVolumeWeightedStockPrice(final Set<Trade> tradesToCalculateFor);

    /**
     * Method to calculate the GBCE All Share index given a Collection of stock prices. The method uses the geometric
     * mean of the parameter stockPrices to calculate the result. The prices are assumed to represent the prices of
     * stocks comprising all shares on the GBCE exchange.
     *
     * @param stockPrices - A collection of stock prices.
     * @return            - BigDecimal representing the geometric mean of the stock prices. This is the GBCE All share
     *                      index if the stockPrice parameter represents the prices of all shares on the GBCE.
     */
    BigDecimal calculateGBCEAllShareIndexFromPrices(final Collection<BigDecimal> stockPrices);
}

