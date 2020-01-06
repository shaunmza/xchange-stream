
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "symbol",
    "baseCurrency",
    "quoteCurrency",
    "shortName",
    "exchange",
    "active",
    "minBaseAmount",
    "maxBaseAmount",
    "minQuoteAmount",
    "maxQuoteAmount"
})
public class ValrWebSocketCurrencyPair {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("baseCurrency")
    private ValrWebSocketCurrency baseCurrency;
    @JsonProperty("quoteCurrency")
    private ValrWebSocketCurrency quoteCurrency;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("minBaseAmount")
    private Double minBaseAmount;
    @JsonProperty("maxBaseAmount")
    private Integer maxBaseAmount;
    @JsonProperty("minQuoteAmount")
    private Integer minQuoteAmount;
    @JsonProperty("maxQuoteAmount")
    private Integer maxQuoteAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketCurrencyPair() {
    }

    /**
     * 
     * @param quoteCurrency
     * @param maxQuoteAmount
     * @param symbol
     * @param minQuoteAmount
     * @param active
     * @param exchange
     * @param id
     * @param maxBaseAmount
     * @param shortName
     * @param baseCurrency
     * @param minBaseAmount
     */
    public ValrWebSocketCurrencyPair(Integer id, String symbol, ValrWebSocketCurrency baseCurrency, ValrWebSocketCurrency quoteCurrency, String shortName, String exchange, Boolean active, Double minBaseAmount, Integer maxBaseAmount, Integer minQuoteAmount, Integer maxQuoteAmount) {
        super();
        this.id = id;
        this.symbol = symbol;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.shortName = shortName;
        this.exchange = exchange;
        this.active = active;
        this.minBaseAmount = minBaseAmount;
        this.maxBaseAmount = maxBaseAmount;
        this.minQuoteAmount = minQuoteAmount;
        this.maxQuoteAmount = maxQuoteAmount;
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

    @JsonProperty("baseCurrency")
    public ValrWebSocketCurrency getBaseCurrency() {
        return baseCurrency;
    }

    @JsonProperty("baseCurrency")
    public void setBaseCurrency(ValrWebSocketCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @JsonProperty("quoteCurrency")
    public ValrWebSocketCurrency getQuoteCurrency() {
        return quoteCurrency;
    }

    @JsonProperty("quoteCurrency")
    public void setQuoteCurrency(ValrWebSocketCurrency quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("minBaseAmount")
    public Double getMinBaseAmount() {
        return minBaseAmount;
    }

    @JsonProperty("minBaseAmount")
    public void setMinBaseAmount(Double minBaseAmount) {
        this.minBaseAmount = minBaseAmount;
    }

    @JsonProperty("maxBaseAmount")
    public Integer getMaxBaseAmount() {
        return maxBaseAmount;
    }

    @JsonProperty("maxBaseAmount")
    public void setMaxBaseAmount(Integer maxBaseAmount) {
        this.maxBaseAmount = maxBaseAmount;
    }

    @JsonProperty("minQuoteAmount")
    public Integer getMinQuoteAmount() {
        return minQuoteAmount;
    }

    @JsonProperty("minQuoteAmount")
    public void setMinQuoteAmount(Integer minQuoteAmount) {
        this.minQuoteAmount = minQuoteAmount;
    }

    @JsonProperty("maxQuoteAmount")
    public Integer getMaxQuoteAmount() {
        return maxQuoteAmount;
    }

    @JsonProperty("maxQuoteAmount")
    public void setMaxQuoteAmount(Integer maxQuoteAmount) {
        this.maxQuoteAmount = maxQuoteAmount;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("symbol", symbol).append("baseCurrency", baseCurrency).append("quoteCurrency", quoteCurrency).append("shortName", shortName).append("exchange", exchange).append("active", active).append("minBaseAmount", minBaseAmount).append("maxBaseAmount", maxBaseAmount).append("minQuoteAmount", minQuoteAmount).append("maxQuoteAmount", maxQuoteAmount).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketCurrencyPair)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketCurrencyPair that = (ValrWebSocketCurrencyPair) o;

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
                id, symbol, baseCurrency, quoteCurrency, shortName, exchange, active, minBaseAmount, maxBaseAmount, minQuoteAmount, maxQuoteAmount
        };
        return result;
    }

    public CurrencyPair toCurrencyPair(){
        return new CurrencyPair(baseCurrency.getShortName(), quoteCurrency.getShortName());
    }

}
