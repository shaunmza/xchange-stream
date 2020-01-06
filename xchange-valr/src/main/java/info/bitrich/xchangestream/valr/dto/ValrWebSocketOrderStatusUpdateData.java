
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orderId",
    "orderStatusType",
    "currencyPair",
    "originalPrice",
    "remainingQuantity",
    "originalQuantity",
    "orderSide",
    "orderType",
    "failedReason",
    "orderUpdatedAt",
    "orderCreatedAt",
    "customerOrderId"
})
public class ValrWebSocketOrderStatusUpdateData {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderStatusType")
    private String orderStatusType;
    @JsonProperty("currencyPair")
    private ValrWebSocketCurrencyPair currencyPair;
    @JsonProperty("originalPrice")
    private String originalPrice;
    @JsonProperty("remainingQuantity")
    private String remainingQuantity;
    @JsonProperty("originalQuantity")
    private String originalQuantity;
    @JsonProperty("orderSide")
    private String orderSide;
    @JsonProperty("orderType")
    private String orderType;
    @JsonProperty("failedReason")
    private String failedReason;
    @JsonProperty("orderUpdatedAt")
    private String orderUpdatedAt;
    @JsonProperty("orderCreatedAt")
    private String orderCreatedAt;
    @JsonProperty("customerOrderId")
    private String customerOrderId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketOrderStatusUpdateData() {
    }

    /**
     * 
     * @param remainingQuantity
     * @param orderType
     * @param currencyPair
     * @param originalPrice
     * @param orderId
     * @param originalQuantity
     * @param orderStatusType
     * @param customerOrderId
     * @param orderUpdatedAt
     * @param orderSide
     * @param orderCreatedAt
     * @param failedReason
     */
    public ValrWebSocketOrderStatusUpdateData(String orderId, String orderStatusType, ValrWebSocketCurrencyPair currencyPair, String originalPrice, String remainingQuantity, String originalQuantity, String orderSide, String orderType, String failedReason, String orderUpdatedAt, String orderCreatedAt, String customerOrderId) {
        super();
        this.orderId = orderId;
        this.orderStatusType = orderStatusType;
        this.currencyPair = currencyPair;
        this.originalPrice = originalPrice;
        this.remainingQuantity = remainingQuantity;
        this.originalQuantity = originalQuantity;
        this.orderSide = orderSide;
        this.orderType = orderType;
        this.failedReason = failedReason;
        this.orderUpdatedAt = orderUpdatedAt;
        this.orderCreatedAt = orderCreatedAt;
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

    @JsonProperty("orderStatusType")
    public String getOrderStatusType() {
        return orderStatusType;
    }

    @JsonProperty("orderStatusType")
    public void setOrderStatusType(String orderStatusType) {
        this.orderStatusType = orderStatusType;
    }

    @JsonProperty("currencyPair")
    public ValrWebSocketCurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @JsonProperty("currencyPair")
    public void setCurrencyPair(ValrWebSocketCurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    @JsonProperty("originalPrice")
    public String getOriginalPrice() {
        return originalPrice;
    }

    @JsonProperty("originalPrice")
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    @JsonProperty("remainingQuantity")
    public String getRemainingQuantity() {
        return remainingQuantity;
    }

    @JsonProperty("remainingQuantity")
    public void setRemainingQuantity(String remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    @JsonProperty("originalQuantity")
    public String getOriginalQuantity() {
        return originalQuantity;
    }

    @JsonProperty("originalQuantity")
    public void setOriginalQuantity(String originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    @JsonProperty("orderSide")
    public String getOrderSide() {
        return orderSide;
    }

    @JsonProperty("orderSide")
    public void setOrderSide(String orderSide) {
        this.orderSide = orderSide;
    }

    @JsonProperty("orderType")
    public String getOrderType() {
        return orderType;
    }

    @JsonProperty("orderType")
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @JsonProperty("failedReason")
    public String getFailedReason() {
        return failedReason;
    }

    @JsonProperty("failedReason")
    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    @JsonProperty("orderUpdatedAt")
    public String getOrderUpdatedAt() {
        return orderUpdatedAt;
    }

    @JsonProperty("orderUpdatedAt")
    public void setOrderUpdatedAt(String orderUpdatedAt) {
        this.orderUpdatedAt = orderUpdatedAt;
    }

    @JsonProperty("orderCreatedAt")
    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    @JsonProperty("orderCreatedAt")
    public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
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
        return new ToStringBuilder(this).append("orderId", orderId).append("orderStatusType", orderStatusType).append("currencyPair", currencyPair).append("originalPrice", originalPrice).append("remainingQuantity", remainingQuantity).append("originalQuantity", originalQuantity).append("orderSide", orderSide).append("orderType", orderType).append("failedReason", failedReason).append("orderUpdatedAt", orderUpdatedAt).append("orderCreatedAt", orderCreatedAt).append("customerOrderId", customerOrderId).toString();
    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketOrderStatusUpdateData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketOrderStatusUpdateData that = (ValrWebSocketOrderStatusUpdateData) o;

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
                orderId, orderStatusType, currencyPair, originalPrice, remainingQuantity, originalQuantity, orderSide,
                orderType, failedReason, orderUpdatedAt, orderCreatedAt, customerOrderId
        };
        return result;
    }

}
