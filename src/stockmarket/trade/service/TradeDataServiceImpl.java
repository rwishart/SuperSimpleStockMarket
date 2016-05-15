package stockmarket.trade.service;

import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.collections.transformation.SortedList;
import stockmarket.trade.BuySellIndicator;
import stockmarket.trade.Trade;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the TradeDataService used within the SuperSimpleStockMarket application.
 *
 * @author Ryan Wishart
 */
public class TradeDataServiceImpl implements TradeDataService {

    private Map<String, List<Trade>> tradeStore;

    public TradeDataServiceImpl() {

        tradeStore = new HashMap<>();
    }

    @Override
    public void recordTrade(Trade trade) {

        String stockSymbol = trade.getStockSymbol();

        if(tradeStore.containsKey(stockSymbol)) {

            List tradeList = tradeStore.get(stockSymbol);

            if(tradeList != null) {
                tradeList.add(trade);
            } else {
                throw new IllegalStateException(String.format("Trade store data is incomplete. Unable to locate trades for %s", stockSymbol));
            }

        } else {

            List<Trade> tradeList = new ArrayList<>();
            tradeList.add(trade);
            tradeStore.put(stockSymbol, tradeList);
        }
    }

    @Override
    public Collection<Trade> getTradesForStockInInterval(String stockSymbol, LocalDateTime intervalStart, LocalDateTime intervalEnd) {

        Collection<Trade> tradesInInterval = Collections.EMPTY_LIST;

        if(tradeStore.containsKey(stockSymbol)) {
            List<Trade> tradesForStock = tradeStore.get(stockSymbol);
            tradesInInterval = tradesForStock.stream().filter(trade -> timestampInInterval(trade.getTimestamp(),
                    intervalStart, intervalEnd)).collect(Collectors.toList());
        }

        return tradesInInterval;
    }

    private boolean timestampInInterval(LocalDateTime timestamp, LocalDateTime intervalStart, LocalDateTime intervalEnd) {

        return timestamp.equals(intervalStart) || (timestamp.isAfter(intervalStart) &&
                timestamp.isBefore(intervalEnd)) || timestamp.equals(intervalEnd);
    }
}
