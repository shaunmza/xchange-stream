
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "side",
    "quantity",
    "price",
    "currencyPair",
    "orderCount"
})
public class ValrWebSocketAggregatedOrderBookTrade {

    @JsonProperty("side")
    private String side;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("price")
    private String price;
    @JsonProperty("currencyPair")
    private String currencyPair;
    @JsonProperty("orderCount")
    private Integer orderCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketAggregatedOrderBookTrade() {
    }

    /**
     * 
     * @param side
     * @param currencyPair
     * @param quantity
     * @param price
     * @param orderCount
     */
    public ValrWebSocketAggregatedOrderBookTrade(String side, String quantity, String price, String currencyPair, Integer orderCount) {
        super();
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.currencyPair = currencyPair;
        this.orderCount = orderCount;
    }

    @JsonProperty("side")
    public String getSide() {
        return side;
    }

    @JsonProperty("side")
    public void setSide(String side) {
        this.side = side;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("currencyPair")
    public String getCurrencyPair() {
        return currencyPair;
    }

    @JsonProperty("currencyPair")
    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    @JsonProperty("orderCount")
    public Integer getOrderCount() {
        return orderCount;
    }

    @JsonProperty("orderCount")
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("side", side).append("quantity", quantity).append("price", price).append("currencyPair", currencyPair).append("orderCount", orderCount).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketAggregatedOrderBookTrade)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketAggregatedOrderBookTrade that = (ValrWebSocketAggregatedOrderBookTrade) o;

        for(int i = 0; i < this.getSigFields().length; ++i){
            if (!Objects.equals(this.getSigFields()[i], that.getSigFields()[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSigFields());
    }

    private Object[] getSigFields(){
        Object[] result = {
                side, quantity, price, currencyPair, orderCount
        };
        return result;
    }

}
