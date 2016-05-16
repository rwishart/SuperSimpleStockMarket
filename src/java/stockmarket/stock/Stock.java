package stockmarket.stock;

import java.math.BigDecimal;

/**
 * Stock class to represent a Stock object in the SimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public interface Stock {

    BigDecimal calculateDividendYield(final BigDecimal price);

    BigDecimal calculatePERatio(final BigDecimal price);

    BigDecimal getParValue();

    BigDecimal getLastDividend();

    String getStockSymbol();

}
