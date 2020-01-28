package info.bitrich.xchangestream.luno;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.luno.dto.*;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class LunoStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(LunoStreamingService.class);

    private final PublishSubject<LunoWebSocketCreateUpdate> subjectCreate = PublishSubject.create();
    private final PublishSubject<LunoWebSocketTradeUpdate> subjectTrade = PublishSubject.create();
    private final PublishSubject<LunoWebSocketDeleteUpdate> subjectDelete = PublishSubject.create();
    private final PublishSubject<LunoWebSocketStatusUpdate> subjectStatus = PublishSubject.create();
    private final PublishSubject<LunoWebSocketOrderBook> subjectOrderBook = PublishSubject.create();

    private String apiKey;
    private String apiSecret;

    private static final String SEQUENCE = "sequence";
    private static final String TRADE_UPDATES = "trade_updates";
    private static final String CREATE_UPDATE = "create_update";
    private static final String DELETE_UPDATE = "delete_update";
    private static final String STATUS_UPDATE = "status_update";
    private static final String STATUS = "status";
    private static final String TIMESTAMP = "timestamp";


    public LunoStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    public Completable connect(ProductSubscription... args) {
        return super.connect();
    }

    @Override
    public Completable disconnect() {
        return super.disconnect();
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    /*@Override
    public boolean processArrayMassageSeparately() {
        return false;
    }*/

    @Override
    public void handleMessage(JsonNode message) {

        JsonNode sequence = message.get(SEQUENCE);
        if (sequence == null) {
            LOG.error("Invalid message received, skipping. {}", message.toString());
            return;
        }

        JsonNode status = message.get(STATUS);
        if (status != null) {
            // if we have `status` in the root, it is the message sent after connecting with the orderbook details.
            try {
                LunoWebSocketOrderBook lunoOrderBook =
                    deserialize(message, LunoWebSocketOrderBook.class);
                subjectOrderBook.onNext(lunoOrderBook);
            } catch (JsonProcessingException e) {
                LOG.error("Json parsing error on orderbook: {}", e.getMessage());
            }
        } else {
            try {
                LunoWebSocketUpdatesWrapper updates = deserialize(message, LunoWebSocketUpdatesWrapper.class);
                Iterator trades = updates.getTradeUpdates().iterator();

                while(trades.hasNext()) {
                    subjectTrade.onNext((LunoWebSocketTradeUpdate)trades.next());
                }
                subjectCreate.onNext(updates.getCreateUpdate());
                subjectDelete.onNext(updates.getDeleteUpdate());
                subjectStatus.onNext(updates.getStatusUpdate());
            } catch (JsonProcessingException e) {
                LOG.error("Json parsing error on updates: {}", e.getMessage());
            }
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
    protected String getChannelNameFromMessage(JsonNode message) {
        // Luno does not have multiple channels, we need to connect multiple times.
        return "";
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        // Luno does not have multiple channels, we need to connect multiple times.
        return "";
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        // Luno does not have multiple channels, so no unsubscribe either.
        return "";
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

   Observable<LunoWebSocketCreateUpdate> getCreateUpdates() {
        return subjectCreate.share();
    }

    Observable<LunoWebSocketTradeUpdate> getTradeUpdates() {
        return subjectTrade.share();
    }

    Observable<LunoWebSocketDeleteUpdate> getDeleteUpdates() {
        return subjectDelete.share();
    }

    Observable<LunoWebSocketStatusUpdate> getStatusUpdates() {
        return subjectStatus.share();
    }

    public Observable<LunoWebSocketOrderBook> getOrderBook() {
        return subjectOrderBook.share();
    }

    private <T> T deserialize(JsonNode message, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(message, valueType);
    }
}