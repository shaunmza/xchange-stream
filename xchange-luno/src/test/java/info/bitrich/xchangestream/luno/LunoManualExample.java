package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.MINUTES;

public class LunoManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(LunoManualExample.class);

    private static final TimedSemaphore rateLimiter = new TimedSemaphore(1, MINUTES, 15);
    private static void rateLimit() {
        try {
            rateLimiter.acquire();
        } catch (InterruptedException e) {
            LOG.warn("Valr connection throttle control has been interrupted");
        }
    }

    public static void main(String[] args) throws Exception {

        ExchangeSpecification defaultExchangeSpecification = new ExchangeSpecification(LunoStreamingExchange.class);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) LunoManualExample::rateLimit);

        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);

        StreamingExchange exchange = getExchange();
        ProductSubscription subscription = ProductSubscription.create()
                .addTicker(CurrencyPair.BTC_ZAR)
                .addTicker(CurrencyPair.ETH_BTC)
                .build();

        exchange.connect(subscription).blockingAwait();

        exchange.connectionSuccess();

        Observable<OrderBook> orderBookObserver = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_ZAR);
        Disposable orderBookSubscriber = orderBookObserver.subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> {
            LOG.error("ERROR in getting order book: ", throwable);
        });

        Disposable tradesSubscriber = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_ZAR)
                .subscribe(trade -> {
                    LOG.info("TRADE: {}", trade);
                }, throwable -> {
                    LOG.error("ERROR in getting trade: ", throwable);
                });

        Thread.sleep(60000);

        tradesSubscriber.dispose();
        orderBookSubscriber.dispose();

        LOG.info("disconnecting...");
        exchange.disconnect().subscribe(() -> LOG.info("disconnected"));

        rateLimiter.shutdown();
    }

    public static StreamingExchange getExchange() {

        ExchangeSpecification exSpec = new ExchangeSpecification(LunoStreamingExchange.class);
        exSpec.setApiKey("");
        exSpec.setSecretKey("");

        return StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
    }

}
