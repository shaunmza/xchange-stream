package info.bitrich.xchangestream.valr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.valr.dto.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.*;

public class ValrStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(ValrStreamingService.class);

    private static final String TYPE = "type";

    public static final String TYPE_AGGREGATED_ORDERBOOK_UPDATE = "AGGREGATED_ORDERBOOK_UPDATE";
    public static final String TYPE_MARKET_SUMMARY_UPDATE = "MARKET_SUMMARY_UPDATE";
    public static final String TYPE_NEW_TRADE = "NEW_TRADE";
    public static final String TYPE_BALANCE_UPDATE = "BALANCE_UPDATE";
    public static final String TYPE_NEW_ACCOUNT_TRADE = "NEW_ACCOUNT_TRADE";
    public static final String TYPE_OPEN_ORDERS_UPDATE = "OPEN_ORDERS_UPDATE";
    public static final String TYPE_ORDER_PROCESSED = "ORDER_PROCESSED";
    public static final String TYPE_ORDER_STATUS_UPDATE = "ORDER_STATUS_UPDATE";
    public static final String TYPE_FAILED_CANCEL_ORDER = "FAILED_CANCEL_ORDER";

    private final PublishSubject<ValrWebSocketMarketSummaryUpdate> subjectMarketSummaryUpdate = PublishSubject.create();
    private final PublishSubject<ValrWebSocketNewTrade> subjectTrade = PublishSubject.create();
    private final PublishSubject<ValrWebSocketNewTradeData> subjectAccountTrade = PublishSubject.create();
    private final PublishSubject<ValrWebSocketAggregatedOrderBookUpdate> subjectOrderBook = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOpenOrdersUpdate> subjectOpenOrders = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOrderProcessedData> subjectOrderProcessed = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOrderStatusUpdateData> subjectOrderStatusUpdate = PublishSubject.create();
    private final PublishSubject<ValrWebSocketFailedCancelOrderData> subjectFailedCancelOrder = PublishSubject.create();
    private final PublishSubject<ValrWebSocketBalanceUpdateData> subjectBalance = PublishSubject.create();

    private String apiKey;
    private String apiSecret;
    private String apiPath;

    private final Map<String, String> subscribedChannels = new HashMap<>();
    private final SynchronizedValueFactory<Long> nonceFactory;

    public ValrStreamingService(String apiUrl,
                                SynchronizedValueFactory<Long> nonceFactory) {
        super(apiUrl, Integer.MAX_VALUE);
        this.nonceFactory = nonceFactory;
    }

    @Override
    public Completable connect() {
        return super.connect();
    }

    @Override
    public Completable disconnect() {
        return super.disconnect();
    }

    @Override
    protected DefaultHttpHeaders getCustomHeaders() {
        DefaultHttpHeaders customHeaders = super.getCustomHeaders();
        if (apiSecret == null || apiKey == null) {
            return customHeaders;
        }
        String timestamp = String.format("%d",  System.currentTimeMillis());

        ValrDigest valrDigest = ValrDigest.createInstance(apiSecret);
        String signature = valrDigest.digestParams(timestamp, "GET", this.apiPath, "");

        customHeaders.add("X-VALR-API-KEY", apiKey);
        customHeaders.add("X-VALR-SIGNATURE", signature);
        customHeaders.add("X-VALR-TIMESTAMP", timestamp);

        return customHeaders;
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    @Override
    public boolean processArrayMassageSeparately() {
        return false;
    }

    @Override
    public void handleMessage(JsonNode message) {

        JsonNode event = message.get(TYPE);
        if (event != null) {
            try {
                LOG.info(event.textValue());
                switch (event.textValue()) {
                    case TYPE_AGGREGATED_ORDERBOOK_UPDATE:
                        ValrWebSocketAggregatedOrderBookUpdate orderBookUpdate =
                                deserialize(message, ValrWebSocketAggregatedOrderBookUpdate.class);
                        subjectOrderBook.onNext(orderBookUpdate);
                        break;

                    case TYPE_MARKET_SUMMARY_UPDATE:
                        ValrWebSocketMarketSummaryUpdate newMarketSummaryUpdate =
                                deserialize(message, ValrWebSocketMarketSummaryUpdate.class);
                        subjectMarketSummaryUpdate.onNext(newMarketSummaryUpdate);
                        break;

                    case TYPE_NEW_TRADE:
                        ValrWebSocketNewTrade newTrade =
                                deserialize(message, ValrWebSocketNewTrade.class);
                        subjectTrade.onNext(newTrade);
                        break;

                    case TYPE_NEW_ACCOUNT_TRADE:
                        ValrWebSocketNewTrade newAccountTrade =
                                deserialize(message, ValrWebSocketNewTrade.class);
                        subjectAccountTrade.onNext(newAccountTrade.getData());
                        break;

                    case TYPE_OPEN_ORDERS_UPDATE:
                        ValrWebSocketOpenOrdersUpdate openOrdersUpdate =
                                deserialize(message, ValrWebSocketOpenOrdersUpdate.class);
                        subjectOpenOrders.onNext(openOrdersUpdate);
                        break;

                    case TYPE_ORDER_PROCESSED:
                        ValrWebSocketOrderProcessed orderProcessed =
                                deserialize(message, ValrWebSocketOrderProcessed.class);
                        subjectOrderProcessed.onNext(orderProcessed.getData());
                        break;

                    case TYPE_ORDER_STATUS_UPDATE:
                        ValrWebSocketOrderStatusUpdate orderStatusUpdate =
                                deserialize(message, ValrWebSocketOrderStatusUpdate.class);
                        subjectOrderStatusUpdate.onNext(orderStatusUpdate.getData());
                        break;

                    case TYPE_FAILED_CANCEL_ORDER:
                        ValrWebSocketFailedCancelOrder failedCancelOrder =
                                deserialize(message, ValrWebSocketFailedCancelOrder.class);
                        subjectFailedCancelOrder.onNext(failedCancelOrder.getData());
                        break;

                    case TYPE_BALANCE_UPDATE:
                        ValrWebSocketBalanceUpdate balanceUpdate =
                                deserialize(message, ValrWebSocketBalanceUpdate.class);
                        subjectBalance.onNext(balanceUpdate.getData());
                        break;
                }
            } catch (JsonProcessingException e) {
                LOG.error("Json parsing error on orderbook: {}", e.getMessage());
            }
        }
    }

    @Override
    public String getSubscriptionUniqueId(String channelName, Object... args) {
        return channelName;
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String chanId = null;
        if (message.has(TYPE)) {
            chanId = message.get(TYPE).asText();
        }
        if (chanId == null) throw new IOException("Can't find TYPE value in socket message: " + message.toString());
        String subscribedChannel = subscribedChannels.get(chanId);
        if (subscribedChannel != null)
            return subscribedChannel;
        return chanId;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if (args.length == 0) throw new IOException("SubscribeMessage: Insufficient arguments");
        List<String> pairs = (List<String>) args[0];
        ValrWebSocketSubscriptionMessage subscribeMessage = new ValrWebSocketSubscriptionMessage(
                 "SUBSCRIBE", channelName, pairs);

        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        ValrWebSocketSubscriptionMessage subscribeMessage = new ValrWebSocketSubscriptionMessage(
                "SUBSCRIBE", channelName, new ArrayList<>());

        return objectMapper.writeValueAsString(subscribeMessage);
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public Observable<ValrWebSocketAggregatedOrderBookUpdate> getOrderBook() {
        return subjectOrderBook.share();
    }

    public Observable<ValrWebSocketMarketSummaryUpdate> getMarketSummaryUpdates() {
        return subjectMarketSummaryUpdate.share();
    }

    public Observable<ValrWebSocketNewTrade> getTrades() {
        return subjectTrade.share();
    }

    public Observable<ValrWebSocketNewTradeData> getAccountTrades() {
        return subjectAccountTrade.share();
    }

    public Observable<ValrWebSocketOpenOrdersUpdate> getOrders() {
        return subjectOpenOrders.share();
    }

    public Observable<ValrWebSocketOrderProcessedData> getOrdersProcessed() {
        return subjectOrderProcessed.share();
    }

    public Observable<ValrWebSocketOrderStatusUpdateData> getOrderStatusUpdates() {
        return subjectOrderStatusUpdate.share();
    }

    public Observable<ValrWebSocketFailedCancelOrderData> getFailedCancelOrders() {
        return subjectFailedCancelOrder.share();
    }

    public Observable<ValrWebSocketBalanceUpdateData> getBalanceUpdates() {
        return subjectBalance.share();
    }

    private <T> T deserialize(JsonNode message, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(message, valueType);
    }
}