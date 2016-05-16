package stockmarket.stocklisting;

import org.junit.Before;
import org.junit.Test;
import stockmarket.stock.CommonStock;
import stockmarket.stock.Stock;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit Test for the {@link SimpleStockListing}. Exercises all the class methods.
 *
 * @author Ryan Wishart
 */
public class TestSimpleStockListing {

    private StockListing stockListing;

    @Before
    public void setUp(){

        stockListing = new SimpleStockListing();
    }

    @Test
    public void testListAndGetListedStock() {

        //Stock not found, null should be returned
        Stock retrievedStock = stockListing.getListedStock("TEA");
        assertNull(retrievedStock);

        Stock teaStock = new CommonStock("TEA", BigDecimal.ZERO, BigDecimal.ONE);
        stockListing.listStock(teaStock);
        retrievedStock = stockListing.getListedStock("TEA");

        assertEquals(teaStock, retrievedStock);
    }

    @Test
    public void testGetAllListedStock() {

        Stock testStock = new CommonStock("TEA", BigDecimal.ZERO, BigDecimal.ONE);

        stockListing.listStock(testStock);

        assertEquals(1, stockListing.getAllListedStock().size());
        assertTrue(stockListing.getAllListedStock().contains(testStock));
    }
}
