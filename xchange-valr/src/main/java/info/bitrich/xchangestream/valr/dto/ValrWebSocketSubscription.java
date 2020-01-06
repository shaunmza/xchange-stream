
package info.bitrich.xchangestream.valr.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "pairs"
})
public class ValrWebSocketSubscription {

    @JsonProperty("event")
    private String event;
    @JsonProperty("pairs")
    private List<String> pairs = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketSubscription() {
    }

    /**
     * 
     * @param event
     * @param pairs
     */
    public ValrWebSocketSubscription(String event, List<String> pairs) {
        super();
        this.event = event;
        this.pairs = pairs;
    }

    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }

    @JsonProperty("pairs")
    public List<String> getPairs() {
        return pairs;
    }

    @JsonProperty("pairs")
    public void setPairs(List<String> pairs) {
        this.pairs = pairs;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("event", event).append("pairs", pairs).toString();
    }*/

}
