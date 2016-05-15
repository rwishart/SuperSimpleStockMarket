package stockmarket.stock;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for {@link Stock}.
 *
 * @author Ryan Wishart
 */
public class TestStock {

    @Test
    public void testStockGettersAndSetters() {

        String stockSymbol = "TEA";
        StockType stockType = StockType.COMMON;
        BigDecimal lastDividend = new BigDecimal(0.50, MathContext.DECIMAL64);
        BigDecimal fixedDividend = new BigDecimal(0.02, MathContext.DECIMAL64);
        BigDecimal parValue = new BigDecimal(1.00, MathContext.DECIMAL64);
        BigDecimal stockPrice = new BigDecimal(1234.56, MathContext.DECIMAL64);

        Stock stock = new Stock(stockSymbol, stockType, lastDividend, fixedDividend, parValue, stockPrice);

        assertEquals(stockSymbol, stock.getStockSymbol());
        assertEquals(stockType, stock.getStockType());
        assertEquals(lastDividend, stock.getLastDividend());
        assertEquals(fixedDividend, stock.getFixedDividend());
        assertEquals(parValue, stock.getParValue());
        assertEquals(stockPrice, stock.getStockPrice());
    }
}

