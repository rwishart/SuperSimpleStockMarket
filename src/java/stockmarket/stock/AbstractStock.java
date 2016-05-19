package stockmarket.stock;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract implementation of the {@link Stock} interface. This class provides basic functionality required by a
 * {@link Stock}.
 *
 * @author Ryan Wishart
 */
abstract class AbstractStock implements Stock {

    protected static final DecimalFormat formatter = new DecimalFormat("#,##0.00");

    protected static final Logger log = Logger.getLogger("AbstractStock");

    protected String stockSymbol;

    protected BigDecimal lastDividend;

    protected BigDecimal parValue;

    protected BigDecimal stockPrice;

    AbstractStock(final String stockSymbol,
                  final BigDecimal lastDividend,
                  final BigDecimal parValue,
                  final BigDecimal stockPrice) {

        this.stockSymbol = stockSymbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
        this.stockPrice = stockPrice;
    }

    @Override
    public abstract BigDecimal calculateDividendYield(final BigDecimal price);

    @Override
    public BigDecimal calculatePERatio(final BigDecimal price) {

        validatePrice(price);
        BigDecimal peRatio = price.divide(lastDividend, MathContext.DECIMAL64);
        log.log(Level.ALL, String.format("Calculated PE ratio for stock %s as %s", stockSymbol, formatter.format(peRatio)));

        return peRatio;
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

    @Override
    public BigDecimal getStockPrice() { return stockPrice; }

    /**
     * This method validates that a parameter BigDecimal value is (1) not null, (2) not negative and (3) not zero.
     * <p/>
     * If these conditions are not met, an {@link IllegalArgumentException} is thrown.
     *
     * @throws IllegalArgumentException
     * @param price - the price to validate.
     */
    protected void validatePrice(BigDecimal price) {

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            log.log(Level.ALL, "Illegal price value provided. Price is null, zero or negative.");
            throw new IllegalArgumentException("Illegal argument price value provided. Price must be non-null and greater than zero.");
        }
    }

    /**
     * Generate a String representation of the object.
     *
     * @return - A String representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractStock{");
        sb.append("stockSymbol='").append(stockSymbol).append('\'');
        sb.append(", lastDividend=").append(formatter.format(lastDividend));
        sb.append(", parValue=").append(formatter.format(parValue));
        sb.append(", stockPrice=").append(formatter.format(stockPrice));
        sb.append('}');
        return sb.toString();
    }

    /**
     * Equals method comparing member variables.
     *
     * @param o -  The object to compare against.
     * @return  -  boolean value indicating if the objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractStock that = (AbstractStock) o;

        if (stockSymbol != null ? !stockSymbol.equals(that.stockSymbol) : that.stockSymbol != null) return false;
        if (lastDividend != null ? !lastDividend.equals(that.lastDividend) : that.lastDividend != null) return false;
        if (parValue != null ? !parValue.equals(that.parValue) : that.parValue != null) return false;
        return stockPrice != null ? stockPrice.equals(that.stockPrice) : that.stockPrice == null;

    }

    /**
     * Generate a hashcode for the object using member fields.
     *
     * @return - integer value representing hascode for the object.
     */
    @Override
    public int hashCode() {
        int result = stockSymbol != null ? stockSymbol.hashCode() : 0;
        result = 31 * result + (lastDividend != null ? lastDividend.hashCode() : 0);
        result = 31 * result + (parValue != null ? parValue.hashCode() : 0);
        result = 31 * result + (stockPrice != null ? stockPrice.hashCode() : 0);
        return result;
    }
}
