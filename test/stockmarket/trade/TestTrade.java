package stockmarket.trade;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit Test for the {@link Trade} class.
 *
 * @author Ryan Wishart
 */
public class TestTrade {

    @Test
    public void testTradeGettersSetters() {

        String stockSymbol = "TEA";
        LocalDateTime now = LocalDateTime.now();
        long quantity = 1000L;
        BuySellIndicator indicator = BuySellIndicator.BUY;
        BigDecimal price = new BigDecimal(1234.56, MathContext.DECIMAL64);

        Trade trade = new Trade(stockSymbol, now, quantity, indicator, price);

        assertEquals(stockSymbol, trade.getStockSymbol());
        assertEquals(now, trade.getTimestamp());
        assertEquals(quantity, trade.getQuantityOfShares());
        assertEquals(indicator, trade.getBuySellIndicator());
        assertEquals(price, trade.getTradedPrice());
    }
}
