package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Concrete implementation of the {@link TradeDataService} interface used within the SuperSimpleStockMarket application.
 * <p/>
 * The implementation uses a ConcurrentSkipListMap object to store all {@link Trade} objects. To speed searching, the Map
 * uses the stockSymbol recorded in the {@link Trade} as a Key. The corresponding Value is a ConcurrentSkipListSet containing
 * all {@link Trade} objects with that stockSymbol.
 * <p/>
 * As the Trades are stored in a Set, duplicate Trades are not allowed. The Trades in the Set are sorted by timestamp. This
 * speeds retrieval over an interval.
 *
 * @author Ryan Wishart
 */
public class TradeDataServiceImpl implements TradeDataService {

    private Map<String, Set<Trade>> tradeStore;

    public TradeDataServiceImpl() {

        tradeStore = new ConcurrentSkipListMap<>();
    }

    @Override
    public void recordTrade(Trade trade) {

        String stockSymbol = trade.getStockSymbol();

        if(tradeStore.containsKey(stockSymbol)) {

            Set trades = tradeStore.get(stockSymbol);

            if(trades != null) {
                trades.add(trade);
            } else {
                throw new IllegalStateException(String.format("Trade store data is incomplete. " +
                        "Unable to locate trades for %s", stockSymbol));
            }

        } else {

            ConcurrentSkipListSet<Trade> tradeList = new ConcurrentSkipListSet<>(new TradeComparator());
            tradeList.add(trade);
            tradeStore.put(stockSymbol, tradeList);
        }
    }

    @Override
    public Set<Trade> getTradesForStockInInterval(final String stockSymbol,
                                                         final LocalDateTime intervalStart,
                                                         final LocalDateTime intervalEnd) {

        Set<Trade> tradesInInterval = new LinkedHashSet<>();

        if(tradeStore.containsKey(stockSymbol)) {
            Set<Trade> tradesForStock = tradeStore.get(stockSymbol);

            for(Trade trade : tradesForStock) {
                if(timestampInInterval(trade.getTimestamp(), intervalStart, intervalEnd)) {
                    tradesInInterval.add(trade);

                } else if (trade.getTimestamp().isAfter(intervalEnd)){ //we have passed the end of our interval. End the search.
                    break;
                }
            }
        }

        return tradesInInterval;
    }

    private boolean timestampInInterval(final LocalDateTime timestamp,
                                        final LocalDateTime intervalStart,
                                        final LocalDateTime intervalEnd) {

        return timestamp.equals(intervalStart) || (timestamp.isAfter(intervalStart) &&
                timestamp.isBefore(intervalEnd)) || timestamp.equals(intervalEnd);
    }
}
