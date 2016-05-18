package stockmarket.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test class for the {@link PreferredStock} class.
 *
 * @author Ryan Wishart
 */
public class TestPreferredStock {


    /**
     * Validate the getters on the PreferredStock object.
     */
    @Test
    public void testGetters() {

        PreferredStock stock = generateStock();

        assertEquals("GIN", stock.getStockSymbol());
        assertEquals(new BigDecimal(0.08, MathContext.DECIMAL64), stock.getLastDividend());
        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), stock.getFixedDividend());
        assertEquals(new BigDecimal(1, MathContext.DECIMAL64), stock.getParValue());
        assertEquals(BigDecimal.ONE, stock.getStockPrice());
    }

    /**
     * Validate the {@PreferredStock#calculateDividendYield} method returns correct answer when the parameter price is valid.
     */
    @Test
    public void testCalculatedDividendYield() {

        PreferredStock stock = generateStock();

        BigDecimal price = new BigDecimal(15.61, MathContext.DECIMAL64);
        BigDecimal expectedValue = stock.getFixedDividend().multiply(stock.getParValue()).divide(price, MathContext.DECIMAL64);
        assertEquals(expectedValue, stock.calculateDividendYield(price));
    }

    /**
     * Verify that an IllegalArgumentException is thrown when a price of zero is given to the {@PreferredStock#calculateDividendYield}
     * method.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedDividendYieldZeroValue() {

        PreferredStock stock = generateStock();

        stock.calculateDividendYield(BigDecimal.ZERO);
    }

    /**
     * Verift that an IllegalArgumentException is thrown when {@PreferredStock#calculateDividendYield} is called with a negative price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedDividendYieldNegativeValue() {

        PreferredStock stock = generateStock();

        stock.calculateDividendYield(BigDecimal.ONE.negate());
    }

    /**
     * Verift that an IllegalArgumentException is thrown when {@PreferredStock#calculateDividendYield} is called with a null price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedDividendYieldNullPrice() {

        PreferredStock stock = generateStock();

        stock.calculateDividendYield(null);
    }

    /**
     * Check that the {@PreferredStock#calculatePERatio} calculates the correct PE value when given a valid price.
     */
    @Test
    public void testCalculatedPERatio() {

        PreferredStock stock = generateStock();

        BigDecimal price = new BigDecimal(1234.56, MathContext.DECIMAL64);
        BigDecimal expectedValue = price.divide(stock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculatePERatio(price));
    }

    /**
     * Verify that the {@PreferredStock#calculatePERatio} throws an IllegalArgumentException when given a price of zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedPERatioZeroPrice() {

        PreferredStock stock = generateStock();
        stock.calculatePERatio(BigDecimal.ZERO);
    }

    /**
     * Check that the {@PreferredStock#calculatePERatio} throws an IllegalArgumentException when given a negative price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedPERatioNegativePrice() {

        PreferredStock stock = generateStock();
        stock.calculatePERatio(BigDecimal.ONE.negate());
    }

    /**
     * Check that the {@PreferredStock#calculatePERatio} throws an IllegalArgumentException when given a null price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedPERatioNullPrice() {

        PreferredStock stock = generateStock();
        stock.calculatePERatio(null);
    }

    //// Helper methods
    private PreferredStock generateStock() {

        return new PreferredStock("GIN", new BigDecimal(0.08, MathContext.DECIMAL64),
                new BigDecimal(1, MathContext.DECIMAL64), new BigDecimal(0.02, MathContext.DECIMAL64), BigDecimal.ONE);
    }
}
