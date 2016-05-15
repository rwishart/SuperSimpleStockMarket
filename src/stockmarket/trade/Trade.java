package stockmarket.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Trade class to capture information about a trade executed on the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public class Trade {

    private String stockSymbol;

    private LocalDateTime timestamp;

    private long quantityOfShares;

    private BuySellIndicator buySellIndicator;

    private BigDecimal tradedPrice;

    public Trade (String stockSymbol,
                  LocalDateTime timestamp,
                  long quantityOfShares,
                  BuySellIndicator buySellIndicator,
                  BigDecimal tradedPrice) {

        this.stockSymbol = stockSymbol;
        this.timestamp = timestamp;
        this.quantityOfShares = quantityOfShares;
        this.buySellIndicator = buySellIndicator;
        this.tradedPrice = tradedPrice;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getTradedPrice() {
        return tradedPrice;
    }

    BuySellIndicator getBuySellIndicator() {
        return buySellIndicator;
    }

    public long getQuantityOfShares() {
        return quantityOfShares;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (quantityOfShares != trade.quantityOfShares) return false;
        if (!stockSymbol.equals(trade.stockSymbol)) return false;
        if (!timestamp.equals(trade.timestamp)) return false;
        if (buySellIndicator != trade.buySellIndicator) return false;
        return tradedPrice.equals(trade.tradedPrice);

    }

    @Override
    public int hashCode() {
        int result = stockSymbol.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (int) (quantityOfShares ^ (quantityOfShares >>> 32));
        result = 31 * result + buySellIndicator.hashCode();
        result = 31 * result + tradedPrice.hashCode();
        return result;
    }
}
