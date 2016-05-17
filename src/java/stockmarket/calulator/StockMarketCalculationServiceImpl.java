package stockmarket.calulator;

import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Set;

/**
 * Implementation of a {@link StockMarketCalculationService}.
 *
 * @author Ryan Wishart
 */
public class StockMarketCalculationServiceImpl implements StockMarketCalculationService {

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(final Set<Trade> tradesToCalculateFor) {

        if (tradesToCalculateFor.size() == 0) {
            return BigDecimal.ZERO;
        }

        int totalQuantity = 0;
        BigDecimal totalTradedPriceQuantity = new BigDecimal(0, MathContext.DECIMAL64);

        for (Trade trade : tradesToCalculateFor) {

            totalTradedPriceQuantity = totalTradedPriceQuantity.add(trade.getTradedPrice().multiply(
                    new BigDecimal(trade.getQuantityOfShares(), MathContext.DECIMAL64),
                    MathContext.DECIMAL64), MathContext.DECIMAL64);
            totalQuantity += trade.getQuantityOfShares();
        }

        return totalTradedPriceQuantity.divide(BigDecimal.valueOf(totalQuantity), MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculateGBCEAllShareIndexFromPrices(final Collection<BigDecimal> stockPrices) {

        int size = stockPrices.size();

        if (size == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal multipliedTotal = stockPrices.stream().reduce(new BigDecimal(1, MathContext.DECIMAL64), BigDecimal::multiply);

        return new BigDecimal(Math.pow(multipliedTotal.doubleValue(), 1 / size), MathContext.DECIMAL64);
    }
}
