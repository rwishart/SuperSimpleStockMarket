package stockmarket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.stock.CommonStock;
import stockmarket.stock.Stock;
import stockmarket.stocklisting.StockListing;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;
import stockmarket.tradedata.TradeDataService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit Test for the {@link SuperSimpleStockMarketImpl}. The class makes use of {@link Mockito} and
 * dummy data to exercise the {@link SuperSimpleStockMarket} functionality.
 *
 * @author Ryan Wishart
 */
public class TestSuperSimpleStockMarketImpl {

    private static final String TEA_STOCK_SYMBOL = "TEA";

    private SuperSimpleStockMarket simpleStockMarket;

    private TradeDataService tradeDataService;

    private StockMarketCalculationService stockMarketCalculationService;

    private StockListing stockListing;

    /**
     * Setup objects for testing.
     */
    @Before
    public void setUp() {

        tradeDataService = mock(TradeDataService.class);
        stockMarketCalculationService = mock(StockMarketCalculationService.class);
        stockListing = mock(StockListing.class);

        simpleStockMarket = new SuperSimpleStockMarketImpl(tradeDataService, stockMarketCalculationService, stockListing);
    }

    /**
     * Validate Dividend Yield is calculated correctly for a CommonStock.
     */
    @Test
    public void testDividendYieldForStock() {

        BigDecimal lastDividend = new BigDecimal(0.50, MathContext.DECIMAL64);
        BigDecimal price = new BigDecimal(123.45, MathContext.DECIMAL64);
        Stock dummyStock = new CommonStock(TEA_STOCK_SYMBOL, lastDividend, BigDecimal.TEN, BigDecimal.ONE);

        when(stockListing.isListedStock(TEA_STOCK_SYMBOL)).thenReturn(true);
        when(stockListing.getListedStock(TEA_STOCK_SYMBOL)).thenReturn(dummyStock);

        BigDecimal calculatedValue = simpleStockMarket.calculateDividendYieldForStock(TEA_STOCK_SYMBOL, price);

        assertEquals(lastDividend.divide(price, MathContext.DECIMAL64), calculatedValue);
    }

    /**
     * Validate calculation of the GBCE All Share Index with dummy data.
     */
    @Test
    public void testCalculateGBCEAllShareIndex() {

        Stock dummyStock = new CommonStock(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE);
        Collection<Stock> stocks = Arrays.asList(dummyStock);

        Collection<BigDecimal> prices = Arrays.asList(BigDecimal.ONE);

        when(stockListing.getAllListedStock()).thenReturn(stocks);
        when(stockMarketCalculationService.calculateGBCEAllShareIndexFromPrices(eq(prices))).thenReturn(BigDecimal.ONE);

        BigDecimal calculatedValue = simpleStockMarket.calculateGBCEAllShareIndex();

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    /**
     * Validate that the PE ratio is calculated correctly for a stock.
     */
    @Test
    public void testCalculatePERatioForStock() {

        Stock dummyStock = new CommonStock(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE);

        when(stockListing.isListedStock(eq(TEA_STOCK_SYMBOL))).thenReturn(true);
        when(stockListing.getListedStock(eq(TEA_STOCK_SYMBOL))).thenReturn(dummyStock);
        BigDecimal calculatedValue = simpleStockMarket.calculatePERatioForStock(TEA_STOCK_SYMBOL, BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, calculatedValue);
    }

    /**
     * Validate that the volume weighted stock price is calculated correctly.
     */
    @Test
    public void testCalculateVolumeWeightedStockPrice() {

        when(stockListing.isListedStock(TEA_STOCK_SYMBOL)).thenReturn(true);
        Trade trade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1, BuySellIndicator.BUY, BigDecimal.ONE);
        Set<Trade> tradesInInterval = Sets.newSet(trade, trade, trade);

        when(tradeDataService.getTradesForStockInInterval(eq(TEA_STOCK_SYMBOL), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(tradesInInterval);
        when(stockMarketCalculationService.calculateVolumeWeightedStockPrice(eq(tradesInInterval))).thenReturn(BigDecimal.TEN);

        BigDecimal calculatedValue = simpleStockMarket.calculateVolumeWeightedStockPrice(TEA_STOCK_SYMBOL);

        assertEquals(BigDecimal.TEN, calculatedValue);
    }

    /**
     * Validate that trades can be recorded.
     */
    @Test
    public void testRecordTrade() {

        when(stockListing.isListedStock(TEA_STOCK_SYMBOL)).thenReturn(true);

        Trade trade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 100L, BuySellIndicator.SELL, BigDecimal.TEN);
        simpleStockMarket.recordTrade(trade);

        //Verify that the recordTrade method is called on the tradeDataService exactly once.
        verify(tradeDataService, times(1)).recordTrade(eq(trade));
    }
}
