package stockmarket.trade.service;

import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Interface describing methods provided by a {@link TradeDataService}.
 *
 * @author Ryan Wishart
 */
public interface TradeDataService {

    void recordTrade(Trade trade);

    Collection<Trade> getTradesForStockInInterval(String stockSymbol, LocalDateTime intervalStart, LocalDateTime intervalEnd);
}
