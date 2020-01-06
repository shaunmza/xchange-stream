
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price",
    "quantity",
    "currencyPair",
    "tradedAt",
    "takerSide"
})
public class ValrWebSocketNewTradeData {

    @JsonProperty("price")
    private String price;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("currencyPair")
    private String currencyPair;
    @JsonProperty("tradedAt")
    private String tradedAt;
    @JsonProperty("takerSide")
    @JsonAlias({"takerSide", "side"})
    private String takerSide;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketNewTradeData() {
    }

    /**
     * 
     * @param currencyPair
     * @param quantity
     * @param takerSide
     * @param price
     * @param tradedAt
     */
    public ValrWebSocketNewTradeData(String price, String quantity, String currencyPair, String tradedAt, String takerSide) {
        super();
        this.price = price;
        this.quantity = quantity;
        this.currencyPair = currencyPair;
        this.tradedAt = tradedAt;
        this.takerSide = takerSide;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("currencyPair")
    public String getCurrencyPair() {
        return currencyPair;
    }

    @JsonProperty("currencyPair")
    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    @JsonProperty("tradedAt")
    public String getTradedAt() {
        return tradedAt;
    }

    @JsonProperty("tradedAt")
    public void setTradedAt(String tradedAt) {
        this.tradedAt = tradedAt;
    }

    @JsonProperty("takerSide")
    public String getTakerSide() {
        return takerSide;
    }

    @JsonProperty("takerSide")
    public void setTakerSide(String takerSide) {
        this.takerSide = takerSide;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("price", price).append("quantity", quantity).append("currencyPair", currencyPair).append("tradedAt", tradedAt).append("takerSide", takerSide).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketNewTradeData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketNewTradeData that = (ValrWebSocketNewTradeData) o;

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
                price, quantity, currencyPair, tradedAt, takerSide
        };
        return result;
    }

}
