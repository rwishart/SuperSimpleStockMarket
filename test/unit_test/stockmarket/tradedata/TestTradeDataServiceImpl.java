package stockmarket.tradedata;

import org.junit.Before;
import org.junit.Test;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test class for the {@link TradeDataServiceImpl} class.
 *
 * @author Ryan Wishart
 */
public class TestTradeDataServiceImpl {

    private TradeDataService tradeDataService;
    
    private static final String TEA_STOCK_SYMBOL = "TEA";

    /**
     * Set up objects for the test.
     */
    @Before
    public void setUp() {

        tradeDataService = new TradeDataServiceImpl();
    }

    /**
     * Verify that an IllegalArgumentException is thrown when a null value is passed to the {@link TradeDataService#recordTrade method.}
     */
    @Test (expected = IllegalArgumentException.class)
    public void testRetrieveTradeNullArgument() {

        tradeDataService.recordTrade(null);
    }

    /**
     * Check that a trade within the time interval is retrieved.
     */
    @Test
    public void testRetrieveTradeInsideInterval() {

        LocalDateTime now = LocalDateTime.now();
        Trade dummyTrade = generateTestTrade(TEA_STOCK_SYMBOL, now, BigDecimal.TEN);
        tradeDataService.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(1, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(dummyTrade));

    }

    /**
     * Test that no trades are retrieved outside of the time interval.
     */
    @Test
    public void testRetrieveTradeOutsideInterval() {

        LocalDateTime now = LocalDateTime.now();
        Trade dummyTrade = generateTestTrade(TEA_STOCK_SYMBOL, now.minusMinutes(30), BigDecimal.TEN);
        tradeDataService.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(0, retrievedTrades.size());
    }

    /**
     * Verify that only the trades on or within the boundary window are retrieved.
     */
    @Test
    public void testRetrieveTradeOnIntervalBoundaries() {

        LocalDateTime now = LocalDateTime.now();
        Trade trade1 = generateTestTrade(TEA_STOCK_SYMBOL, now, BigDecimal.TEN);
        Trade trade2 = generateTestTrade(TEA_STOCK_SYMBOL, now.minusMinutes(15), BigDecimal.TEN);

        tradeDataService.recordTrade(trade1);
        tradeDataService.recordTrade(trade2);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(2, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(trade1));
        assertTrue(retrievedTrades.contains(trade2));
    }

    /**
     * Verify that only stock with the correct stockSymbol are retrieved.
     */
    @Test
    public void testRetrieveTradesFiltersByStockSymbol() {

        LocalDateTime now = LocalDateTime.now();
        Trade trade1 = generateTestTrade("POP", now, BigDecimal.TEN);
        Trade trade2 = generateTestTrade(TEA_STOCK_SYMBOL, now.minusMinutes(15), BigDecimal.TEN);

        tradeDataService.recordTrade(trade1);
        tradeDataService.recordTrade(trade2);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(1, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(trade2));
    }

    // Helper methods
    private Trade generateTestTrade(String stockSymbol, LocalDateTime timestamp, BigDecimal price) {

        return new Trade(stockSymbol, timestamp, 1, BuySellIndicator.BUY, price);
    }
}
