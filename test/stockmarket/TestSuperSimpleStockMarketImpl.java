package stockmarket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.stock.Stock;
import stockmarket.stock.StockType;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;
import stockmarket.trade.service.TradeDataService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit Test for the {@link SuperSimpleStockMarketImpl}. The class makes use of {@link Mockito} and
 * dummy data to exercise the SuperSimpleStockMarket functionality.
 *
 * @author Ryan Wishart
 */
public class TestSuperSimpleStockMarketImpl {

    private static final String TEA_STOCK_SYMBOL = "TEA";
    private SuperSimpleStockMarket simpleStockMarket;

    private TradeDataService tradeDataService;

    private StockMarketCalculationService stockMarketCalculationService;

    private Map<String, Stock> stockData;

    @Before
    public void setUp() {

        tradeDataService = mock(TradeDataService.class);
        stockMarketCalculationService = mock(StockMarketCalculationService.class);
        stockData = mock(Map.class);

        simpleStockMarket = new SuperSimpleStockMarketImpl(tradeDataService, stockMarketCalculationService, stockData);
    }

    @Test
    public void testDividendYieldForStock() {

        Stock dummyStock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.TEN);
        BigDecimal price = new BigDecimal(123.45);
        when(simpleStockMarket.calculateDividendYieldForStock(eq(dummyStock), eq(price))).thenReturn(BigDecimal.ONE);

        BigDecimal calculatedValue = simpleStockMarket.calculateDividendYieldForStock(dummyStock, price);

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testCalculateGBCEAllShareIndex() {

        Stock dummyStock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.TEN);
        Collection<Stock> stocks = Arrays.asList(dummyStock);
        when(stockData.values()).thenReturn(stocks);
        when(stockMarketCalculationService.calculateGBCEAllShareIndex(eq(stocks))).thenReturn(BigDecimal.ONE);

        BigDecimal calculatedValue = simpleStockMarket.calculateGBCEAllShareIndex();

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testCalculatePERRatioForStock() {

        Stock dummyStock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.TEN);
        when(stockMarketCalculationService.calculatePERatioForAStock(eq(dummyStock), eq(BigDecimal.TEN))).thenReturn(BigDecimal.ONE);

        BigDecimal calculatedValue = simpleStockMarket.calculatePERatioForStock(dummyStock, BigDecimal.TEN);

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testCalculateVolumeWeightedStockPrice() {

        Stock dummyStock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.TEN);
        Trade trade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1, BuySellIndicator.BUY, BigDecimal.ONE);
        Collection<Trade> tradesInInterval = Arrays.asList(trade, trade, trade);

        when(tradeDataService.getTradesForStockInInterval(eq(TEA_STOCK_SYMBOL), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(tradesInInterval);
        when(stockMarketCalculationService.calculateVolumeWeightedStockPrice(eq(tradesInInterval))).thenReturn(BigDecimal.TEN);

        BigDecimal calculatedValue = simpleStockMarket.calculateVolumeWeightedStockPrice(dummyStock);

        assertEquals(BigDecimal.TEN, calculatedValue);
    }

    @Test
    public void testRecordTrade() {

        Trade trade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1, BuySellIndicator.BUY, BigDecimal.ONE);

        simpleStockMarket.recordTrade(trade);

        //Verify that the recordTrade method is called on the tradeDataService
        verify(tradeDataService, times(1)).recordTrade(eq(trade));
    }

    //TODO: this should be abstracted in to a base test class
    //// Helper methods
    private Stock generateStockForTest(String stockSymbol, BigDecimal lastDividend, BigDecimal stockPrice) {
        return new Stock(stockSymbol, StockType.COMMON, lastDividend, null, BigDecimal.ONE, stockPrice);
    }

}
