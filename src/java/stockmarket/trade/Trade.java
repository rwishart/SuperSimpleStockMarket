package stockmarket.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete representation of a Trade conducted on the SuperSimpleStockExchange.
 * <p/>
 * Trades record:
 * <ul>
 *     <li>the stock involved</li>
 *     <li>the timestamp for the transaction</li>
 *     <li>the quantity of shares</li>
 *     <li>whether it was a buy or a sell trade</li>
 *     <li>the price of the stock</li>
 * </ul>
 * <p/>
 * All trades are assumed to be conducted in GBP.
 *
 * @author Ryan Wishart
 */
public class Trade {

    private String stockSymbol;

    private LocalDateTime timestamp;

    private long quantityOfShares;

    private BuySellIndicator buySellIndicator;

    private BigDecimal tradedPrice;

    public Trade (final String stockSymbol,
                  final LocalDateTime timestamp,
                  final long quantityOfShares,
                  final BuySellIndicator buySellIndicator,
                  final BigDecimal tradedPrice) {

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

    /**
     * Equals method for a {@link Trade}. Used to determine equality with a parameter object.
     * <p/>
     * The method returns a boolean indicating if the objects are equal (true) or not (false).
     *
     * @param o - the Object to compare against
     * @return  - boolean value indicating if this Trade is equal to the parameter Object o.
     */
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

    /**
     * Method to calculate a hashcode value for this Trade object.
     *
     * @return - int value representing a hashcode value.
     */
    @Override
    public int hashCode() {
        int result = stockSymbol.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (int) (quantityOfShares ^ (quantityOfShares >>> 32));
        result = 31 * result + buySellIndicator.hashCode();
        result = 31 * result + tradedPrice.hashCode();
        return result;
    }

    /**
     * Method to generate a String value representing this Trade.
     *
     * @return - a String representing the stringified members of this Trade.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trade{");
        sb.append("stockSymbol='").append(stockSymbol).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", quantityOfShares=").append(quantityOfShares);
        sb.append(", buySellIndicator=").append(buySellIndicator);
        sb.append(", tradedPrice=").append(tradedPrice);
        sb.append('}');
        return sb.toString();
    }
}
