package stockmarket;

import stockmarket.trade.Trade;

import java.math.BigDecimal;

/**
 * Interface for the SuperSimpleStockMarket.
 * <p/>
 * A SuperSimpleStockMarket provides basic stock market functionality for a set of stocks represented as Stock objects.
 * <p/>
 * All calculations are done using BigDecimal representations using the MathContext.DECIMAL64 context for precision and
 * rounding rules.
 * <p/>
 * The exchange supports two kinds of Stock, preferred (represented as PreferredStock) and common (represented
 * as CommonStock).
 *
 * @author Ryan Wishart
 */
interface SuperSimpleStockMarket {

    /**
     * Method to calculate the dividend yield for a particular stock given a price.
     *
     * @param stockSymbol - The stock symbol for the stock to use.
     * @param price       - The price of the stock.
     * @return            - The dividend yield for the stock at the parameter price.
     */
    BigDecimal calculateDividendYieldForStock(final String stockSymbol, final BigDecimal price);

    /**
     * Method to calculate the Price to Earnings (PE) ratio for a Stock.
     *
     * @param stockSymbol - Stock symbol for the stock to calculate PE ratio on
     * @param price       - The price at which the PE ratio should be calculated
     * @return            - The PE ratio calculated for the parameter stock
     */
    BigDecimal calculatePERatioForStock(final String stockSymbol, final BigDecimal price);

    /**
     * Record a Trade on the stock exchange.
     *
     * @param trade - the Trade to record.
     */
    void recordTrade(final Trade trade);

    /**
     * Method to calculate the volume weighted stock price for a given stock using all
     * trades on the stock within the last 15 min.
     *
     * @param stockSymbol - the Stock symbol for the stock to use.
     * @return            - volume weighted stock price for the Stock as a BigDecimal.
     */
    BigDecimal calculateVolumeWeightedStockPrice(final String stockSymbol);

    /**
     * Calculates the GBCE All Share Index for the exchange. Takes the geometric mean of
     * prices for all Stocks.
     *
     * @return - BigDecimal representing the geometric mean of prices for all stock registered on the
     *           SuperSimpleStockMarket.
     */
    BigDecimal calculateGBCEAllShareIndex();
}
