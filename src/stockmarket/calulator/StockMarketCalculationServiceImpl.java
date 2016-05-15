package stockmarket.calulator;

import stockmarket.stock.Stock;
import stockmarket.stock.StockType;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * Implementation of a {@link StockMarketCalculationService}.
 *
 * @author Ryan Wishart
 */
public class StockMarketCalculationServiceImpl implements StockMarketCalculationService {

    @Override
    public BigDecimal calculateDividendYieldForAStock(Stock stock, BigDecimal price) {

        if (stock.getStockPrice().equals(BigDecimal.ZERO) || price.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        switch (stock.getStockType()) {

            case COMMON:
                return calculateDividendForCommonStock(stock.getLastDividend(), price);
            case PREFERRED:
                return calculateDividendForPreferredStock(stock.getFixedDividend(), stock.getParValue(), price);

            default:
                return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calculatePERatioForAStock(Stock stock, BigDecimal price) {

        if (stock.getLastDividend().equals(BigDecimal.ZERO) || stock.getStockPrice().equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        return price.divide(stock.getLastDividend(), MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(final Collection<Trade> tradesToCalculateFor) {

        if (tradesToCalculateFor.size() == 0) {
            return BigDecimal.ZERO;
        }

        int totalQuantity = 0;
        BigDecimal totalTradedPriceQuantity = new BigDecimal(0, MathContext.DECIMAL64);

        for (Trade trade : tradesToCalculateFor) {

            totalTradedPriceQuantity = totalTradedPriceQuantity.add(trade.getTradedPrice().multiply(new BigDecimal(trade.getQuantityOfShares(), MathContext.DECIMAL64),
                    MathContext.DECIMAL64), MathContext.DECIMAL64);
            totalQuantity += trade.getQuantityOfShares();
        }

        return totalTradedPriceQuantity.divide(BigDecimal.valueOf(totalQuantity), MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculateGBCEAllShareIndex(final Collection<Stock> stocksToCalculateFor) {

        int size = stocksToCalculateFor.size();

        if (size == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal multipliedTotal = new BigDecimal(1, MathContext.DECIMAL64);

        for (Stock stock : stocksToCalculateFor) {
            multipliedTotal = multipliedTotal.multiply(stock.getStockPrice(), MathContext.DECIMAL64);
        }

        return new BigDecimal(Math.pow(multipliedTotal.doubleValue(), 1 / size), MathContext.DECIMAL64);
    }

    private BigDecimal calculateDividendForCommonStock(BigDecimal lastDividend, BigDecimal price) {

        return lastDividend.divide(price, MathContext.DECIMAL64);
    }

    private BigDecimal calculateDividendForPreferredStock(BigDecimal fixedDividend, BigDecimal parValue, BigDecimal price) {

        if (fixedDividend.equals(BigDecimal.ZERO) || parValue.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        return (fixedDividend.multiply(parValue, MathContext.DECIMAL64)).divide(price, MathContext.DECIMAL64);
    }
}
