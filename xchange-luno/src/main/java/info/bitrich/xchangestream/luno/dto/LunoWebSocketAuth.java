
package info.bitrich.xchangestream.luno.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "api_key_id",
    "api_key_secret"
})
public class LunoWebSocketAuth {

    @JsonProperty("api_key_id")
    private String apiKeyId;
    @JsonProperty("api_key_secret")
    private String apiKeySecret;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LunoWebSocketAuth() {
    }

    /**
     * 
     * @param apiKeySecret
     * @param apiKeyId
     */
    public LunoWebSocketAuth(String apiKeyId, String apiKeySecret) {
        super();
        this.apiKeyId = apiKeyId;
        this.apiKeySecret = apiKeySecret;
    }

    @JsonProperty("api_key_id")
    public String getApiKeyId() {
        return apiKeyId;
    }

    @JsonProperty("api_key_id")
    public void setApiKeyId(String apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    @JsonProperty("api_key_secret")
    public String getApiKeySecret() {
        return apiKeySecret;
    }

    @JsonProperty("api_key_secret")
    public void setApiKeySecret(String apiKeySecret) {
        this.apiKeySecret = apiKeySecret;
    }

    @Override
    public String toString() {
        return "LunoWebSocketAuth{" +
                "apiKeyId='" + apiKeyId + "'" +
                "apiKeySecret='" + apiKeySecret + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunoWebSocketAuth)) return false;
        //if (!super.equals(o)) return false;

        LunoWebSocketAuth that = (LunoWebSocketAuth) o;

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
                apiKeyId, apiKeySecret
        };
        return result;
    }

}
