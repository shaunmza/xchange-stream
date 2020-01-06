
package info.bitrich.xchangestream.valr.dto;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "subscriptions"
})
public class ValrWebSocketSubscriptionMessage {

    @JsonProperty("type")
    private String type;
    @JsonProperty("subscriptions")
    private List<ValrWebSocketSubscription> subscriptions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketSubscriptionMessage() {
    }

    /**
     *
     * @param channel
     * @param event
     * @param pairs
     */
    public ValrWebSocketSubscriptionMessage(String channel, String type, String event, List<String>  pairs) {
        ValrWebSocketSubscription subscription = new ValrWebSocketSubscription(event, pairs);
        List<ValrWebSocketSubscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        this.type = type;
        this.subscriptions = subscriptions;
    }

    /**
     * 
     * @param subscriptions
     * @param type
     */
    public ValrWebSocketSubscriptionMessage(String type, List<ValrWebSocketSubscription> subscriptions) {
        super();
        this.type = type;
        this.subscriptions = subscriptions;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("subscriptions")
    public List<ValrWebSocketSubscription> getSubscriptions() {
        return subscriptions;
    }

    @JsonProperty("subscriptions")
    public void setSubscriptions(List<ValrWebSocketSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("subscriptions", subscriptions).toString();
    }*/


}
