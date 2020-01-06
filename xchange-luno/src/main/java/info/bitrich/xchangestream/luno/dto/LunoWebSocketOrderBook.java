
package info.bitrich.xchangestream.luno.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sequence",
    "asks",
    "bids",
    "status",
    "timestamp"
})
public class LunoWebSocketOrderBook {

    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("asks")
    private List<LunoOrder> asks = null;
    @JsonProperty("bids")
    private List<LunoOrder> bids = null;
    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private Integer timestamp;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketOrderBook() {
    }

    /**
     * 
     * @param sequence
     * @param asks
     * @param bids
     * @param status
     * @param timestamp
     */
    public LunoWebSocketOrderBook(String sequence, List<LunoOrder> asks, List<LunoOrder> bids, String status, Integer timestamp) {
        super();
        this.sequence = sequence;
        this.asks = asks;
        this.bids = bids;
        this.status = status;
        this.timestamp = timestamp;
    }

    @JsonProperty("sequence")
    public String getSequence() {
        return sequence;
    }

    @JsonProperty("sequence")
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @JsonProperty("asks")
    public List<LunoOrder> getAsks() {
        return asks;
    }

    @JsonProperty("asks")
    public void setAsks(List<LunoOrder> asks) {
        this.asks = asks;
    }

    @JsonProperty("bids")
    public List<LunoOrder> getBids() {
        return bids;
    }

    @JsonProperty("bids")
    public void setBids(List<LunoOrder> bids) {
        this.bids = bids;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LunoWebSocketOrderBook{" +
                "sequence='" + sequence + "'" +
                "asks='" + asks + "'" +
                "bids='" + bids + "'" +
                "status='" + status + "'" +
                "timestamp='" + timestamp + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketOrderBook)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketOrderBook that = (LunoWebSocketOrderBook) o;

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
                sequence, asks, bids, status, timestamp
        };
        return result;
    }

}
