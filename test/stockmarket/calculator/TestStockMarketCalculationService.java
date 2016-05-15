package stockmarket.calculator;

import com.sun.org.apache.bcel.internal.generic.POP;
import org.junit.Test;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.calulator.StockMarketCalculationServiceImpl;
import stockmarket.stock.Stock;
import stockmarket.stock.StockType;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit Test for the StockMarketCalculationService.
 *
 * @author Ryan Wishart
 */
public class TestStockMarketCalculationService {

    private StockMarketCalculationService calculationService;

    private static final String POP_STOCK_SYMBOL = "POP";

    private static final String TEA_STOCK_SYMBOL = "TEA";

    public TestStockMarketCalculationService() {

        calculationService = new StockMarketCalculationServiceImpl();
    }

    @Test
    public void testCalculatePERationForAStock(){

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculatePERatioForAStock(stock, BigDecimal.ONE);

        BigDecimal expectedPrice = BigDecimal.ONE;

        assertEquals(expectedPrice, calculatedPrice);
    }

    @Test
    public void testCalculatePERationForAStockWithZeroDividend(){

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculatePERatioForAStock(stock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    @Test
    public void testCalculatePERationForAStockWithZeroPrice(){

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ZERO);
        BigDecimal calculatedPrice = calculationService.calculatePERatioForAStock(stock, BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    @Test
    public void testCalculatePERationForAStockWithZeroPriceZeroDividend(){

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal calculatedPrice = calculationService.calculatePERatioForAStock(stock, BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    @Test
    public void testDividendYieldForAStock() {

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateDividendYieldForAStock(stock, BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    @Test
    public void testVolumeWeightedStockPriceEmptyCollection() {

        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Collections.EMPTY_LIST);

        assertEquals(BigDecimal.ZERO, calculatedPrice);
    }

    @Test
    public void testVolumeWeightedStockPrice() {

        Trade dummyTrade = generateTradeForTest(TEA_STOCK_SYMBOL, LocalDateTime.now(), BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Arrays.asList(dummyTrade));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    @Test
    public void testVolumeWeightedStockPriceMultipleTrades() {

        Trade dummyTrade = generateTradeForTest(TEA_STOCK_SYMBOL, LocalDateTime.now(), BigDecimal.ONE);
        BigDecimal calculatedPrice = calculationService.calculateVolumeWeightedStockPrice(Arrays.asList(dummyTrade, dummyTrade, dummyTrade));

        assertEquals(BigDecimal.ONE, calculatedPrice);
    }

    @Test
    public void testGBSECalculationNoStocks() {

        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndex(Collections.EMPTY_LIST);

        assertEquals(BigDecimal.ZERO, calculatedValue);
    }

    @Test
    public void testGBSECalculationSingleStock() {

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndex(Arrays.asList(stock));

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testGBSECalculationMultipleStocks() {

        Stock stock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndex(Arrays.asList(stock, stock, stock, stock));

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testGBSECalculationMultipleDifferentStocks() {

        Stock teaStock = generateStockForTest(TEA_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.TEN);
        Stock popStock = generateStockForTest(POP_STOCK_SYMBOL, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateGBCEAllShareIndex(Arrays.asList(teaStock, popStock, teaStock, popStock));

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testCalculateDividendYieldForCommonStock() {

        Stock commonStock = new Stock(TEA_STOCK_SYMBOL, StockType.COMMON, BigDecimal.ONE, null, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateDividendYieldForAStock(commonStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, calculatedValue);
    }

    @Test
    public void testCalculateDividendYieldForCommonStockZeroValues() {

        Stock commonStock = new Stock(TEA_STOCK_SYMBOL, StockType.COMMON, BigDecimal.ZERO, null, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateDividendYieldForAStock(commonStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedValue);

        commonStock = new Stock(TEA_STOCK_SYMBOL, StockType.COMMON, BigDecimal.ONE, null, BigDecimal.ZERO, BigDecimal.ONE);
        calculatedValue = calculationService.calculateDividendYieldForAStock(commonStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, calculatedValue);

        commonStock = new Stock(TEA_STOCK_SYMBOL, StockType.COMMON, BigDecimal.ONE, null, BigDecimal.ONE, BigDecimal.ZERO);
        calculatedValue = calculationService.calculateDividendYieldForAStock(commonStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedValue);
    }

    @Test
    public void testCalculateDividendYieldForPreferredStock() {

        Stock preferredStock = new Stock(TEA_STOCK_SYMBOL, StockType.PREFERRED, BigDecimal.ONE, new BigDecimal(0.02, MathContext.DECIMAL64), BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateDividendYieldForAStock(preferredStock, BigDecimal.ONE);

        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), calculatedValue);
    }

    @Test
    public void testCalculateDividendYieldForPreferredStockZeroValues() {

        Stock preferredStock = new Stock(TEA_STOCK_SYMBOL, StockType.PREFERRED, BigDecimal.ZERO, new BigDecimal(0.02, MathContext.DECIMAL64), BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal calculatedValue = calculationService.calculateDividendYieldForAStock(preferredStock, BigDecimal.ONE);

        assertEquals(new BigDecimal(0.02, MathContext.DECIMAL64), calculatedValue);

        preferredStock = new Stock(TEA_STOCK_SYMBOL, StockType.PREFERRED, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE);
        calculatedValue = calculationService.calculateDividendYieldForAStock(preferredStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedValue);

        preferredStock = new Stock(TEA_STOCK_SYMBOL, StockType.PREFERRED, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE);
        calculatedValue = calculationService.calculateDividendYieldForAStock(preferredStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedValue);

        preferredStock = new Stock(TEA_STOCK_SYMBOL, StockType.PREFERRED, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);
        calculatedValue = calculationService.calculateDividendYieldForAStock(preferredStock, BigDecimal.ONE);

        assertEquals(BigDecimal.ZERO, calculatedValue);
    }

    //// Helper methods
    private Stock generateStockForTest(String stockSymbol, BigDecimal lastDividend, BigDecimal stockPrice) {

        return new Stock(stockSymbol, StockType.COMMON, lastDividend, null, BigDecimal.ONE, stockPrice);
    }

    private Trade generateTradeForTest(String stockSymbol, LocalDateTime timestamp, BigDecimal price) {

        return new Trade(stockSymbol, timestamp, 1, BuySellIndicator.BUY, price);
    }
}
