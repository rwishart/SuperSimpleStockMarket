package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger log = Logger.getLogger("TradeDataServiceImpl");

    private Map<String, Set<Trade>> tradeStore;

    public TradeDataServiceImpl() {

        tradeStore = new ConcurrentSkipListMap<>();
    }

    @Override
    public void recordTrade(Trade trade) {

        if(trade == null) {
            throw new IllegalArgumentException("Null parameter passed to the recordTrade method. This is an illegal argument.");
        }

        String stockSymbol = trade.getStockSymbol();

        if(tradeStore.containsKey(stockSymbol)) {

            log.log(Level.ALL, String.format("The trade store contains the stockSymbol %s. Adding trade to existing set of trades",
                    trade.getStockSymbol()));
            Set trades = tradeStore.get(stockSymbol);

            if(trades != null) {
                trades.add(trade);
            } else {
                throw new IllegalStateException(String.format("Trade store data is incomplete. " +
                        "Unable to locate trades for %s", stockSymbol));
            }

        } else {

            log.log(Level.ALL, "Adding trade to new Set and storing");
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
                    log.log(Level.ALL, String.format("timestamp %s is in interval (%s, %s)", trade.getTimestamp(), intervalStart, intervalEnd));
                    tradesInInterval.add(trade);

                } else if (trade.getTimestamp().isAfter(intervalEnd)){ //we have passed the end of our interval. End the search.
                    log.log(Level.ALL, String.format("timestamp %s is after end of interval %s so exiting.", trade.getTimestamp(), intervalEnd));
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
