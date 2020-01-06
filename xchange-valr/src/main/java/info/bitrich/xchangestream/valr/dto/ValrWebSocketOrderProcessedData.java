
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orderId",
    "success",
    "failureReason"
})
public class ValrWebSocketOrderProcessedData {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("failureReason")
    private String failureReason;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketOrderProcessedData() {
    }

    /**
     * 
     * @param orderId
     * @param success
     * @param failureReason
     */
    public ValrWebSocketOrderProcessedData(String orderId, Boolean success, String failureReason) {
        super();
        this.orderId = orderId;
        this.success = success;
        this.failureReason = failureReason;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("failureReason")
    public String getFailureReason() {
        return failureReason;
    }

    @JsonProperty("failureReason")
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("orderId", orderId).append("success", success).append("failureReason", failureReason).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketOrderProcessedData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketOrderProcessedData that = (ValrWebSocketOrderProcessedData) o;

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
                orderId, success, failureReason
        };
        return result;
    }

}
