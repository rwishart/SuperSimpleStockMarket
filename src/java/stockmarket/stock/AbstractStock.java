package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Abstract implementation of the {@link Stock} interface. This class provides basic functionality required by a
 * {@link Stock}.
 *
 * @author Ryan Wishart
 */
abstract class AbstractStock implements Stock {

    String stockSymbol;

    BigDecimal lastDividend;

    BigDecimal parValue;

    AbstractStock(final String stockSymbol,
                 final BigDecimal lastDividend,
                 final BigDecimal parValue) {

        this.stockSymbol = stockSymbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    @Override
    public abstract BigDecimal calculateDividendYield(final BigDecimal price);

    @Override
    public BigDecimal calculatePERatio(final BigDecimal price) {

        validatePrice(price);

        return price.divide(lastDividend, MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal getParValue() {
        return parValue;
    }

    @Override
    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    @Override
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * This method validates that a parameter BigDecimal value is (1) not null and (2) not negative or zero.
     * <p/>
     * If these conditions are not met, an {@link IllegalArgumentException} is thrown.
     *
     * @param price - the price to validate.
     */
    protected void validatePrice(BigDecimal price) {

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Illegal argument provided to calculatePERatio method call. Price must be greater than zero.");
        }
    }
}
