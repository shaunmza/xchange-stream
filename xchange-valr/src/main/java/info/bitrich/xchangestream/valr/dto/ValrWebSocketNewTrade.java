
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
public class ValrWebSocketNewTrade {

    @JsonProperty("type")
    private String type;
    @JsonProperty("currencyPairSymbol")
    private String currencyPairSymbol;
    @JsonProperty("data")
    private ValrWebSocketNewTradeData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketNewTrade() {
    }

    /**
     * 
     * @param currencyPairSymbol
     * @param data
     * @param type
     */
    public ValrWebSocketNewTrade(String type, String currencyPairSymbol, ValrWebSocketNewTradeData data) {
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

    @JsonProperty("currencyPairSymbol")
    public String getCurrencyPairSymbol() {
        return currencyPairSymbol;
    }

    @JsonProperty("currencyPairSymbol")
    public void setCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
    }

    @JsonProperty("data")
    public ValrWebSocketNewTradeData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(ValrWebSocketNewTradeData data) {
        this.data = data;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("currencyPairSymbol", currencyPairSymbol).append("data", data).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketNewTrade)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketNewTrade that = (ValrWebSocketNewTrade) o;

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
