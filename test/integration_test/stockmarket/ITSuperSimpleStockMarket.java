package stockmarket;


import org.junit.Before;
import org.junit.Test;
import stockmarket.calulator.StockMarketCalculationService;
import stockmarket.calulator.StockMarketCalculationServiceImpl;
import stockmarket.stock.CommonStock;
import stockmarket.stock.PreferredStock;
import stockmarket.stock.Stock;
import stockmarket.stocklisting.SimpleStockListing;
import stockmarket.stocklisting.StockListing;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;
import stockmarket.tradedata.TradeDataService;
import stockmarket.tradedata.TradeDataServiceImpl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

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

    private StockListing listing;

    private TradeDataService tradeDataService;

    @Before
    public void setUp() {

        tradeDataService = new TradeDataServiceImpl();
        StockMarketCalculationService stockMarketCalculationService = new StockMarketCalculationServiceImpl();
        listing = initlisting();
        simpleStockMarket = new SuperSimpleStockMarketImpl(tradeDataService, stockMarketCalculationService, listing);
    }

    @Test
    public void testCalculateDividendYieldForStock() {

        BigDecimal calculatedValue = simpleStockMarket.calculateDividendYieldForStock("TEA", BigDecimal.TEN);
        assertEquals(BigDecimal.ZERO, calculatedValue);

        calculatedValue = simpleStockMarket.calculateDividendYieldForStock("POP", BigDecimal.TEN);
        Stock popStock = listing.getListedStock("POP");
        assertEquals(popStock.getLastDividend().divide(BigDecimal.TEN, MathContext.DECIMAL64), calculatedValue);

        calculatedValue = simpleStockMarket.calculateDividendYieldForStock("GIN", BigDecimal.TEN);

        PreferredStock ginStock = (PreferredStock) listing.getListedStock("GIN");
        BigDecimal expectedValue = ginStock.getFixedDividend().multiply(ginStock.getParValue(), MathContext.DECIMAL64).divide(BigDecimal.TEN, MathContext.DECIMAL64);
        assertEquals(expectedValue, calculatedValue);

    }

    @Test
    public void testRecordTrade() {

        LocalDateTime now = LocalDateTime.now();
        Trade dummyTrade = new Trade("TEA", now, 100L, BuySellIndicator.BUY, BigDecimal.TEN);

        simpleStockMarket.recordTrade(dummyTrade);

        Collection<Trade> retrievedTrades = tradeDataService.getTradesForStockInInterval("TEA", now, now.minusMinutes(10));
        assertEquals(1, retrievedTrades.size());
        assertTrue(retrievedTrades.contains(dummyTrade));
    }

    @Test
    public void testVolumeWeightedStockPrice() {

        generateAndRegisterTradesForStock("TEA");

        BigDecimal calculatedValue = simpleStockMarket.calculateVolumeWeightedStockPrice("TEA");

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

    @Test(expected=ArithmeticException.class)
    public void testCalculatePERatioForStock() {

        simpleStockMarket.calculatePERatioForStock("TEA", BigDecimal.TEN);

    }

    @Test
    public void testCalculatePERatioForStockWithNonZero() {

        BigDecimal calculatedValue = simpleStockMarket.calculatePERatioForStock("POP", BigDecimal.TEN);

        Stock popStock = listing.getListedStock("POP");
        BigDecimal expectedValue = BigDecimal.TEN.divide(popStock.getLastDividend(), MathContext.DECIMAL64);

        assertEquals(expectedValue, calculatedValue);
    }

    // Helper methods ///

    /**
     * Initialisation method for the Stock Markets stock listings.
     *
     * @return Map  - data structure representing the listed stocks listed in the stock market.
     */
    private StockListing initlisting() {

        Stock teaStock = new CommonStock("TEA", BigDecimal.ZERO, BigDecimal.ONE);
        Stock popStock = new CommonStock("POP", new BigDecimal(0.08, MathContext.DECIMAL64), BigDecimal.ONE);
        Stock aleStock = new CommonStock("ALE", new BigDecimal(0.23, MathContext.DECIMAL64), new BigDecimal(0.6, MathContext.DECIMAL64));
        Stock ginStock = new PreferredStock("GIN", new BigDecimal(0.08, MathContext.DECIMAL64), new BigDecimal(0.02, MathContext.DECIMAL64), BigDecimal.ONE);
        Stock joeStock = new CommonStock("JOE", new BigDecimal(0.13, MathContext.DECIMAL64), new BigDecimal(2.5, MathContext.DECIMAL64));

        
        StockListing stockListing = new SimpleStockListing();
        stockListing.listStock(teaStock);
        stockListing.listStock(popStock);
        stockListing.listStock(aleStock);
        stockListing.listStock(ginStock);
        stockListing.listStock(joeStock);

        return stockListing;
    }

    private void generateAndRegisterTradesForStock(String stockSymbol) {

        Trade trade = new Trade(stockSymbol, LocalDateTime.now(), 100, BuySellIndicator.BUY, BigDecimal.TEN);
        Collection<Trade> trades = Arrays.asList(trade, trade, trade, trade);

        trades.stream().forEach(t -> tradeDataService.recordTrade(t));
    }
}
