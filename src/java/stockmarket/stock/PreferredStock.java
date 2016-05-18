package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;

/**
 * Concrete implementation of a Preferred Stock.
 * <p/>
 * Preferred stocks have a fixed dividend value that is used when calculating dividend yield.
 *
 * @author Ryan Wishart
 */
public class PreferredStock extends AbstractStock {

    private BigDecimal fixedDividend;

    /**
     * Public constructor for a PreferredStock. All monetary amounts are given in GBP.
     *
     * @param stockSymbol    - Unique identifier for the stock
     * @param lastDividend   - Last dividend of the stock
     * @param parValue       - Par value recorded for the stock
     * @param fixedDividend  - The fixed dividend of the stock. Given as a decimal.
     * @param stockPrice     - The price of the stock in GBP.
     */
    public PreferredStock(final String stockSymbol,
                          final BigDecimal lastDividend,
                          final BigDecimal parValue,
                          final BigDecimal fixedDividend,
                          final BigDecimal stockPrice) {

        super(stockSymbol, lastDividend, parValue, stockPrice);
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    /**
     * Calculate the dividend yield for this PreferredStock given the parameter price.
     * <p/>
     * Preferred stocks use formula:  dividend yield = fixed dividend * par value/price
     * @param price
     * @return
     */
    @Override
    public BigDecimal calculateDividendYield(final BigDecimal price) {

        super.validatePrice(price);
        BigDecimal dividendYield = fixedDividend.multiply(parValue, MathContext.DECIMAL64).divide(price, MathContext.DECIMAL64);
        log.log(Level.ALL, String.format("Calculated dividend yield for PreferredStock %s as %s", stockSymbol, formatter.format(dividendYield)));

        return dividendYield;
    }

    /**
     * Equality check between this PreferredStock and another Object.
     *
     * @param o -  The object to compare against.
     * @return  -  boolean value indicating if the objects match.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferredStock that = (PreferredStock) o;

        return fixedDividend.equals(that.fixedDividend);

    }

    /**
     * Generate a hashcode value for this PreferredStock.
     *
     * @return - A hashcode value based on the fixedDividend member.
     */
    @Override
    public int hashCode() {
        return fixedDividend.hashCode();
    }

    /**
     * Generate a String representation of the object.
     *
     * @return - a String representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PreferredStock{");
        sb.append(super.toString());
        sb.append("fixedDividend=").append(super.formatter.format(fixedDividend));
        sb.append('}');
        return sb.toString();
    }
}
