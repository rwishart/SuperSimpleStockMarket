package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Implementation of the TradeDataService used within the SuperSimpleStockMarket application.
 *
 * @author Ryan Wishart
 */
public class TradeDataServiceImpl implements TradeDataService {

    private Map<String, Set<Trade>> tradeStore;

    public TradeDataServiceImpl() {

        tradeStore = new HashMap<>();
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
    public Collection<Trade> getTradesForStockInInterval(final String stockSymbol,
                                                         final LocalDateTime intervalStart,
                                                         final LocalDateTime intervalEnd) {

        Collection<Trade> tradesInInterval = Collections.EMPTY_LIST;

        if(tradeStore.containsKey(stockSymbol)) {
            Set<Trade> tradesForStock = tradeStore.get(stockSymbol);
            tradesInInterval = tradesForStock.stream().filter(trade -> timestampInInterval(trade.getTimestamp(),
                    intervalStart, intervalEnd)).collect(Collectors.toList());
        }

        return tradesInInterval;
    }

    private boolean timestampInInterval(final LocalDateTime timestamp,
                                        final LocalDateTime intervalStart,
                                        final LocalDateTime intervalEnd) {

        return timestamp.equals(intervalStart) || (timestamp.isAfter(intervalStart) &&
                timestamp.isBefore(intervalEnd)) || timestamp.equals(intervalEnd);
    }

    private class TradeComparator implements Comparator<Trade> {

        @Override
        public int compare(Trade left, Trade right) {

            if(left == null && right != null)
                return -1;

            if(right == null && left != null)
                return 1;

            if(right == left && right == null)
                return 0;

            if(left.equals(right)) {
                return 0;

            } else {
                return left.getTimestamp().compareTo(right.getTimestamp());
            }
        }
    }
}
