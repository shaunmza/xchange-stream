
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    private Object tradeUpdates;
    @JsonProperty("create_update")
    private Object createUpdate;
    @JsonProperty("delete_update")
    private Object deleteUpdate;
    @JsonProperty("status_update")
    private Object statusUpdate;
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
    public LunoWebSocketUpdatesWrapper(String sequence, Object tradeUpdates, Object createUpdate, Object deleteUpdate, Object statusUpdate, Integer timestamp) {
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
    public Object getTradeUpdates() {
        return tradeUpdates;
    }

    @JsonProperty("trade_updates")
    public void setTradeUpdates(Object tradeUpdates) {
        this.tradeUpdates = tradeUpdates;
    }

    @JsonProperty("create_update")
    public Object getCreateUpdate() {
        return createUpdate;
    }

    @JsonProperty("create_update")
    public void setCreateUpdate(Object createUpdate) {
        this.createUpdate = createUpdate;
    }

    @JsonProperty("delete_update")
    public Object getDeleteUpdate() {
        return deleteUpdate;
    }

    @JsonProperty("delete_update")
    public void setDeleteUpdate(Object deleteUpdate) {
        this.deleteUpdate = deleteUpdate;
    }

    @JsonProperty("status_update")
    public Object getStatusUpdate() {
        return statusUpdate;
    }

    @JsonProperty("status_update")
    public void setStatusUpdate(Object statusUpdate) {
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

}
