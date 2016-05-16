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

    @Test
    public void testStockGetters() {

        CommonStock stock = generateDummyStock();

        assertEquals("TEA", stock.getStockSymbol());
        assertEquals(new BigDecimal(0.5, MathContext.DECIMAL64), stock.getLastDividend());
        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), stock.getParValue());

    }

    @Test
    public void testCalculateDividendYield() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.TEN));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculateDividendYieldZeroValue() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.ZERO));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculateDividendYieldNegativeValue() {

        CommonStock stock = generateDummyStock();
        BigDecimal expectedValue = stock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculateDividendYield(BigDecimal.ONE.negate()));
    }

    @Test
    public void testCalculatePERatio() {

        CommonStock stock = generateDummyStock();

        BigDecimal price = new BigDecimal(10.5, MathContext.DECIMAL64);
        BigDecimal expectedValue = price.divide(stock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, stock.calculatePERatio(price));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatePERatioWithZero() {

        CommonStock stock = generateDummyStock();

        stock.calculatePERatio(BigDecimal.ZERO);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculatePERatioWithNegativeValue() {

        CommonStock stock = generateDummyStock();

        stock.calculatePERatio(BigDecimal.ONE.negate());
    }

    /// Helper methods

    private CommonStock generateDummyStock() {

        String stockSymbol = "TEA";
        BigDecimal lastDividend = new BigDecimal(0.50, MathContext.DECIMAL64);
        BigDecimal fixedDividend = new BigDecimal(0.02, MathContext.DECIMAL64);

        return new CommonStock(stockSymbol, lastDividend, fixedDividend);
    }
}