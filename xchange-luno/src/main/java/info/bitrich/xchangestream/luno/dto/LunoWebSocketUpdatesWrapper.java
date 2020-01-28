
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sequence",
    "trade_updates",
    "create_update",
    "delete_update",
    "status_update",
    "timestamp"
})
public class LunoWebSocketUpdatesWrapper {

    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("trade_updates")
    private List<LunoWebSocketTradeUpdate> tradeUpdates;
    @JsonProperty("create_update")
    private LunoWebSocketCreateUpdate createUpdate;
    @JsonProperty("delete_update")
    private LunoWebSocketDeleteUpdate deleteUpdate;
    @JsonProperty("status_update")
    private LunoWebSocketStatusUpdate statusUpdate;
    @JsonProperty("timestamp")
    private Integer timestamp;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketUpdatesWrapper() {
    }

    /**
     * 
     * @param createUpdate
     * @param sequence
     * @param statusUpdate
     * @param deleteUpdate
     * @param tradeUpdates
     * @param timestamp
     */
    public LunoWebSocketUpdatesWrapper(String sequence, List<LunoWebSocketTradeUpdate> tradeUpdates,
                                       LunoWebSocketCreateUpdate createUpdate, LunoWebSocketDeleteUpdate deleteUpdate,
                                       LunoWebSocketStatusUpdate statusUpdate, Integer timestamp) {
        super();
        this.sequence = sequence;
        this.tradeUpdates = tradeUpdates;
        this.createUpdate = createUpdate;
        this.deleteUpdate = deleteUpdate;
        this.statusUpdate = statusUpdate;
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

    @JsonProperty("trade_updates")
    public List<LunoWebSocketTradeUpdate> getTradeUpdates() {
        return tradeUpdates;
    }

    @JsonProperty("trade_updates")
    public void setTradeUpdates(List<LunoWebSocketTradeUpdate> tradeUpdates) {
        this.tradeUpdates = tradeUpdates;
    }

    @JsonProperty("create_update")
    public LunoWebSocketCreateUpdate getCreateUpdate() {
        return createUpdate;
    }

    @JsonProperty("create_update")
    public void setCreateUpdate(LunoWebSocketCreateUpdate createUpdate) {
        this.createUpdate = createUpdate;
    }

    @JsonProperty("delete_update")
    public LunoWebSocketDeleteUpdate getDeleteUpdate() {
        return deleteUpdate;
    }

    @JsonProperty("delete_update")
    public void setDeleteUpdate(LunoWebSocketDeleteUpdate deleteUpdate) {
        this.deleteUpdate = deleteUpdate;
    }

    @JsonProperty("status_update")
    public LunoWebSocketStatusUpdate getStatusUpdate() {
        return statusUpdate;
    }

    @JsonProperty("status_update")
    public void setStatusUpdate(LunoWebSocketStatusUpdate statusUpdate) {
        this.statusUpdate = statusUpdate;
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
        return "LunoWebSocketUpdatesWrapper{" +
                "sequence='" + sequence + "'" +
        "tradeUpdates='" + tradeUpdates + "'" +
        "createUpdate='" + createUpdate + "'" +
        "deleteUpdate='" + deleteUpdate + "'" +
        "statusUpdate='" + statusUpdate + "'" +
        "timestamp='" + timestamp + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketUpdatesWrapper)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketUpdatesWrapper that = (LunoWebSocketUpdatesWrapper) o;

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
                sequence, tradeUpdates, createUpdate, deleteUpdate, statusUpdate, timestamp
        };
        return result;
    }

}
