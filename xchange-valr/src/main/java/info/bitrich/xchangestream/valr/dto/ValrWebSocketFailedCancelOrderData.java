
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orderId",
    "message"
})
public class ValrWebSocketFailedCancelOrderData {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("message")
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketFailedCancelOrderData() {
    }

    /**
     * 
     * @param orderId
     * @param message
     */
    public ValrWebSocketFailedCancelOrderData(String orderId, String message) {
        super();
        this.orderId = orderId;
        this.message = message;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketFailedCancelOrderData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketFailedCancelOrderData that = (ValrWebSocketFailedCancelOrderData) o;

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
                orderId, message
        };
        return result;
    }

}
