package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.MINUTES;

public class ValrManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(ValrManualExample.class);

    private static final TimedSemaphore rateLimiter = new TimedSemaphore(1, MINUTES, 15);
    private static void rateLimit() {
        try {
            rateLimiter.acquire();
        } catch (InterruptedException e) {
            LOG.warn("Valr connection throttle control has been interrupted");
        }
    }

    public static void main(String[] args) throws Exception {

        ExchangeSpecification defaultExchangeSpecification = new ExchangeSpecification(ValrStreamingExchange.class);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) ValrManualExample::rateLimit);

        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);

        StreamingExchange exchange = getExchange();
        CurrencyPair EthZar = new CurrencyPair(Currency.ETH, Currency.ZAR);
        ProductSubscription subscription = ProductSubscription.create()
                .addOrderbook(EthZar)
                .addOrderbook(CurrencyPair.BTC_ZAR)
                .addTicker(EthZar)
                .addTicker(CurrencyPair.BTC_ZAR)
                .addTrades(EthZar)
                .addTrades(CurrencyPair.BTC_ZAR)
                .build();

        exchange.connect(subscription).blockingAwait();

        Observable<OrderBook> orderBookObserver = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_ZAR);
        Disposable orderBookSubscriber = orderBookObserver.subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> {
            LOG.error("ERROR in getting order book: ", throwable);
        });


        Observable<OrderBook> orderBookETHObserver = exchange.getStreamingMarketDataService().getOrderBook(EthZar);
        Disposable orderBookETHSubscriber = orderBookETHObserver.subscribe(orderBook -> {
            LOG.info("ETH First ask: {}", orderBook.getAsks().get(0));
            LOG.info("ETH First bid: {}", orderBook.getBids().get(0));
        }, throwable -> {
            LOG.error("ERROR in getting ETH order book: ", throwable);
        });

        Disposable tradesSubscriber = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_ZAR)
                .subscribe(trade -> {
                    LOG.info("TRADE: {}", trade);
                }, throwable -> {
                    LOG.error("ERROR in getting trade: ", throwable);
                });

        Disposable tradesSubscriberETH = exchange.getStreamingMarketDataService().getTrades(EthZar)
                .subscribe(trade -> {
                    LOG.info("TRADE ETH: {}", trade);
                }, throwable -> {
                    LOG.error("ERROR in getting trade ETH: ", throwable);
                });

        Disposable tickerSubscriber = exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_ZAR)
                .subscribe(ticker -> {
                    LOG.info("TICKER: {}", ticker);
                }, throwable -> {
                    LOG.error("ERROR in getting ticker: ", throwable);
                });

        Disposable tickerSubscriberETH = exchange.getStreamingMarketDataService().getTicker(EthZar)
                .subscribe(ticker -> {
                    LOG.info("TICKER ETH: {}", ticker);
                }, throwable -> {
                    LOG.error("ERROR in getting ticker ETH: ", throwable);
                });

        Thread.sleep(60000);

        tickerSubscriber.dispose();
        tickerSubscriberETH.dispose();
        tradesSubscriber.dispose();
        tradesSubscriberETH.dispose();
        orderBookSubscriber.dispose();
        orderBookETHSubscriber.dispose();

        Thread.sleep(1000);

        LOG.info("disconnecting...");
        exchange.disconnect().subscribe(() -> LOG.info("disconnected"));

        rateLimiter.shutdown();
    }

    public static StreamingExchange getExchange() {

        ExchangeSpecification exSpec = new ExchangeSpecification(ValrStreamingExchange.class);
        exSpec.setApiKey("");
        exSpec.setSecretKey("");

        return StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
    }

}
