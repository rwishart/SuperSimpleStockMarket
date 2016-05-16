package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Concrete implementation of a Preferred Stock.
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
}
