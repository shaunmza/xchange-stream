package info.bitrich.xchangestream.valr;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketAggregatedOrderBookUpdate;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketMarketSummaryUpdate;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketNewTrade;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ValrStreamingMarketDataService implements StreamingMarketDataService {

    private final ValrStreamingService service;
    private List<String> orderBookSubscriptions;
    private List<String> tickerSubscriptions;
    private List<String> tradeSubscriptions;

    private Observable<ValrWebSocketAggregatedOrderBookUpdate> subscribedOrderbooks;
    private Observable<ValrWebSocketMarketSummaryUpdate> subscribedMarketSummary;
    private Observable<ValrWebSocketNewTrade> subscribedNewTrade;

    public ValrStreamingMarketDataService(ValrStreamingService service, ProductSubscription subscriptions) {
        this.service = service;
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        orderBookSubscriptions = new ArrayList<>();
        orderBookSubscriptions = addSubscriptions(subscriptions.getOrderBook().iterator(), orderBookSubscriptions);
        subscribedOrderbooks = service.subscribeChannel(
                service.TYPE_AGGREGATED_ORDERBOOK_UPDATE,
                new Object[]{orderBookSubscriptions})
                .map(s -> mapper.treeToValue(s, ValrWebSocketAggregatedOrderBookUpdate.class));

        tickerSubscriptions = new ArrayList<>();
        tickerSubscriptions = addSubscriptions(subscriptions.getTicker().iterator(), tickerSubscriptions);
        subscribedMarketSummary = service.subscribeChannel(
                service.TYPE_MARKET_SUMMARY_UPDATE,
                new Object[]{tickerSubscriptions})
                .map(s -> mapper.treeToValue(s, ValrWebSocketMarketSummaryUpdate.class));

        tradeSubscriptions = new ArrayList<>();
        tradeSubscriptions = addSubscriptions(subscriptions.getTrades().iterator(), tradeSubscriptions);
        subscribedNewTrade = service.subscribeChannel(
                service.TYPE_NEW_TRADE,
                new Object[]{tradeSubscriptions})
                .map(s -> mapper.treeToValue(s, ValrWebSocketNewTrade.class));
    }

    private List<String> addSubscriptions(Iterator iterator, List<String> list) {
        while(iterator.hasNext()) {
            CurrencyPair currencyPair = (CurrencyPair)iterator.next();
            if (currencyPair != null) {
                String pair = currencyPair.base.toString() + currencyPair.counter.toString();
                list.add(pair);
            }
        }

        return list;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        Observable<OrderBook> orderBookObservable = service.getOrderBook()
                .filter(o -> currencyPair.equals(ValrStreamingAdapters.toCurrencyPair(o.getCurrencyPairSymbol())))
                .map(s -> ValrStreamingAdapters.adaptOrderBook(s));

        return Observable.merge(subscribedOrderbooks
                .map(s -> ValrStreamingAdapters.adaptOrderBook(s)), orderBookObservable);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        Observable<Ticker> tickerObservable = service.getMarketSummaryUpdates()
                .filter(o -> currencyPair.equals(ValrStreamingAdapters.toCurrencyPair(o.getCurrencyPairSymbol())))
                .map(ValrStreamingAdapters::adaptTicker);

        return Observable.merge(subscribedMarketSummary
                .map(s -> ValrStreamingAdapters.adaptTicker(s)), tickerObservable);
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        Observable<Trade> tradeObservable = service.getTrades()
                .filter(o -> currencyPair.equals(ValrStreamingAdapters.toCurrencyPair(o.getCurrencyPairSymbol())))
                .map(ValrStreamingAdapters::adaptTrade);

        return Observable.merge(subscribedNewTrade
                .map(s -> ValrStreamingAdapters.adaptTrade(s)), tradeObservable);
    }
}