package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Interface describing methods provided by a {@link TradeDataService}. A TradeDataService is responsible
 * for recording trades on the SuperSimpleStockMarket and also returning sets of trades within an interval.
 *
 * @author Ryan Wishart
 */
public interface TradeDataService {

    /**
     * Method to record a trade that occurs on the SuperSimpleStockMarket.
     *
     * @param trade - the trade to record.
     */
    void recordTrade(final Trade trade);

    /**
     * Method to retrieve trades for a particular stock that occurred within an interval period between intervalStart
     * and intervalEnd (inclusive).
     *
     * @param stockSymbol    - the stockSymbol to pull trades for
     * @param intervalStart  - the time after which to retrieve trades
     * @param intervalEnd    - the up to which trades should be retrieved
     * @return               - Set of trades in the interval [intervalStart - intervalEnd] for the stock with stockSymbol = stockSymbol
     */
    Set<Trade> getTradesForStockInInterval(final String stockSymbol, final LocalDateTime intervalStart, final LocalDateTime intervalEnd);
}
