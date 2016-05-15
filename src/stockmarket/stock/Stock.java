package stockmarket.stock;

import java.math.BigDecimal;

/**
 * Definition of a Stock traded on the SuperSimpleStockMarket.
 *
 * @author Ryan Wishart
 */
public class Stock {

    private String stockSymbol;

    private StockType stockType;

    private BigDecimal lastDividend;

    private BigDecimal fixedDividend;

    private BigDecimal parValue;

    private BigDecimal stockPrice;

    public Stock(String stockSymbol, StockType stockType, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue, BigDecimal stockPrice) {

        this.stockSymbol = stockSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.stockPrice = stockPrice;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public StockType getStockType() {
        return stockType;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public void setFixedDividend(BigDecimal fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }

    public void setStockPrice(BigDecimal price) {
        this.stockPrice = price;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!stockSymbol.equals(stock.stockSymbol)) return false;
        if (stockType != stock.stockType) return false;
        if (!lastDividend.equals(stock.lastDividend)) return false;
        if (fixedDividend != null ? !fixedDividend.equals(stock.fixedDividend) : stock.fixedDividend != null)
            return false;
        if (!parValue.equals(stock.parValue)) return false;
        return stockPrice.equals(stock.stockPrice);

    }

    @Override
    public int hashCode() {
        int result = stockSymbol.hashCode();
        result = 31 * result + stockType.hashCode();
        result = 31 * result + lastDividend.hashCode();
        result = 31 * result + (fixedDividend != null ? fixedDividend.hashCode() : 0);
        result = 31 * result + parValue.hashCode();
        result = 31 * result + stockPrice.hashCode();
        return result;
    }
}
