package info.bitrich.xchangestream.valr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.valr.dto.*;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.knowm.xchange.service.BaseParamsDigest.HMAC_SHA_384;

public class ValrStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(ValrStreamingService.class);

    static final String CHANNEL_USER_POSITIONS = "userPositions";
    static final String CHANNEL_USER_BALANCE_UPDATES = "userBalanceUpdates";
    static final String CHANNEL_USER_BALANCES = "userBalances";
    static final String CHANNEL_USER_ORDER_UPDATES = "userOrderUpdates";
    static final String CHANNEL_USER_ORDERS = "userOrders";
    static final String CHANNEL_USER_TRADES = "userTrades";
    static final String CHANNEL_USER_PRE_TRADES = "userPreTrades";

    private static final String INFO = "info";
    private static final String ERROR = "error";
    private static final String CHANNEL_ID = "chanId";
    private static final String SUBSCRIBED = "subscribed";
    private static final String UNSUBSCRIBED = "unsubscribed";
    private static final String ERROR_CODE = "code";
    private static final String AUTH = "auth";
    private static final String STATUS = "status";
    private static final String MESSAGE = "msg";
    private static final String TYPE = "type";
    private static final String VERSION = "version";

    public static final String TYPE_AGGREGATED_ORDERBOOK_UPDATE = "AGGREGATED_ORDERBOOK_UPDATE";
    public static final String TYPE_MARKET_SUMMARY_UPDATE = "MARKET_SUMMARY_UPDATE";
    public static final String TYPE_NEW_TRADE_BUCKET = "NEW_TRADE_BUCKET";
    public static final String TYPE_NEW_TRADE = "NEW_TRADE";
    public static final String TYPE_NEW_ACCOUNT_HISTORY_RECORD = "NEW_ACCOUNT_HISTORY_RECORD";
    public static final String TYPE_BALANCE_UPDATE = "BALANCE_UPDATE";
    public static final String TYPE_NEW_ACCOUNT_TRADE = "NEW_ACCOUNT_TRADE";
    public static final String TYPE_INSTANT_ORDER_COMPLETED = "INSTANT_ORDER_COMPLETED";
    public static final String TYPE_OPEN_ORDERS_UPDATE = "OPEN_ORDERS_UPDATE";
    public static final String TYPE_ORDER_PROCESSED = "ORDER_PROCESSED";
    public static final String TYPE_ORDER_STATUS_UPDATE = "ORDER_STATUS_UPDATE";
    public static final String TYPE_FAILED_CANCEL_ORDER = "FAILED_CANCEL_ORDER";
    public static final String TYPE_NEW_PENDING_RECEIVE = "NEW_PENDING_RECEIVE";
    public static final String TYPE_SEND_STATUS_UPDATE = "SEND_STATUS_UPDATE";

    private static final int CALCULATION_BATCH_SIZE = 8;
    private static final List<String> WALLETS = Arrays.asList("exchange", "margin", "funding");

    //private final PublishSubject<ValrWebSocketPreTrade> subjectPreTrade = PublishSubject.create();
    private final PublishSubject<ValrWebSocketNewTradeBucketData> subjectTradeBucket = PublishSubject.create();
    private final PublishSubject<ValrWebSocketNewTradeData> subjectTrade = PublishSubject.create();
    private final PublishSubject<ValrWebSocketNewTradeData> subjectAccountTrade = PublishSubject.create();
    private final PublishSubject<ValrWebSocketAggregatedOrderBookData> subjectOrderBook = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOpenOrdersUpdate> subjectOpenOrders = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOrderProcessedData> subjectOrderProcessed = PublishSubject.create();
    private final PublishSubject<ValrWebSocketOrderStatusUpdateData> subjectOrderStatusUpdate = PublishSubject.create();
    private final PublishSubject<ValrWebSocketFailedCancelOrderData> subjectFailedCancelOrder = PublishSubject.create();
    private final PublishSubject<ValrWebSocketBalanceUpdateData> subjectBalance = PublishSubject.create();

    private static final int SUBSCRIPTION_FAILED = 10300;

    private String apiKey;
    private String apiSecret;

    private final Map<String, String> subscribedChannels = new HashMap<>();
    private final SynchronizedValueFactory<Long> nonceFactory;

    private final BlockingQueue<String> calculationQueue = new LinkedBlockingQueue<>();
    private Disposable calculator;

    public ValrStreamingService(String apiUrl,
                                SynchronizedValueFactory<Long> nonceFactory) {
        super(apiUrl, Integer.MAX_VALUE);
        this.nonceFactory = nonceFactory;
    }

    @Override
    public Completable connect() {
        return super.connect().doOnComplete(() -> {
            //this.calculator = Observable.interval(1, TimeUnit.SECONDS).subscribe(x -> requestCalcs());
        });
    }

    @Override
    public Completable disconnect() {
        if (calculator != null)
            calculator.dispose();
        return super.disconnect();
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
                switch (event.textValue()) {
                    case TYPE_AGGREGATED_ORDERBOOK_UPDATE:
                        ValrWebSocketAggregatedOrderBookUpdate orderBookUpdate =
                                deserialize(message, ValrWebSocketAggregatedOrderBookUpdate.class);
                        subjectOrderBook.onNext(orderBookUpdate.getData());

                    case TYPE_NEW_TRADE_BUCKET:
                        ValrWebSocketNewTradeBucket newTradeBucket =
                                deserialize(message, ValrWebSocketNewTradeBucket.class);
                        subjectTradeBucket.onNext(newTradeBucket.getData());

                    case TYPE_NEW_TRADE:
                        ValrWebSocketNewTrade newTrade =
                                deserialize(message, ValrWebSocketNewTrade.class);
                        subjectTrade.onNext(newTrade.getData());

                    case TYPE_NEW_ACCOUNT_TRADE:
                        ValrWebSocketNewTrade newAccountTrade =
                                deserialize(message, ValrWebSocketNewTrade.class);
                        subjectAccountTrade.onNext(newAccountTrade.getData());

                    case TYPE_OPEN_ORDERS_UPDATE:
                        ValrWebSocketOpenOrdersUpdate openOrdersUpdate =
                                deserialize(message, ValrWebSocketOpenOrdersUpdate.class);
                        subjectOpenOrders.onNext(openOrdersUpdate);

                    case TYPE_ORDER_PROCESSED:
                        ValrWebSocketOrderProcessed orderProcessed =
                                deserialize(message, ValrWebSocketOrderProcessed.class);
                        subjectOrderProcessed.onNext(orderProcessed.getData());

                    case TYPE_ORDER_STATUS_UPDATE:
                        ValrWebSocketOrderStatusUpdate orderStatusUpdate =
                                deserialize(message, ValrWebSocketOrderStatusUpdate.class);
                        subjectOrderStatusUpdate.onNext(orderStatusUpdate.getData());

                    case TYPE_FAILED_CANCEL_ORDER:
                        ValrWebSocketFailedCancelOrder failedCancelOrder =
                                deserialize(message, ValrWebSocketFailedCancelOrder.class);
                        subjectFailedCancelOrder.onNext(failedCancelOrder.getData());

                    case TYPE_BALANCE_UPDATE:
                        ValrWebSocketBalanceUpdate balanceUpdate =
                                deserialize(message, ValrWebSocketBalanceUpdate.class);
                        subjectBalance.onNext(balanceUpdate.getData());
                }
            } catch (JsonProcessingException e) {
                LOG.error("Json parsing error on orderbook: {}", e.getMessage());
            }
        } else {
            try {
                if ("0".equals(getChannelNameFromMessage(message)) && message.isArray() && message.size() == 3) {
                    //processAuthenticatedMessage(message);
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to get channel name from message", e);
            }
            super.handleMessage(message);
        }
    }

    @Override
    public String getSubscriptionUniqueId(String channelName, Object... args) {
        if (args.length > 0) {
            return channelName + "-" + args[0].toString();
        } else {
            return channelName;
        }
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String chanId = null;
        if (message.has(CHANNEL_ID)) {
            chanId = message.get(CHANNEL_ID).asText();
        } else {
            JsonNode jsonNode = message.get(0);
            if (jsonNode != null) {
                chanId = message.get(0).asText();
            }
        }
        if (chanId == null) throw new IOException("Can't find CHANNEL_ID value in socket message: " + message.toString());
        String subscribedChannel = subscribedChannels.get(chanId);
        if (subscribedChannel != null)
            return subscribedChannel;
        return chanId; // In case bitfinex adds new channels, just fallback to the name in the message
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if (args.length < 2) throw new IOException("SubscribeMessage: Insufficient arguments");
        ValrWebSocketSubscriptionMessage subscribeMessage = new ValrWebSocketSubscriptionMessage(
                channelName, "SUBSCRIBE", (String) args[0], (List<String>) args[1]);

        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        /*String channelId = null;
        String event = null;
        List<String> pairs = null;
        for (Map.Entry<String, String> entry : subscribedChannels.entrySet()) {
            if (entry.getValue().equals(channelName)) {
                channelId = entry.getKey();
                event = entry.
                break;
            }
        }

        if (channelId == null) throw new IOException("Can't find channel unique name");

        ValrWebSocketSubscriptionMessage subscribeMessage = new ValrWebSocketSubscriptionMessage(
                channelName, "SUBSCRIBE", (String) args[0], (List<String>) args[1]);

        return objectMapper.writeValueAsString(subscribeMessage);
        */
        return null;
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    boolean isAuthenticated() {
        return StringUtils.isNotEmpty(apiKey);
    }

    private void auth() {
        long nonce = nonceFactory.createValue();
        String payload = "AUTH" + nonce;
        String signature;
        try {
            Mac macEncoder = Mac.getInstance(HMAC_SHA_384);
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA_384);
            macEncoder.init(secretKeySpec);
            byte[] result = macEncoder.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            signature = DatatypeConverter.printHexBinary(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            LOG.error("auth. Sign failed error={}", e.getMessage());
            return;
        }
        /*BitfinexWebSocketAuth message = new BitfinexWebSocketAuth(
                apiKey, payload, String.valueOf(nonce), signature.toLowerCase()
        );*/
        //sendObjectMessage(message);
    }

    public Observable<ValrWebSocketAggregatedOrderBookData> getOrderBook() {
        return subjectOrderBook.share();
    }

    /*Observable<ValrWebSocketPreTrade> getPreTrades() {
        return subjectPreTrade.share();
    }*/

    public Observable<ValrWebSocketNewTradeBucketData> getTradeBuckets() {
        return subjectTradeBucket.share();
    }

    public Observable<ValrWebSocketNewTradeData> getTrades() {
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