package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketAuth;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.luno.LunoExchange;
import org.knowm.xchange.luno.LunoUtil;

import java.util.*;

public class LunoStreamingExchange extends LunoExchange implements StreamingExchange {

    static final public String API_URI = "wss://ws.luno.com/api/1/stream/";

    private Map<CurrencyPair, LunoStreamingService> streamingServices;
    private LunoStreamingMarketDataService streamingMarketDataService;
    private LunoStreamingTradeService streamingTradeService;
    private LunoStreamingAccountService streamingAccountService;

    private String apiKey;
    private String apiSecret;

    @Override
    protected void initServices() {
        super.initServices();
    }

    private Map<CurrencyPair, LunoStreamingService> createStreamingServices(List<CurrencyPair> currencyPairs) {
        Map<CurrencyPair, LunoStreamingService> streamingServices = new HashMap<>();
        currencyPairs.forEach((currencyPair -> {
            LunoStreamingService streamingService = new LunoStreamingService(API_URI + LunoUtil.toLunoPair(currencyPair));
            applyStreamingSpecification(getExchangeSpecification(), streamingService);
            if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
                setApiKey(exchangeSpecification.getApiKey());
                setApiSecret(exchangeSpecification.getSecretKey());
            }

            streamingServices.put(currencyPair, streamingService);
                })

        );

        return streamingServices;
    }


    @Override
    public Completable connect(ProductSubscription... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Subscriptions must be made at connection time");
        }

        ProductSubscription subscriptions = args[0];

        List<CurrencyPair> currencyPairs = new ArrayList<>();
        currencyPairs.addAll(subscriptions.getOrderBook());
        currencyPairs.addAll(subscriptions.getOrders());
        currencyPairs.addAll(subscriptions.getTicker());
        currencyPairs.addAll(subscriptions.getTrades());
        currencyPairs.addAll(subscriptions.getUserTrades());

        Map<CurrencyPair, LunoStreamingService> streamingServices;
        this.streamingServices = createStreamingServices(currencyPairs);


        streamingMarketDataService = new LunoStreamingMarketDataService(this.streamingServices);
        streamingAccountService = new LunoStreamingAccountService(this.streamingServices);
        streamingTradeService = new LunoStreamingTradeService(this.streamingServices);

        List<Completable> completables = new ArrayList<>();
        this.streamingServices.forEach(((currencyPair, lunoStreamingService) -> {
            completables.add(lunoStreamingService.connect(args));
        }));

        return Completable.concat(completables);
    }

    @Override
    public Completable disconnect() {
        List<Completable> completables = new ArrayList<>();
        this.streamingServices.forEach(((currencyPair, lunoStreamingService) -> {
            completables.add(lunoStreamingService.disconnect());
        }));

        return Completable.concat(completables);
    }

    @Override
    public boolean isAlive() {
        boolean isSocketOpen = true;
        Map<CurrencyPair, Boolean> o = null;
        this.streamingServices.forEach((currencyPair, lunoStreamingService) -> {
            o.put(currencyPair, lunoStreamingService.isSocketOpen());
        });

        for (Map.Entry<CurrencyPair, Boolean> pair : o.entrySet()) {
            isSocketOpen = isSocketOpen && pair.getValue();
        }

        return isSocketOpen;
    }

    /*@Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }*/

    @Override
    public Observable<Object> connectionSuccess() {
        Observable a = null;

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (a == null){
                a = pair.getValue().subscribeConnectionSuccess();
            } else {
                a = pair.getValue().subscribeConnectionSuccess().mergeWith(a);
            }
           /* LunoWebSocketAuth message = new LunoWebSocketAuth(
                    this.apiKey, this.apiSecret
            );*/
            pair.getValue().sendMessage(String.format("{   \"api_key_id\": \"%s\",   \"api_key_secret\": \"%s\" }", this.apiKey, this.apiSecret));
        }

        return a;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);

        return spec;
    }


    @Override
    public LunoStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public LunoStreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public LunoStreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        this.streamingServices.forEach((currencyPair, lunoStreamingService) -> {
            lunoStreamingService.useCompressedMessages(compressedMessages);
        });
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

}
