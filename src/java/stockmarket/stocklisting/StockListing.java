package stockmarket.stocklisting;

import stockmarket.stock.Stock;

import java.util.Collection;

/**
 * Interface for a StockListing. The StockListing stores Stock objects for the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public interface StockListing {

    boolean isListedStock(String stockSymbol);

    Stock getListedStock(String stockSymbol);

    Collection<Stock> getAllListedStock();

    void listStock(Stock stockSymbol);
}
