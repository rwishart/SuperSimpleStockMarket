package stockmarket.stocklisting;

import stockmarket.stock.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of the {@link StockListing} interface that makes use of a HashMap to
 * store and retrieve {@link Stock}.
 *
 * @author Ryan Wishart
 */
public class SimpleStockListing implements StockListing{

    private Map<String, Stock> listing;

    public SimpleStockListing() {

        listing = new HashMap<>();
    }

    @Override
    public boolean isListedStock(String stockSymbol){
        return listing.containsKey(stockSymbol);
    }

    @Override
    public Stock getListedStock(String stockSymbol){
        return listing.get(stockSymbol);
    }

    @Override
    public Collection<Stock> getAllListedStock(){
        return listing.values();
    }

    @Override
    public void listStock(Stock stock) {
        listing.put(stock.getStockSymbol(), stock);
    }
}
