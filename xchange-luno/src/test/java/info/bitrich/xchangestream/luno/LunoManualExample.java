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
            LOG.warn("Luno connection throttle control has been interrupted");
        }
    }

    public static void main(String[] args) throws Exception {

        ExchangeSpecification defaultExchangeSpecification = new ExchangeSpecification(LunoStreamingExchange.class);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) LunoManualExample::rateLimit);

        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);

        StreamingExchange exchange = getExchange();
        ProductSubscription subscription = ProductSubscription.create()
                .addTicker(CurrencyPair.ETH_BTC)
                .addTicker(CurrencyPair.BTC_ZAR)
                .build();

        exchange.connect(subscription).blockingAwait();

        Observable<OrderBook> orderBookObserver = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_ZAR);
        Disposable orderBookSubscriber = orderBookObserver.subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
            LOG.info("Order count: {}", orderBook.getBids().size() + orderBook.getAsks().size());
        }, throwable -> {
            LOG.error("ERROR in getting order book: ", throwable);
        });

        Observable<OrderBook> orderBookObserverETH = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.ETH_BTC);
        Disposable orderBookSubscriberETH = orderBookObserverETH.subscribe(orderBook -> {
            LOG.info("ETH First ask: {}", orderBook.getAsks().get(0));
            LOG.info("ETH First bid: {}", orderBook.getBids().get(0));
            LOG.info("ETH Order count: {}", orderBook.getBids().size() + orderBook.getAsks().size());
        }, throwable -> {
            LOG.error("ETH ERROR in getting order book: ", throwable);
        });

       Disposable orderChangesSubscriber = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.ETH_BTC)
                .subscribe(order -> {
                    LOG.info("ORDER: {}", order);
                }, throwable -> {
                    LOG.error("ERROR in getting order: ", throwable);
                });

        Thread.sleep(60000);

        orderChangesSubscriber.dispose();
        orderBookSubscriber.dispose();
        orderBookSubscriberETH.dispose();

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
