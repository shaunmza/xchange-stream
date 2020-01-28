
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_id",
    "type",
    "price",
    "volume"
})
public class LunoWebSocketCreateUpdate {

    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private String price;
    @JsonProperty("volume")
    private String volume;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketCreateUpdate() {
    }

    /**
     * 
     * @param volume
     * @param orderId
     * @param price
     * @param type
     */
    public LunoWebSocketCreateUpdate(String orderId, String type, String price, String volume) {
        super();
        this.orderId = orderId;
        this.type = type;
        this.price = price;
        this.volume = volume;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LunoWebSocketCreateUpdate withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public LunoWebSocketCreateUpdate withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    public LunoWebSocketCreateUpdate withPrice(String price) {
        this.price = price;
        return this;
    }

    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    public LunoWebSocketCreateUpdate withVolume(String volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketCreateUpdate)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketCreateUpdate that = (LunoWebSocketCreateUpdate) o;

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
                orderId, type, price, volume
        };
        return result;
    }

}
