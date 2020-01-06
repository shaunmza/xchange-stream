package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.valr.ValrExchange;

import java.util.ArrayList;
import java.util.List;

public class ValrStreamingExchange extends ValrExchange implements StreamingExchange {

    static final public String API_URI = "wss://api.valr.com";

    private String ACCOUNT_PATH = "/ws/account";
    private String TRADE_PATH = "/ws/trade";

    private ValrStreamingService streamingServiceForAccount;
    private ValrStreamingService streamingServiceForTrade;
    private ValrStreamingMarketDataService streamingMarketDataService;
    private ValrStreamingTradeService streamingTradeService;
    private ValrStreamingAccountService streamingAccountService;

    @Override
    protected void initServices() {
        super.initServices();
        this.streamingServiceForTrade = createStreamingService(TRADE_PATH);
        this.streamingServiceForAccount = createStreamingService(ACCOUNT_PATH);

    }

    private ValrStreamingService createStreamingService(String path) {
        ValrStreamingService streamingService = new ValrStreamingService(API_URI + path, getNonceFactory());
        applyStreamingSpecification(getExchangeSpecification(), streamingService);
        if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
            streamingService.setApiKey(exchangeSpecification.getApiKey());
            streamingService.setApiSecret(exchangeSpecification.getSecretKey());
        }
        return streamingService;
    }

    @Override
    public Completable connect(ProductSubscription... args) {

        List<Completable> completables = new ArrayList<>();
        completables.add(streamingServiceForAccount.connect());
        completables.add(streamingServiceForTrade.connect());

        this.streamingMarketDataService = new ValrStreamingMarketDataService(streamingServiceForTrade);
        this.streamingTradeService = new ValrStreamingTradeService(streamingServiceForTrade);
        this.streamingAccountService = new ValrStreamingAccountService(streamingServiceForAccount);

        return Completable.concat(completables);
    }

    @Override
    public Completable disconnect() {

        List<Completable> completables = new ArrayList<>();
        completables.add(streamingServiceForAccount.disconnect());
        completables.add(streamingServiceForTrade.disconnect());

        return Completable.concat(completables);
    }

    @Override
    public boolean isAlive() {
        return streamingServiceForAccount.isSocketOpen() && streamingServiceForTrade.isSocketOpen();
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return Observable.concat(
                streamingServiceForAccount.subscribeConnectionSuccess(),
                streamingServiceForTrade.subscribeConnectionSuccess());
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);

        return spec;
    }

    @Override
    public ValrStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public ValrStreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public ValrStreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingServiceForAccount.useCompressedMessages(compressedMessages); }
}
