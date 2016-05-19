package stockmarket.stocklisting;

import stockmarket.stock.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic implementation of the {@link StockListing} interface that makes use of a HashMap to
 * store and retrieve {@link Stock}.
 *
 * @author Ryan Wishart
 */
public class SimpleStockListing implements StockListing{

    private Map<String, Stock> listing;

    private static final Logger log = Logger.getLogger("SimpleStockListing");

    public SimpleStockListing() {

        listing = new HashMap<>();
    }

    @Override
    public boolean isListedStock(String stockSymbol){
        return listing.containsKey(stockSymbol);
    }

    @Override
    public Stock getListedStock(String stockSymbol){

        log.log(Level.ALL, String.format("Retrieving stock for stockSymbol %s.", stockSymbol));
        return listing.get(stockSymbol);
    }

    @Override
    public Collection<Stock> getAllListedStock(){

        log.log(Level.ALL, "Getting all listed stocks.");
        return listing.values();
    }

    @Override
    public void listStock(Stock stock) {

        log.log(Level.ALL, String.format("Listing stock %s", stock));
        listing.put(stock.getStockSymbol(), stock);
    }
}
