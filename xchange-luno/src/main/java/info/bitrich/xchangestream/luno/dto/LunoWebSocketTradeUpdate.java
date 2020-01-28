
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "base",
        "counter",
        "maker_order_id",
        "taker_order_id"
})
public class LunoWebSocketTradeUpdate {

    @JsonProperty("base")
    private String base;
    @JsonProperty("counter")
    private String counter;
    @JsonProperty("maker_order_id")
    private String makerOrderId;
    @JsonProperty("taker_order_id")
    private String takerOrderId;

    /**
     * No args constructor for use in serialization
     *
     */
    public LunoWebSocketTradeUpdate() {
    }

    /**
     *
     * @param takerOrderId
     * @param counter
     * @param makerOrderId
     * @param base
     */
    public LunoWebSocketTradeUpdate(String base, String counter, String makerOrderId, String takerOrderId) {
        super();
        this.base = base;
        this.counter = counter;
        this.makerOrderId = makerOrderId;
        this.takerOrderId = takerOrderId;
    }

    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    @JsonProperty("base")
    public void setBase(String base) {
        this.base = base;
    }

    public LunoWebSocketTradeUpdate withBase(String base) {
        this.base = base;
        return this;
    }

    @JsonProperty("counter")
    public String getCounter() {
        return counter;
    }

    @JsonProperty("counter")
    public void setCounter(String counter) {
        this.counter = counter;
    }

    public LunoWebSocketTradeUpdate withCounter(String counter) {
        this.counter = counter;
        return this;
    }

    @JsonProperty("maker_order_id")
    public String getMakerOrderId() {
        return makerOrderId;
    }

    @JsonProperty("maker_order_id")
    public void setMakerOrderId(String makerOrderId) {
        this.makerOrderId = makerOrderId;
    }

    public LunoWebSocketTradeUpdate withMakerOrderId(String makerOrderId) {
        this.makerOrderId = makerOrderId;
        return this;
    }

    @JsonProperty("taker_order_id")
    public String getTakerOrderId() {
        return takerOrderId;
    }

    @JsonProperty("taker_order_id")
    public void setTakerOrderId(String takerOrderId) {
        this.takerOrderId = takerOrderId;
    }

    public LunoWebSocketTradeUpdate withTakerOrderId(String takerOrderId) {
        this.takerOrderId = takerOrderId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketTradeUpdate)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketTradeUpdate that = (LunoWebSocketTradeUpdate) o;

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
                base, counter, makerOrderId, takerOrderId
        };
        return result;
    }

}
