package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;

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
     */
    public CommonStock(final String stockSymbol,
                       final BigDecimal lastDividend,
                       final BigDecimal parValue) {

        super(stockSymbol, lastDividend, parValue);
    }

    /**
     * Method to calculate the dividend yield for the stock based on a
     * quoted price.
     *
     * @param price - a non-null, non-zero, non-negative decimal value representing the price for the stock.
     * @return      - the calculated dividend yield for ths stock.
     */
    @Override
    public BigDecimal calculateDividendYield(final BigDecimal price) {

        super.validatePrice(price);

        return lastDividend.divide(price, MathContext.DECIMAL64);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonStock{");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
