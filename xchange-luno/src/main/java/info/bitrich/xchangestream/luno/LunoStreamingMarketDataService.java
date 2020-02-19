package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketCreateUpdate;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketDeleteUpdate;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketOrderBook;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketTradeUpdate;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LunoStreamingMarketDataService implements StreamingMarketDataService {

    private final Map<CurrencyPair, LunoStreamingService> streamingServices;
    private final Map<CurrencyPair, OrderBook> orderBookMap;

    public LunoStreamingMarketDataService(Map<CurrencyPair, LunoStreamingService> streamingServices) {
        this.streamingServices = streamingServices;
        this.orderBookMap = new HashMap<>();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        Observable<Trade> createObservable = Observable.empty();
        Observable<Trade> tradeObservable = Observable.empty();
        Observable<Trade> deleteObservable = Observable.empty();

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                createObservable = pair.getValue().getCreateUpdates().map(
                        s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                );

                tradeObservable = pair.getValue().getTradeUpdates().map(
                        s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                );

                deleteObservable = pair.getValue().getDeleteUpdates().map(
                        s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                );
            }
        }

        return Observable.merge(createObservable, tradeObservable, deleteObservable);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        Observable<OrderBook> orderBookObservable = Observable.empty();
        Observable<OrderBook> tradeBookObservable = Observable.empty();
        Observable<OrderBook> createBookObservable = Observable.empty();
        Observable<OrderBook> deleteBookObservable = Observable.empty();

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                orderBookObservable = pair.getValue().getOrderBook().map(
                        s -> initialiseOrderBook(s, currencyPair)
                );


                tradeBookObservable = pair.getValue().getTradeUpdates().map(
                        s -> updateOrderBookWithTrade(s, currencyPair)
                );

                createBookObservable = pair.getValue().getCreateUpdates().map(
                        s -> addOrderToOrderBook(s, currencyPair)
                );

                deleteBookObservable = pair.getValue().getDeleteUpdates().map(
                        s -> deleteOrderFromOrderBook(s, currencyPair)
                );

            }
        }

        return Observable.merge(orderBookObservable, tradeBookObservable, createBookObservable, deleteBookObservable);
    }

    private OrderBook initialiseOrderBook(LunoWebSocketOrderBook lunoOrderBook, CurrencyPair currencyPair){
        OrderBook orderBook = LunoStreamingAdapters.adaptOrderBook(lunoOrderBook, currencyPair);
        orderBookMap.put(currencyPair, orderBook);

        return orderBook;
    }

    private OrderBook updateOrderBookWithTrade(LunoWebSocketTradeUpdate lunoTradeUpdate, CurrencyPair currencyPair) {
        Trade trade = LunoStreamingAdapters.adaptTrade(lunoTradeUpdate, currencyPair);
        OrderBook orderBook = orderBookMap.get(currencyPair);
        if (orderBook != null) {
            List<LimitOrder> asks = orderBook.getAsks();
            Iterator asksIterator = asks.iterator();

            while (asksIterator.hasNext()) {
                LimitOrder order = (LimitOrder) asksIterator.next();
                if (order.getId().equals(trade.getId())) {
                    asks.set(asks.indexOf(order), new LimitOrder(
                                    order.getType(),
                                    order.getOriginalAmount().subtract(trade.getOriginalAmount()),
                                    currencyPair,
                                    order.getId(),
                                    null,
                                    order.getLimitPrice()
                            )
                    );
                }
            }

            List<LimitOrder> buys = orderBook.getBids();
            Iterator bidsIterator = buys.iterator();

            while (bidsIterator.hasNext()) {
                LimitOrder order = (LimitOrder) bidsIterator.next();
                if (order.getId().equals(trade.getId())) {
                    buys.set(buys.indexOf(order), new LimitOrder(
                                    order.getType(),
                                    order.getOriginalAmount().subtract(trade.getOriginalAmount()),
                                    currencyPair,
                                    order.getId(),
                                    null,
                                    order.getLimitPrice()
                            )
                    );
                }
            }

            orderBookMap.put(currencyPair, orderBook);
        }

        return orderBook;
    }

    private OrderBook addOrderToOrderBook(LunoWebSocketCreateUpdate lunoOrderCreate, CurrencyPair currencyPair) {
        OrderBook orderBook = orderBookMap.get(currencyPair);
        LimitOrder order = LunoStreamingAdapters.adaptLimitOrder(lunoOrderCreate, currencyPair);
        if (orderBook != null) {
            if (order.getType() == Order.OrderType.ASK) {
                orderBook.getAsks().add(order);
            } else {
                orderBook.getAsks().add(order);
            }

            orderBookMap.put(currencyPair, orderBook);
        }

        return orderBook;
    }


    private OrderBook deleteOrderFromOrderBook(LunoWebSocketDeleteUpdate lunoOrderDelete, CurrencyPair currencyPair) {
        OrderBook orderBook = orderBookMap.get(currencyPair);
        if (orderBook != null) {
            List<LimitOrder> asks = orderBook.getAsks();
            Iterator asksIterator = asks.iterator();

            while (asksIterator.hasNext()) {
                LimitOrder order = (LimitOrder) asksIterator.next();
                if (order.getId().equals(lunoOrderDelete.getOrderId())) {
                    asksIterator.remove();
                }
            }

            List<LimitOrder> bids = orderBook.getBids();
            Iterator bidsIterator = bids.iterator();

            while (bidsIterator.hasNext()) {
                LimitOrder order = (LimitOrder) bidsIterator.next();
                if (order.getId().equals(lunoOrderDelete.getOrderId())) {
                    bidsIterator.remove();
                }
            }

            orderBookMap.put(currencyPair, orderBook);
        }

        return orderBook;
    }
}
