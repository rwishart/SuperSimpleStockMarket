package stockmarket;

import stockmarket.trade.Trade;

import java.math.BigDecimal;

/**
 * Interface for the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
interface SuperSimpleStockMarket {

    BigDecimal calculateDividendYieldForStock(final String stockSymbol, final BigDecimal price);

    BigDecimal calculatePERatioForStock(final String stockSymbol, final BigDecimal price);

    void recordTrade(final Trade trade);

    BigDecimal calculateVolumeWeightedStockPrice(final String stockSymbol);

    BigDecimal calculateGBCEAllShareIndex();
}
