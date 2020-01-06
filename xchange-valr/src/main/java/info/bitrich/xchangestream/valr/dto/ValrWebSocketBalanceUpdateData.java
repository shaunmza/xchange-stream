
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currency",
    "available",
    "reserved",
    "total"
})
public class ValrWebSocketBalanceUpdateData {

    @JsonProperty("currency")
    private ValrWebSocketCurrency currency;
    @JsonProperty("available")
    private String available;
    @JsonProperty("reserved")
    private String reserved;
    @JsonProperty("total")
    private String total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketBalanceUpdateData() {
    }

    /**
     * 
     * @param total
     * @param reserved
     * @param available
     * @param currency
     */
    public ValrWebSocketBalanceUpdateData(ValrWebSocketCurrency currency, String available, String reserved, String total) {
        super();
        this.currency = currency;
        this.available = available;
        this.reserved = reserved;
        this.total = total;
    }

    @JsonProperty("currency")
    public ValrWebSocketCurrency getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(ValrWebSocketCurrency currency) {
        this.currency = currency;
    }

    public ValrWebSocketBalanceUpdateData withCurrency(ValrWebSocketCurrency currency) {
        this.currency = currency;
        return this;
    }

    @JsonProperty("available")
    public String getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(String available) {
        this.available = available;
    }

    public ValrWebSocketBalanceUpdateData withAvailable(String available) {
        this.available = available;
        return this;
    }

    @JsonProperty("reserved")
    public String getReserved() {
        return reserved;
    }

    @JsonProperty("reserved")
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public ValrWebSocketBalanceUpdateData withReserved(String reserved) {
        this.reserved = reserved;
        return this;
    }

    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
    }

    public ValrWebSocketBalanceUpdateData withTotal(String total) {
        this.total = total;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketBalanceUpdateData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketBalanceUpdateData that = (ValrWebSocketBalanceUpdateData) o;

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
                currency, available, reserved, total
        };
        return result;
    }


}
