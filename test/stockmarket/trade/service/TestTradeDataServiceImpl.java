package stockmarket.trade.service;

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
 * Unit test class fro the {@link TradeDataServiceImpl} class.
 *
 * @author Ryan Wishart
 */
public class TestTradeDataServiceImpl {

    private TradeDataService tradeDataService;
    
    private static final String TEA_STOCK_SYMBOL = "TEA";

    @Before
    public void setUp() {

        tradeDataService = new TradeDataServiceImpl();
    }

    @Test (expected = NullPointerException.class)
    public void testRetrieveTradeNullArgument() {

        tradeDataService.recordTrade(null);
    }

    @Test
    public void testRetrieveTradeInsideInterval() {

        LocalDateTime now = LocalDateTime.now();
        Trade dummyTrade = generateTestTrade(TEA_STOCK_SYMBOL, now, BigDecimal.TEN);
        tradeDataService.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(1, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(dummyTrade));

    }

    @Test
    public void testRetrieveTradeOutsideInterval() {

        LocalDateTime now = LocalDateTime.now();
        Trade dummyTrade = generateTestTrade(TEA_STOCK_SYMBOL, now.minusMinutes(30), BigDecimal.TEN);
        tradeDataService.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval(TEA_STOCK_SYMBOL, now, now.minusMinutes(15));

        assertEquals(0, retrievedTrades.size());
    }

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
