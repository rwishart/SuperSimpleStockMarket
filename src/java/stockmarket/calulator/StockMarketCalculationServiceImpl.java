package stockmarket.calulator;

import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of a {@link StockMarketCalculationService}.
 *
 * @author Ryan Wishart
 */
public class StockMarketCalculationServiceImpl implements StockMarketCalculationService {

    private static final Logger log = Logger.getLogger("StockMarketCalculationServiceImpl");

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(final Set<Trade> tradesToCalculateFor) {

        if (tradesToCalculateFor == null || tradesToCalculateFor.size() == 0) {
            log.log(Level.ALL, "No trades provided to Volume Weighted Stock Price calculation - defaulting to zero.");
            return BigDecimal.ZERO;
        }

        int totalQuantity = 0;
        BigDecimal totalTradedPriceQuantity = new BigDecimal(0, MathContext.DECIMAL64);

        for (Trade trade : tradesToCalculateFor) {
            log.log(Level.ALL, "Calculating volume weighted stock price using trade %s", trade);
            totalTradedPriceQuantity = totalTradedPriceQuantity.add(trade.getTradedPrice().multiply(
                    new BigDecimal(trade.getQuantityOfShares(), MathContext.DECIMAL64),
                    MathContext.DECIMAL64), MathContext.DECIMAL64);
            totalQuantity += trade.getQuantityOfShares();
        }

        return totalTradedPriceQuantity.divide(BigDecimal.valueOf(totalQuantity), MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculateGBCEAllShareIndexFromPrices(final Collection<BigDecimal> stockPrices) {

        if(stockPrices == null || stockPrices.size() == 0) {
            log.log(Level.ALL, "There are no prices provided to GBCE All Share Index calculation - defaulting to zero");
            return BigDecimal.ZERO;
        }

        BigDecimal multipliedTotal = stockPrices.stream().reduce(new BigDecimal(1, MathContext.DECIMAL64), BigDecimal::multiply);

        return new BigDecimal(Math.pow(multipliedTotal.doubleValue(), 1 / stockPrices.size()), MathContext.DECIMAL64);
    }
}
