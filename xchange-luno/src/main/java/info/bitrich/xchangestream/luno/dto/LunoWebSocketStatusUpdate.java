
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status"
})
public class LunoWebSocketStatusUpdate {

    @JsonProperty("status")
    private String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketStatusUpdate() {
    }

    /**
     * 
     * @param status
     */
    public LunoWebSocketStatusUpdate(String status) {
        super();
        this.status = status;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public LunoWebSocketStatusUpdate withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketStatusUpdate)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketStatusUpdate that = (LunoWebSocketStatusUpdate) o;

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
                status
        };
        return result;
    }

}
