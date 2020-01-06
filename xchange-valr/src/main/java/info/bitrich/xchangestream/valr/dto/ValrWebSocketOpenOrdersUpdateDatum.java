
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orderId",
    "side",
    "remainingQuantity",
    "originalPrice",
    "currencyPair",
    "createdAt",
    "originalQuantity",
    "filledPercentage",
    "customerOrderId"
})
public class ValrWebSocketOpenOrdersUpdateDatum {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("side")
    private String side;
    @JsonProperty("remainingQuantity")
    private String remainingQuantity;
    @JsonProperty("originalPrice")
    private String originalPrice;
    @JsonProperty("currencyPair")
    private ValrWebSocketCurrencyPair currencyPair;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("originalQuantity")
    private String originalQuantity;
    @JsonProperty("filledPercentage")
    private String filledPercentage;
    @JsonProperty("customerOrderId")
    private String customerOrderId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketOpenOrdersUpdateDatum() {
    }

    /**
     * 
     * @param remainingQuantity
     * @param createdAt
     * @param side
     * @param currencyPair
     * @param originalPrice
     * @param orderId
     * @param originalQuantity
     * @param customerOrderId
     * @param filledPercentage
     */
    public ValrWebSocketOpenOrdersUpdateDatum(String orderId, String side, String remainingQuantity, String originalPrice, ValrWebSocketCurrencyPair currencyPair, String createdAt, String originalQuantity, String filledPercentage, String customerOrderId) {
        super();
        this.orderId = orderId;
        this.side = side;
        this.remainingQuantity = remainingQuantity;
        this.originalPrice = originalPrice;
        this.currencyPair = currencyPair;
        this.createdAt = createdAt;
        this.originalQuantity = originalQuantity;
        this.filledPercentage = filledPercentage;
        this.customerOrderId = customerOrderId;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("side")
    public String getSide() {
        return side;
    }

    @JsonProperty("side")
    public void setSide(String side) {
        this.side = side;
    }

    @JsonProperty("remainingQuantity")
    public String getRemainingQuantity() {
        return remainingQuantity;
    }

    @JsonProperty("remainingQuantity")
    public void setRemainingQuantity(String remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    @JsonProperty("originalPrice")
    public String getOriginalPrice() {
        return originalPrice;
    }

    @JsonProperty("originalPrice")
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    @JsonProperty("currencyPair")
    public ValrWebSocketCurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @JsonProperty("currencyPair")
    public void setCurrencyPair(ValrWebSocketCurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("originalQuantity")
    public String getOriginalQuantity() {
        return originalQuantity;
    }

    @JsonProperty("originalQuantity")
    public void setOriginalQuantity(String originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    @JsonProperty("filledPercentage")
    public String getFilledPercentage() {
        return filledPercentage;
    }

    @JsonProperty("filledPercentage")
    public void setFilledPercentage(String filledPercentage) {
        this.filledPercentage = filledPercentage;
    }

    @JsonProperty("customerOrderId")
    public String getCustomerOrderId() {
        return customerOrderId;
    }

    @JsonProperty("customerOrderId")
    public void setCustomerOrderId(String customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("orderId", orderId).append("side", side).append("remainingQuantity", remainingQuantity).append("originalPrice", originalPrice).append("currencyPair", currencyPair).append("createdAt", createdAt).append("originalQuantity", originalQuantity).append("filledPercentage", filledPercentage).append("customerOrderId", customerOrderId).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketOpenOrdersUpdateDatum)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketOpenOrdersUpdateDatum that = (ValrWebSocketOpenOrdersUpdateDatum) o;

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
                orderId, side, remainingQuantity, originalPrice, currencyPair, createdAt, originalQuantity, filledPercentage, customerOrderId
        };
        return result;
    }

}
