
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "price",
    "volume"
})
public class LunoWebSocketOrder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("price")
    private String price;
    @JsonProperty("volume")
    private String volume;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketOrder() {
    }

    /**
     * 
     * @param volume
     * @param price
     * @param id
     */
    public LunoWebSocketOrder(String id, String price, String volume) {
        super();
        this.id = id;
        this.price = price;
        this.volume = volume;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "LunoWebSocketOrder{" +
                "id'" + id + "'" +
                "price'" + price + "'" +
                "volume'" + volume + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketOrder)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketOrder that = (LunoWebSocketOrder) o;

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
                id, price, volume
        };
        return result;
    }

}
