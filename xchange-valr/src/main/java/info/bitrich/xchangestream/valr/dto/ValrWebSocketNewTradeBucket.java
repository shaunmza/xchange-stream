
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "currencyPairSymbol",
    "data"
})
public class ValrWebSocketNewTradeBucket {

    @JsonProperty("type")
    private String type;
    @JsonProperty("currencyPairSymbol")
    private String currencyPairSymbol;
    @JsonProperty("data")
    private ValrWebSocketNewTradeBucketData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketNewTradeBucket() {
    }

    /**
     * 
     * @param currencyPairSymbol
     * @param data
     * @param type
     */
    public ValrWebSocketNewTradeBucket(String type, String currencyPairSymbol, ValrWebSocketNewTradeBucketData data) {
        super();
        this.type = type;
        this.currencyPairSymbol = currencyPairSymbol;
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

    public ValrWebSocketNewTradeBucket withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("currencyPairSymbol")
    public String getCurrencyPairSymbol() {
        return currencyPairSymbol;
    }

    @JsonProperty("currencyPairSymbol")
    public void setCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
    }

    public ValrWebSocketNewTradeBucket withCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
        return this;
    }

    @JsonProperty("data")
    public ValrWebSocketNewTradeBucketData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(ValrWebSocketNewTradeBucketData data) {
        this.data = data;
    }

    public ValrWebSocketNewTradeBucket withData(ValrWebSocketNewTradeBucketData data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketNewTradeBucket)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketNewTradeBucket that = (ValrWebSocketNewTradeBucket) o;

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
                type, currencyPairSymbol, data
        };
        return result;
    }

}
