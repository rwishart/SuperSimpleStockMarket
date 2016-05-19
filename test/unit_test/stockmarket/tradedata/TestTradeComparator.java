package stockmarket.tradedata;

import org.junit.Test;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the {@link TradeComparator} to verify functionality.
 *
 * @author Ryan Wishart
 */
public class TestTradeComparator {

    /**
     * Validate the behaviour of the TradeComparator with null values
     * and valid {@link Trade}s.
     */
    @Test
    public void test(){

        TradeComparator comparator = new TradeComparator();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);

        Trade first = new Trade("TEA", now, 100L, BuySellIndicator.BUY, BigDecimal.TEN);
        Trade second = new Trade("TEA", later, 100L, BuySellIndicator.BUY, BigDecimal.TEN);

        assertEquals(0, comparator.compare(null, null));
        assertEquals(-1, comparator.compare(null, first));
        assertEquals(1, comparator.compare(first, null));
        assertEquals(0, comparator.compare(first, first));
        assertEquals(1, comparator.compare(second, first));
        assertEquals(-1, comparator.compare(first, second));
    }
}
