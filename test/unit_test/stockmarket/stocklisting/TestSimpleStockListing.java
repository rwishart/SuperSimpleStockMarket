package stockmarket.stocklisting;

import org.junit.Before;
import org.junit.Test;
import stockmarket.stock.CommonStock;
import stockmarket.stock.Stock;

import java.math.BigDecimal;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit Test for the {@link SimpleStockListing}. Exercises all the interface methods.
 *
 * @author Ryan Wishart
 */
public class TestSimpleStockListing {

    private StockListing stockListing;

    /**
     * create objects for the testing.
     */
    @Before
    public void setUp(){

        stockListing = new SimpleStockListing();
    }

    /**
     * Verify that the SimpleStockListing can list and retrieve a Stock.
     */
    @Test
    public void testListAndGetListedStock() {

        //Stock not found, null should be returned
        Stock retrievedStock = stockListing.getListedStock("TEA");
        assertNull(retrievedStock);

        Stock teaStock = new CommonStock("TEA", BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE);
        stockListing.listStock(teaStock);
        retrievedStock = stockListing.getListedStock("TEA");

        assertEquals(teaStock, retrievedStock);
    }

    /**
     * Test that all listed Stocks are retrieved on a call to {@link SimpleStockListing#getAllListedStock}.
     */
    @Test
    public void testGetAllListedStock() {

        Stock stock1 = new CommonStock("TEA", BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE);
        Stock stock2 = new CommonStock("POP", BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE);

        stockListing.listStock(stock1);
        stockListing.listStock(stock2);

        assertEquals(2, stockListing.getAllListedStock().size());
        assertTrue(stockListing.getAllListedStock().containsAll(Arrays.asList(stock1,stock2)));
    }
}
