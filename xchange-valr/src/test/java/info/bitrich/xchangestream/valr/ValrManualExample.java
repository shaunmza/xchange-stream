package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.utils.CertHelper;
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
        CertHelper.trustAllCerts();

        ExchangeSpecification defaultExchangeSpecification = new ExchangeSpecification(ValrStreamingExchange.class);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) ValrManualExample::rateLimit);

        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
/*
        defaultExchangeSpecification.setProxyHost("localhost");
        defaultExchangeSpecification.setProxyPort(9999);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_HOST, "localhost");
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_PORT, 8889);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ACCEPT_ALL_CERITICATES, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ENABLE_LOGGING_HANDLER, true);
*/

        StreamingExchange exchange = getExchange();//StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
        exchange.connect().blockingAwait();

        Observable<OrderBook> orderBookObserver = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_ZAR);
        Disposable orderBookSubscriber = orderBookObserver.subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> {
            LOG.error("ERROR in getting order book: ", throwable);
        });

        Disposable tickerSubscriber = exchange.getStreamingMarketDataService().getTicker(CurrencyPair.ETH_BTC).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        Disposable tradesSubscriber = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_ZAR)
                .subscribe(trade -> {
                    LOG.info("TRADE: {}", trade);
                }, throwable -> {
                    LOG.error("ERROR in getting trade: ", throwable);
                });

        Thread.sleep(10000);

        tickerSubscriber.dispose();
        tradesSubscriber.dispose();
        orderBookSubscriber.dispose();

        LOG.info("disconnecting...");
        exchange.disconnect().subscribe(() -> LOG.info("disconnected"));

        rateLimiter.shutdown();
    }

    public static StreamingExchange getExchange() {

        ExchangeSpecification exSpec = new ExchangeSpecification(ValrStreamingExchange.class);
        exSpec.setApiKey("46e5ed5776c771144b05d645fd5a59d9e7b4040d88a0b14fce0bf1fe5d87f360");
        exSpec.setSecretKey("ddfe4f8983ce9513d6d48cf8df1aa9a145e07e6db137faccb9243e91b75f379f");

        return StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
    }

}
