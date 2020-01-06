
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "symbol",
    "decimalPlaces",
    "isActive",
    "shortName",
    "longName",
    "currencyDecimalPlaces",
    "supportedWithdrawDecimalPlaces"
})
public class ValrWebSocketCurrency {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("decimalPlaces")
    private Integer decimalPlaces;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("longName")
    private String longName;
    @JsonProperty("currencyDecimalPlaces")
    private Integer currencyDecimalPlaces;
    @JsonProperty("supportedWithdrawDecimalPlaces")
    private Integer supportedWithdrawDecimalPlaces;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketCurrency() {
    }

    /**
     * 
     * @param symbol
     * @param decimalPlaces
     * @param supportedWithdrawDecimalPlaces
     * @param id
     * @param isActive
     * @param shortName
     * @param currencyDecimalPlaces
     * @param longName
     */
    public ValrWebSocketCurrency(Integer id, String symbol, Integer decimalPlaces, Boolean isActive, String shortName, String longName, Integer currencyDecimalPlaces, Integer supportedWithdrawDecimalPlaces) {
        super();
        this.id = id;
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
        this.isActive = isActive;
        this.shortName = shortName;
        this.longName = longName;
        this.currencyDecimalPlaces = currencyDecimalPlaces;
        this.supportedWithdrawDecimalPlaces = supportedWithdrawDecimalPlaces;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("decimalPlaces")
    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    @JsonProperty("decimalPlaces")
    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    @JsonProperty("isActive")
    public Boolean getIsActive() {
        return isActive;
    }

    @JsonProperty("isActive")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("longName")
    public String getLongName() {
        return longName;
    }

    @JsonProperty("longName")
    public void setLongName(String longName) {
        this.longName = longName;
    }

    @JsonProperty("currencyDecimalPlaces")
    public Integer getCurrencyDecimalPlaces() {
        return currencyDecimalPlaces;
    }

    @JsonProperty("currencyDecimalPlaces")
    public void setCurrencyDecimalPlaces(Integer currencyDecimalPlaces) {
        this.currencyDecimalPlaces = currencyDecimalPlaces;
    }

    @JsonProperty("supportedWithdrawDecimalPlaces")
    public Integer getSupportedWithdrawDecimalPlaces() {
        return supportedWithdrawDecimalPlaces;
    }

    @JsonProperty("supportedWithdrawDecimalPlaces")
    public void setSupportedWithdrawDecimalPlaces(Integer supportedWithdrawDecimalPlaces) {
        this.supportedWithdrawDecimalPlaces = supportedWithdrawDecimalPlaces;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("symbol", symbol).append("decimalPlaces", decimalPlaces).append("isActive", isActive).append("shortName", shortName).append("longName", longName).append("currencyDecimalPlaces", currencyDecimalPlaces).append("supportedWithdrawDecimalPlaces", supportedWithdrawDecimalPlaces).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketCurrency)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketCurrency that = (ValrWebSocketCurrency) o;

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
                id, symbol, decimalPlaces, isActive, shortName, longName, currencyDecimalPlaces, supportedWithdrawDecimalPlaces
        };
        return result;
    }

}
