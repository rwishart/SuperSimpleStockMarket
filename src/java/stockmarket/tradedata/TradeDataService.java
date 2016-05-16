package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Interface describing methods provided by a {@link TradeDataService}.
 *
 * @author Ryan Wishart
 */
public interface TradeDataService {

    void recordTrade(final Trade trade);

    Collection<Trade> getTradesForStockInInterval(final String stockSymbol, final LocalDateTime intervalStart, final LocalDateTime intervalEnd);
}
