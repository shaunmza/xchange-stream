
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_id"
})
public class LunoWebSocketDeleteUpdate {

    @JsonProperty("order_id")
    private String orderId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketDeleteUpdate() {
    }

    /**
     * 
     * @param orderId
     */
    public LunoWebSocketDeleteUpdate(String orderId) {
        super();
        this.orderId = orderId;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LunoWebSocketDeleteUpdate withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketDeleteUpdate)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketDeleteUpdate that = (LunoWebSocketDeleteUpdate) o;

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
                orderId
        };
        return result;
    }

}
