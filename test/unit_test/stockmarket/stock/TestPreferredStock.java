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


    @Test
    public void testGetters() {

        PreferredStock stock = generateStock();

        assertEquals("GIN", stock.getStockSymbol());
        assertEquals(new BigDecimal(0.08, MathContext.DECIMAL64), stock.getLastDividend());
        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), stock.getFixedDividend());
        assertEquals(new BigDecimal(1, MathContext.DECIMAL64), stock.getParValue());
    }

    @Test
    public void testCalculatedDividendYield() {

        PreferredStock stock = generateStock();

        BigDecimal price = new BigDecimal(15.61, MathContext.DECIMAL64);
        BigDecimal expectedValue = stock.getFixedDividend().multiply(stock.getParValue()).divide(price, MathContext.DECIMAL64);
        assertEquals(expectedValue, stock.calculateDividendYield(price));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedDividendYieldZeroValue() {

        PreferredStock stock = generateStock();

        stock.calculateDividendYield(BigDecimal.ZERO);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedDividendYieldNegativeValue() {

        PreferredStock stock = generateStock();

        stock.calculateDividendYield(BigDecimal.ONE.negate());
    }

    @Test
    public void testCalculatedPERatio() {

        PreferredStock stock = generateStock();

        BigDecimal price = new BigDecimal(1234.56, MathContext.DECIMAL64);
        BigDecimal expectedValue = price.divide(stock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculatePERatio(price));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedPERatioZeroPrice() {

        PreferredStock stock = generateStock();
        stock.calculatePERatio(BigDecimal.ZERO);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatedPERatioNegativePrice() {

        PreferredStock stock = generateStock();
        stock.calculatePERatio(BigDecimal.ONE.negate());
    }

    //// Helper methods
    private PreferredStock generateStock() {

        return new PreferredStock("GIN", new BigDecimal(0.08, MathContext.DECIMAL64),
                new BigDecimal(1, MathContext.DECIMAL64), new BigDecimal(0.02, MathContext.DECIMAL64));
    }
}
