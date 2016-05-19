package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Interface describing methods provided by a {@link TradeDataService}. A TradeDataService is responsible
 * for recording trades on the SuperSimpleStockMarket and also returning sets of Trades within an interval.
 *
 * @author Ryan Wishart
 */
public interface TradeDataService {

    /**
     * Method to record a Trade that occurs on the SuperSimpleStockMarket.
     *
     * @param trade - The Trade to record.
     */
    void recordTrade(final Trade trade);

    /**
     * Method to retrieve Trades for a particular Stock that occurred within an interval period between intervalStart
     * and intervalEnd (inclusive).
     *
     * @param stockSymbol    - The stockSymbol to pull Trades for
     * @param intervalStart  - The time after which to retrieve Trades
     * @param intervalEnd    - The up to which Trades should be retrieved
     * @return               - Set of Trades in the interval [intervalStart - intervalEnd] for the stock with stockSymbol == stockSymbol
     */
    Set<Trade> getTradesForStockInInterval(final String stockSymbol, final LocalDateTime intervalStart, final LocalDateTime intervalEnd);
}
