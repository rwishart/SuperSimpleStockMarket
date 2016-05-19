package stockmarket.tradedata;

import stockmarket.trade.Trade;

import java.util.Comparator;

/**
 * Comparator to use when sorting the ConcurrentSkipListSet used for storing {@link Trade} objects.
 *
 * @author Ryan Wishart
 */
public class TradeComparator implements Comparator<Trade> {

    /**
     * The comparator compares to Trades (left and right). The value returned is in the range [-1,1]
     * with -1 if left is less than right, 0 if they are equal and 1 if right > left.
     *
     * @param left  - the first trade object to compare
     * @param right - the second trade object to compare
     * @return      - result of the comparison.
     */
    @Override
    public int compare(Trade left, Trade right) {

        if (right == left && right == null)
            return 0;

        if (left == null)
            return -1;

        if (right == null)
            return 1;

        if (left.equals(right)) {
            return 0;

        } else {
            return left.getTimestamp().compareTo(right.getTimestamp());
        }
    }
}
