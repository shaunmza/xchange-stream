
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "price",
    "volume"
})
public class LunoOrder {

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
    public LunoOrder() {
    }

    /**
     * 
     * @param volume
     * @param price
     * @param id
     */
    public LunoOrder(String id, String price, String volume) {
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
        return "LunoOrder{" +
                "id'" + id + "'" +
                "price'" + price + "'" +
                "volume'" + volume + "'}";
    }

}
