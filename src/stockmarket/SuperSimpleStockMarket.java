package stockmarket;

import stockmarket.stock.Stock;
import stockmarket.trade.Trade;

import java.math.BigDecimal;

/**
 * Interface for the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
interface SuperSimpleStockMarket {

    BigDecimal calculateDividendYieldForStock(Stock stock, BigDecimal price);

    BigDecimal calculatePERatioForStock(Stock stock, BigDecimal price);

    void recordTrade(Trade tradeToRecord);

    BigDecimal calculateVolumeWeightedStockPrice(Stock stock);

    BigDecimal calculateGBCEAllShareIndex();
}
