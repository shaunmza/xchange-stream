
package info.bitrich.xchangestream.valr.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asks",
    "Bids"
})
public class ValrWebSocketAggregatedOrderBookData {

    @JsonProperty("Asks")
    private List<ValrWebSocketAggregatedOrderBookTrade> asks = null;
    @JsonProperty("Bids")
    private List<ValrWebSocketAggregatedOrderBookTrade> bids = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketAggregatedOrderBookData() {
    }

    /**
     * 
     * @param asks
     * @param bids
     */
    public ValrWebSocketAggregatedOrderBookData(List<ValrWebSocketAggregatedOrderBookTrade> asks, List<ValrWebSocketAggregatedOrderBookTrade> bids) {
        super();
        this.asks = asks;
        this.bids = bids;
    }

    @JsonProperty("Asks")
    public List<ValrWebSocketAggregatedOrderBookTrade> getAsks() {
        return asks;
    }

    @JsonProperty("Asks")
    public void setAsks(List<ValrWebSocketAggregatedOrderBookTrade> asks) {
        this.asks = asks;
    }

    @JsonProperty("Bids")
    public List<ValrWebSocketAggregatedOrderBookTrade> getBids() {
        return bids;
    }

    @JsonProperty("Bids")
    public void setBids(List<ValrWebSocketAggregatedOrderBookTrade> bids) {
        this.bids = bids;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("asks", asks).append("bids", bids).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketAggregatedOrderBookData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketAggregatedOrderBookData that = (ValrWebSocketAggregatedOrderBookData) o;

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
                asks, bids
        };
        return result;
    }

}
