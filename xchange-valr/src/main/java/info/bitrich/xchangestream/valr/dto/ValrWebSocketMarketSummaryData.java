
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currencyPairSymbol",
    "askPrice",
    "bidPrice",
    "lastTradedPrice",
    "previousClosePrice",
    "baseVolume",
    "highPrice",
    "lowPrice",
    "created",
    "changeFromPrevious"
})
public class ValrWebSocketMarketSummaryData {

    @JsonProperty("currencyPairSymbol")
    private String currencyPairSymbol;
    @JsonProperty("askPrice")
    private String askPrice;
    @JsonProperty("bidPrice")
    private String bidPrice;
    @JsonProperty("lastTradedPrice")
    private String lastTradedPrice;
    @JsonProperty("previousClosePrice")
    private String previousClosePrice;
    @JsonProperty("baseVolume")
    private String baseVolume;
    @JsonProperty("highPrice")
    private String highPrice;
    @JsonProperty("lowPrice")
    private String lowPrice;
    @JsonProperty("created")
    private String created;
    @JsonProperty("changeFromPrevious")
    private String changeFromPrevious;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketMarketSummaryData() {
    }

    /**
     * 
     * @param previousClosePrice
     * @param askPrice
     * @param currencyPairSymbol
     * @param lowPrice
     * @param created
     * @param highPrice
     * @param changeFromPrevious
     * @param baseVolume
     * @param lastTradedPrice
     * @param bidPrice
     */
    public ValrWebSocketMarketSummaryData(String currencyPairSymbol, String askPrice, String bidPrice, String lastTradedPrice, String previousClosePrice, String baseVolume, String highPrice, String lowPrice, String created, String changeFromPrevious) {
        super();
        this.currencyPairSymbol = currencyPairSymbol;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.lastTradedPrice = lastTradedPrice;
        this.previousClosePrice = previousClosePrice;
        this.baseVolume = baseVolume;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.created = created;
        this.changeFromPrevious = changeFromPrevious;
    }

    @JsonProperty("currencyPairSymbol")
    public String getCurrencyPairSymbol() {
        return currencyPairSymbol;
    }

    @JsonProperty("currencyPairSymbol")
    public void setCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
    }

    @JsonProperty("askPrice")
    public String getAskPrice() {
        return askPrice;
    }

    @JsonProperty("askPrice")
    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    @JsonProperty("bidPrice")
    public String getBidPrice() {
        return bidPrice;
    }

    @JsonProperty("bidPrice")
    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    @JsonProperty("lastTradedPrice")
    public String getLastTradedPrice() {
        return lastTradedPrice;
    }

    @JsonProperty("lastTradedPrice")
    public void setLastTradedPrice(String lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    @JsonProperty("previousClosePrice")
    public String getPreviousClosePrice() {
        return previousClosePrice;
    }

    @JsonProperty("previousClosePrice")
    public void setPreviousClosePrice(String previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    @JsonProperty("baseVolume")
    public String getBaseVolume() {
        return baseVolume;
    }

    @JsonProperty("baseVolume")
    public void setBaseVolume(String baseVolume) {
        this.baseVolume = baseVolume;
    }

    @JsonProperty("highPrice")
    public String getHighPrice() {
        return highPrice;
    }

    @JsonProperty("highPrice")
    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    @JsonProperty("lowPrice")
    public String getLowPrice() {
        return lowPrice;
    }

    @JsonProperty("lowPrice")
    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("changeFromPrevious")
    public String getChangeFromPrevious() {
        return changeFromPrevious;
    }

    @JsonProperty("changeFromPrevious")
    public void setChangeFromPrevious(String changeFromPrevious) {
        this.changeFromPrevious = changeFromPrevious;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this).append("currencyPairSymbol", currencyPairSymbol).append("askPrice", askPrice).append("bidPrice", bidPrice).append("lastTradedPrice", lastTradedPrice).append("previousClosePrice", previousClosePrice).append("baseVolume", baseVolume).append("highPrice", highPrice).append("lowPrice", lowPrice).append("created", created).append("changeFromPrevious", changeFromPrevious).toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketMarketSummaryData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketMarketSummaryData that = (ValrWebSocketMarketSummaryData) o;

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
                previousClosePrice, askPrice, currencyPairSymbol, lowPrice, created, highPrice,
                changeFromPrevious, baseVolume, lastTradedPrice, bidPrice
        };
        return result;
    }

}
