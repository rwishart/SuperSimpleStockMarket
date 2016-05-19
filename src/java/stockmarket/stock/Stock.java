package stockmarket.stock;

import java.math.BigDecimal;

/**
 * This interface represents the concept of a stock within the SuperSimpleStockMarket.
 * <p/>
 * All stocks require a stock symbol. This is used to identify the stock in the SuperSimpleStockMarket. All stocks
 * must also provide a last dividend value and a par value. Both the last dividend and the par value may be zero.
 *
 * @author Ryan Wishart
 */
public interface Stock {

    /**
     * This method takes a parameter price and returns the calculated dividend yield value.
     *
     * @param price  - Value representing the price of the stock.
     * @return       - Value representing the calculated dividend yield value.
     */
    BigDecimal calculateDividendYield(final BigDecimal price);

    /**
     * This method calculates the Price to Earnings ratio (PE ratio) for the stock given a parameter price.
     *
     * @param price  - value representing the quoted price for the stock
     * @return       - the PE ratio calculated for the stock.
     */
    BigDecimal calculatePERatio(final BigDecimal price);

    BigDecimal getParValue();

    BigDecimal getLastDividend();

    String getStockSymbol();

    BigDecimal getStockPrice();

}
