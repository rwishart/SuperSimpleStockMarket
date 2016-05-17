package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Concrete implementation of a Preferred Stock.
 * <p/>
 * Preferred stocks have a fixed dividend value that is used when calculating dividend yield.
 *
 * @author Ryan Wishart
 */
public class PreferredStock extends AbstractStock {

    private BigDecimal fixedDividend;

    public PreferredStock(final String stockSymbol,
                          final BigDecimal lastDividend,
                          final BigDecimal parValue,
                          final BigDecimal fixedDividend) {

        super(stockSymbol, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    @Override
    public BigDecimal calculateDividendYield(final BigDecimal price) {

        super.validatePrice(price);

        return (fixedDividend.multiply(parValue, MathContext.DECIMAL64)).divide(price, MathContext.DECIMAL64);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferredStock that = (PreferredStock) o;

        return fixedDividend.equals(that.fixedDividend);

    }

    @Override
    public int hashCode() {
        return fixedDividend.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PreferredStock{");
        sb.append(super.toString());
        sb.append("fixedDividend=").append(fixedDividend);
        sb.append('}');
        return sb.toString();
    }
}
