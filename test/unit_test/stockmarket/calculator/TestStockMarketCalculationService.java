package stockmarket.calculator;

import org.junit.Test;
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

    public TestStockMarketCalculationService() {

        calculationService = new StockMarketCalculationServiceImpl();
    }


    @Test
    public void testVolumeWeightedStockPriceEmptyCollection() {

        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Collections.EMPTY_LIST);

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    @Test
    public void testVolumeWeightedStockPrice() {

        Trade dummyTrade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 100L, BuySellIndicator.BUY, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Arrays.asList(dummyTrade));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    @Test
    public void testVolumeWeightedStockPriceMultipleTrades() {

        Trade dummyTrade = new Trade(TEA_STOCK_SYMBOL, LocalDateTime.now(), 1L, BuySellIndicator.BUY, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Arrays.asList(dummyTrade, dummyTrade, dummyTrade));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    @Test
    public void testGBSECalculationNoStocks() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Collections.EMPTY_LIST);

        assertEquals(BigDecimal.ZERO, calculatedValue);
    }

    @Test
    public void testGBSECalculationSinglePrice() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Arrays.asList(BigDecimal.ONE));

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testGBSECalculationMultipleStocks() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Arrays.asList(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));

        BigDecimal expectedValue = BigDecimal.TEN.multiply(BigDecimal.TEN, MathContext.DECIMAL64);
        expectedValue = new BigDecimal(Math.pow(expectedValue.doubleValue(), 1/4), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void testGBSECalculationMultipleDifferentStocks() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndexFromPrices(Arrays.asList(BigDecimal.ONE, BigDecimal.TEN));
        BigDecimal expectedValue = new BigDecimal(Math.pow(BigDecimal.TEN.doubleValue(), 1/2), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }
}
