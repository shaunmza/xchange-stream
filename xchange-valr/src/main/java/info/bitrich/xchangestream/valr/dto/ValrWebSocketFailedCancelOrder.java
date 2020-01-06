
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "data"
})
public class ValrWebSocketFailedCancelOrder {

    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private ValrWebSocketFailedCancelOrderData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketFailedCancelOrder() {
    }

    /**
     * 
     * @param data
     * @param type
     */
    public ValrWebSocketFailedCancelOrder(String type, ValrWebSocketFailedCancelOrderData data) {
        super();
        this.type = type;
        this.data = data;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("data")
    public ValrWebSocketFailedCancelOrderData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(ValrWebSocketFailedCancelOrderData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketFailedCancelOrder)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketFailedCancelOrder that = (ValrWebSocketFailedCancelOrder) o;

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
                type, data
        };
        return result;
    }

}
