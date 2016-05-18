package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;

/**
 * Concrete implementation of a Common Stock traded on the SuperSimpleStockMarket.
 * <p/>
 * Common stocks use their last dividend value divided by quoted price to calculate their
 * dividend yield.
 *
 * @author Ryan Wishart
 */
public class CommonStock extends AbstractStock {

    /**
     * Constructor for a CommonStock object.
     *
     * @param stockSymbol   - the identifier for the stock.
     * @param lastDividend  - the last dividend payed for the stock
     * @param parValue      - the par value for the stock
     * @param stockPrice    - the price of the stock in GBP
     */
    public CommonStock(final String stockSymbol,
                       final BigDecimal lastDividend,
                       final BigDecimal parValue,
                       final BigDecimal stockPrice) {

        super(stockSymbol, lastDividend, parValue, stockPrice);
    }

    /**
     * Method to calculate the dividend yield for the stock based on a
     * quoted price.
     *
     * Common stock use the formula:  dividend yield = last dividend / price.
     *
     * @param price - a non-null, non-zero, non-negative decimal value representing the price for the stock.
     * @return      - the calculated dividend yield for ths stock.
     */
    @Override
    public BigDecimal calculateDividendYield(final BigDecimal price) {

        super.validatePrice(price);
        BigDecimal dividendYield = lastDividend.divide(price, MathContext.DECIMAL64);
        log.log(Level.ALL, String.format("Calculated dividend yield for CommonStock %s as %s", stockSymbol,
                formatter.format(dividendYield)));

        return dividendYield;
    }

    /**
     * Generate a String representation of the object.
     *
     * @return - A String representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonStock{");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }

}
