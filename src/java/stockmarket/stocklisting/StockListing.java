package stockmarket.stocklisting;

import stockmarket.stock.Stock;

import java.util.Collection;

/**
 * Interface for a StockListing. The StockListing stores {@link Stock} objects for the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public interface StockListing {

    /**
     * Method to determine if a stock symbol is listed on the stock exchange.
     * <p/>
     * Returns a boolean result indicating (true) if the stock is listed and (false) otherwise.
     *
     * @param stockSymbol - The stock symbol to search for.
     * @return            - boolean indicating if the stock is listed on the exchange.
     */
    boolean isListedStock(String stockSymbol);

    /**
     * Method to retrieve a stock object listed on the stock exchange.
     *
     * @param stockSymbol - Stock symbol of the stock to retrieve.
     * @return            - Returns a {@link Stock} object representing the stock with the parameter stock symbol.
     *                      If the stock symbol is not found, null is returned.
     */
    Stock getListedStock(String stockSymbol);

    /**
     * Method to retrieve {@link Stock}s representing all the stock listed on the stock exchange.
     *
     * @return           - A collection containing all the stocks listed on the stock exchange.
     */
    Collection<Stock> getAllListedStock();

    /**
     * Method to list a stock on the stock exchange.
     * <p/>
     * If there is already a stock listed with the same stockSymbol, the new stock replaces the old.
     *
     * @param stockSymbol - the stock to list on the stock exchange.
     */
    void listStock(Stock stockSymbol);
}
