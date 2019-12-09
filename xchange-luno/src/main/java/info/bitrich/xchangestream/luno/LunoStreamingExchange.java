package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.luno.LunoExchange;

public class LunoStreamingExchange extends LunoExchange implements StreamingExchange {

    static final public String API_URI = "wss://ws.luno.com/api/1/stream/";

    private LunoStreamingService streamingService;
    private LunoStreamingMarketDataService streamingMarketDataService;
    //private LunoStreamingService streamingTradeService;
    //private LunoStreamingService streamingAccountService;

    @Override
    protected void initServices() {
        super.initServices();
        this.streamingService = createStreamingService();
        this.streamingMarketDataService = new LunoStreamingMarketDataService(streamingService);
    }

    private LunoStreamingService createStreamingService() {
        LunoStreamingService streamingService = new LunoStreamingService(API_URI);
        applyStreamingSpecification(getExchangeSpecification(), streamingService);
        if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
            streamingService.setApiKey(exchangeSpecification.getApiKey());
            streamingService.setApiSecret(exchangeSpecification.getSecretKey());
        }
        return streamingService;
    }


    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect(args);
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess();
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

    public LunoStreamingService getStreamingService() {
        return streamingService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }
}
