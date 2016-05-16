package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Definition of a CommonStock traded on the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public class CommonStock extends AbstractStock {

    public CommonStock(final String stockSymbol,
                       final BigDecimal lastDividend,
                       final BigDecimal parValue) {

        super(stockSymbol, lastDividend, parValue);
    }

    @Override
    public BigDecimal calculateDividendYield(final BigDecimal price) {

        super.validatePrice(price);

        return lastDividend.divide(price, MathContext.DECIMAL64);
    }
}
