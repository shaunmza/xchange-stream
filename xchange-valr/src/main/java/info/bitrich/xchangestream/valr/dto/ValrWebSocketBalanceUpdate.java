
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "data"
})
public class ValrWebSocketBalanceUpdate {

    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private ValrWebSocketBalanceUpdateData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketBalanceUpdate() {
    }

    /**
     * 
     * @param data
     * @param type
     */
    public ValrWebSocketBalanceUpdate(String type, ValrWebSocketBalanceUpdateData data) {
        super();
        this.type = type;
        this.data = data;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ValrWebSocketBalanceUpdate withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("data")
    public ValrWebSocketBalanceUpdateData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(ValrWebSocketBalanceUpdateData data) {
        this.data = data;
    }

    public ValrWebSocketBalanceUpdate withData(ValrWebSocketBalanceUpdateData data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketBalanceUpdate)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketBalanceUpdate that = (ValrWebSocketBalanceUpdate) o;

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
                type, data
        };
        return result;
    }

}
