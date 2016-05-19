package stockmarket.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit Test class for {@link CommonStock}.
 *
 * @author Ryan Wishart
 */
public class TestCommonStock {

    /**
     * Validate the getter methods on the CommonStock object.
     */
    @Test
    public void testStockGetters() {

        CommonStock stock = generateDummyStock();

        assertEquals("TEA", stock.getStockSymbol());
        assertEquals(new BigDecimal(0.5, MathContext.DECIMAL64), stock.getLastDividend());
        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), stock.getParValue());
        assertEquals(BigDecimal.ONE, stock.getStockPrice());

    }

    /**
     * Verify that a CommonStock can calculate its dividend given a price.
     */
    @Test
    public void testCalculateDividendYield() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.TEN));
    }

    /**
     * Verify that a CommonStock can calculate its dividend yield with a price of zero. An IllegalArgumentException
     * is expected as this is not a valid price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculateDividendYieldZeroValue() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.ZERO));
    }

    /**
     * Validate that the CommonStock throws and IllegalArgumentException when given a negative price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculateDividendYieldNegativeValue() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.ONE.negate()));
    }

    /**
     * Validate that the CommonStock throws and IllegalArgumentException when given a null price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculateDividendYieldNullPrice() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(null));
    }

    /**
     * Verify that a CommonStock can calculate a PE ratio.
     */
    @Test
    public void testCalculatePERatio() {

        CommonStock stock = generateDummyStock();

        BigDecimal price = new BigDecimal(10.5, MathContext.DECIMAL64);
        BigDecimal expectedValue = price.divide(stock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculatePERatio(price));
    }

    /**
     * Verify that {@link CommonStock#calculatePERatio}  throws an IllegalArgumentException when passed a price of zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatePERatioWithZero() {

        CommonStock stock = generateDummyStock();

        stock.calculatePERatio(BigDecimal.ZERO);
    }

    /**
     * Verify that the {@link CommonStock#calculatePERatio} throws an IllegalArgumentException when given a negative
     * price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatePERatioWithNegativeValue() {

        CommonStock stock = generateDummyStock();

        stock.calculatePERatio(BigDecimal.ONE.negate());
    }

    /**
     * Verify that the {@link CommonStock#calculatePERatio} throws an IllegalArgumentException when given a null
     * price.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCalculatePERatioWithNullPrice() {

        CommonStock stock = generateDummyStock();

        stock.calculatePERatio(null);
    }

    /// Helper methods
    private CommonStock generateDummyStock() {

        String stockSymbol = "TEA";
        BigDecimal lastDividend = new BigDecimal(0.50, MathContext.DECIMAL64);
        BigDecimal fixedDividend = new BigDecimal(0.02, MathContext.DECIMAL64);

        return new CommonStock(stockSymbol, lastDividend, fixedDividend, BigDecimal.ONE);
    }
}