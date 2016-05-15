package stockmarket;

import org.junit.Before;
import org.junit.Test;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.calulator.StockMarketCalculationServiceImpl;
import stockmarket.stock.Stock;
import stockmarket.stock.StockType;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;
import stockmarket.trade.service.TradeDataService;
import stockmarket.trade.service.TradeDataServiceImpl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration Test for the {@link SuperSimpleStockMarketImpl} class linking together the
 * various underlying classes. All the methods in the interface are exercised.
 *
 * @author Ryan Wishart
 */
public class ITSuperSimpleStockMarket {

    private SuperSimpleStockMarket simpleStockMarket;

    private Map<String, Stock> stockListings;

    private TradeDataService tradeDataService;

    @Before
    public void setUp() {

        tradeDataService = new TradeDataServiceImpl();
        StockMarketCalculationService stockMarketCalculationService = new StockMarketCalculationServiceImpl();
        stockListings = initStockListings();


        simpleStockMarket = new SuperSimpleStockMarketImpl(tradeDataService, stockMarketCalculationService, stockListings);
    }

    @Test
    public void testCalculateDividendYieldForStock() {

        Stock teaStock = stockListings.get("TEA");
        BigDecimal calculatedValue = simpleStockMarket.calculateDividendYieldForStock(teaStock, BigDecimal.TEN);
        assertEquals(BigDecimal.ZERO, calculatedValue);

        Stock popStock = stockListings.get("POP");
        popStock.setStockPrice(BigDecimal.TEN);
        calculatedValue = simpleStockMarket.calculateDividendYieldForStock(popStock, BigDecimal.TEN);
        assertEquals(popStock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64), calculatedValue);

        Stock ginStock = stockListings.get("GIN");
        ginStock.setStockPrice(BigDecimal.TEN);
        calculatedValue = simpleStockMarket.calculateDividendYieldForStock(ginStock, BigDecimal.TEN);

        BigDecimal expectedValue = ginStock.getFixedDividend().multiply(ginStock.getParValue(), MathContext.DECIMAL64).divide(BigDecimal.TEN, MathContext.DECIMAL64);
        assertEquals(expectedValue, calculatedValue);

    }

    @Test
    public void testRecordTrade() {

        Trade dummyTrade = new Trade("TEA", LocalDateTime.now(), 100, BuySellIndicator.BUY, BigDecimal.TEN);
        simpleStockMarket.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval("TEA", LocalDateTime.now(), LocalDateTime.now().minusMinutes(10));
        assertEquals(1, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(dummyTrade));
    }

    @Test
    public void testVolumeWeightedStockPrice() {

        generateAndRegisterTradesForStock("TEA");

        Stock teaStock = stockListings.get("TEA");
        BigDecimal calculatedValue = simpleStockMarket.calculateVolumeWeightedStockPrice(teaStock);

        BigDecimal four = new BigDecimal(4);
        BigDecimal hundred = new BigDecimal(100);

        BigDecimal expectedValue = four.multiply(BigDecimal.TEN.multiply(hundred,MathContext.DECIMAL64).divide(four.multiply(hundred), MathContext.DECIMAL64));

        assertEquals(0, expectedValue.compareTo(calculatedValue));
    }

    @Test
    public void testCalculationOfGBCEAllShareIndex() {

        generateAndRegisterTradesForStock("TEA");
        generateAndRegisterTradesForStock("POP");
        generateAndRegisterTradesForStock("ALE");
        generateAndRegisterTradesForStock("GIN");
        generateAndRegisterTradesForStock("JOE");

        BigDecimal calculatedValue = simpleStockMarket.calculateGBCEAllShareIndex();

        double base = 5 * 100;
        BigDecimal expectedValue = new BigDecimal(Math.pow(base, 1/5), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void testCalculatePERatioForStock() {

        Stock teaStock = stockListings.get("TEA");
        BigDecimal calculatedValue = simpleStockMarket.calculatePERatioForStock(teaStock, BigDecimal.TEN);
        BigDecimal expectedValue = BigDecimal.ZERO;

        assertEquals(expectedValue, calculatedValue);

        Stock popStock = stockListings.get("POP");
        popStock.setStockPrice(BigDecimal.TEN);
        calculatedValue = simpleStockMarket.calculatePERatioForStock(popStock, BigDecimal.TEN);
        expectedValue = BigDecimal.TEN.divide(popStock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }

    // Helper methods ///

    /**
     * Initialisation method for the Stock Markets stock listings.
     *
     * @author Ryan Wishart
     *
     * @return Map  - data structure representing the listed stocks listed in the stock market.
     */
    private Map<String, Stock> initStockListings() {

        Stock teaStock = new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, null, BigDecimal.ONE, BigDecimal.ZERO);
        Stock popStock = new Stock("POP", StockType.COMMON, new BigDecimal(0.08, MathContext.DECIMAL64), null, BigDecimal.ONE, BigDecimal.ZERO);
        Stock aleStock = new Stock("ALE", StockType.COMMON, new BigDecimal(0.23, MathContext.DECIMAL64), null, new BigDecimal(0.6, MathContext.DECIMAL64), BigDecimal.ZERO);
        Stock ginStock = new Stock("GIN", StockType.PREFERRED, new BigDecimal(0.08, MathContext.DECIMAL64), new BigDecimal(0.02, MathContext.DECIMAL64), BigDecimal.ONE, BigDecimal.ZERO);
        Stock joeStock = new Stock("JOE", StockType.COMMON, new BigDecimal(0.13, MathContext.DECIMAL64), null, new BigDecimal(2.5, MathContext.DECIMAL64), BigDecimal.ZERO);

        Map<String, Stock> stockListings = new HashMap<>();
        stockListings.put("TEA", teaStock);
        stockListings.put("POP", popStock);
        stockListings.put("ALE", aleStock);
        stockListings.put("GIN", ginStock);
        stockListings.put("JOE", joeStock);

        return stockListings;
    }

    private void generateAndRegisterTradesForStock(String stockSymbol) {

        Trade trade = new Trade(stockSymbol, LocalDateTime.now(), 100, BuySellIndicator.BUY, BigDecimal.TEN);
        Collection<Trade> trades = Arrays.asList(trade, trade, trade, trade);

        trades.stream().forEach(t -> tradeDataService.recordTrade(t));
    }
}
