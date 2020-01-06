
package info.bitrich.xchangestream.valr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currencyPairSymbol",
    "bucketPeriodInSeconds",
    "startTime",
    "open",
    "high",
    "low",
    "close",
    "volume"
})
public class ValrWebSocketNewTradeBucketData {

    @JsonProperty("currencyPairSymbol")
    private String currencyPairSymbol;
    @JsonProperty("bucketPeriodInSeconds")
    private Integer bucketPeriodInSeconds;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("open")
    private String open;
    @JsonProperty("high")
    private String high;
    @JsonProperty("low")
    private String low;
    @JsonProperty("close")
    private String close;
    @JsonProperty("volume")
    private String volume;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValrWebSocketNewTradeBucketData() {
    }

    /**
     * 
     * @param volume
     * @param currencyPairSymbol
     * @param high
     * @param bucketPeriodInSeconds
     * @param low
     * @param startTime
     * @param close
     * @param open
     */
    public ValrWebSocketNewTradeBucketData(String currencyPairSymbol, Integer bucketPeriodInSeconds, String startTime, String open, String high, String low, String close, String volume) {
        super();
        this.currencyPairSymbol = currencyPairSymbol;
        this.bucketPeriodInSeconds = bucketPeriodInSeconds;
        this.startTime = startTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    @JsonProperty("currencyPairSymbol")
    public String getCurrencyPairSymbol() {
        return currencyPairSymbol;
    }

    @JsonProperty("currencyPairSymbol")
    public void setCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
    }

    public ValrWebSocketNewTradeBucketData withCurrencyPairSymbol(String currencyPairSymbol) {
        this.currencyPairSymbol = currencyPairSymbol;
        return this;
    }

    @JsonProperty("bucketPeriodInSeconds")
    public Integer getBucketPeriodInSeconds() {
        return bucketPeriodInSeconds;
    }

    @JsonProperty("bucketPeriodInSeconds")
    public void setBucketPeriodInSeconds(Integer bucketPeriodInSeconds) {
        this.bucketPeriodInSeconds = bucketPeriodInSeconds;
    }

    public ValrWebSocketNewTradeBucketData withBucketPeriodInSeconds(Integer bucketPeriodInSeconds) {
        this.bucketPeriodInSeconds = bucketPeriodInSeconds;
        return this;
    }

    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public ValrWebSocketNewTradeBucketData withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    @JsonProperty("open")
    public String getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(String open) {
        this.open = open;
    }

    public ValrWebSocketNewTradeBucketData withOpen(String open) {
        this.open = open;
        return this;
    }

    @JsonProperty("high")
    public String getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(String high) {
        this.high = high;
    }

    public ValrWebSocketNewTradeBucketData withHigh(String high) {
        this.high = high;
        return this;
    }

    @JsonProperty("low")
    public String getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(String low) {
        this.low = low;
    }

    public ValrWebSocketNewTradeBucketData withLow(String low) {
        this.low = low;
        return this;
    }

    @JsonProperty("close")
    public String getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(String close) {
        this.close = close;
    }

    public ValrWebSocketNewTradeBucketData withClose(String close) {
        this.close = close;
        return this;
    }

    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    public ValrWebSocketNewTradeBucketData withVolume(String volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValrWebSocketNewTradeBucketData)) return false;
        //if (!super.equals(o)) return false;

        ValrWebSocketNewTradeBucketData that = (ValrWebSocketNewTradeBucketData) o;

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
                currencyPairSymbol, bucketPeriodInSeconds, startTime, open, high, low, close
        };
        return result;
    }

}
