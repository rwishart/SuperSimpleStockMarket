package stockmarket.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.calulator.StockMarketCalculationServiceImpl;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for the {@link StockMarketCalculationService} class.
 *
 * @author Ryan Wishart
 */
public class TestStockMarketCalculationService {

    private StockMarketCalculationService calculationService;

    private static final String TEA_STOCK_SYMBOL = "TEA";

    /**
     * Setup objects for the testing.
     */
    @Before
    public void setUp() {

        calculationService = new StockMarketCalculationServiceImpl();
    }

    /**
     * Validate that Volume Weighted stock price on an empty set returns zero
     */
    @Test
    public void testVolumeWeightedStockPriceEmptySet() {

        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Sets.newSet());

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    /**
     * Validate that the StockMarketCalculationService can calculate a value from a single trade.
     */
    @Test
    public void testVolumeWeightedStockPrice() {

        Trade dummyTrade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 100L, BuySellIndicator.BUY, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Sets.newSet(dummyTrade));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    /**
     * Test that the StockMarketCalculationService can calculate a value when provided with multiple trades.
     */
    @Test
    public void testVolumeWeightedStockPriceMultipleTrades() {

        Trade dummyTrade1 = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1L, BuySellIndicator.BUY, BigDecimal.ONE);
        Trade dummyTrade2 = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1L, BuySellIndicator.SELL, BigDecimal.ONE);
        Trade dummyTrade3 = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1L, BuySellIndicator.BUY, BigDecimal.ONE);

        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Sets.newSet(dummyTrade1, dummyTrade2, dummyTrade3));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    /**
     * Validate that the {@link StockMarketCalculationService#calculateGBCEAllShareIndexFromPrices} can handle an empty collection .
     */
    @Test
    public void testGBSECalculationNoPrices() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Collections.EMPTY_LIST);

        assertEquals(BigDecimal.ZERO, calculatedValue);
    }

    /**
     * Validate that the {@link StockMarketCalculationService#calculateGBCEAllShareIndexFromPrices} works with a single price.
     */
    @Test
    public void testGBSECalculationSinglePrice() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Arrays.asList(BigDecimal.ONE));

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    /**
     * Verify that the {@link StockMarketCalculationService#calculateGBCEAllShareIndexFromPrices} works with multiple prices.
     */
    @Test
    public void testGBSECalculationMultiplePrices() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Arrays.asList(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));

        BigDecimal expectedValue = BigDecimal.TEN.multiply(BigDecimal.TEN, MathContext.DECIMAL64);
        expectedValue = new BigDecimal(Math.pow(expectedValue.doubleValue(), 1/4), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }
}
