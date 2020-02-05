package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketTradeUpdate;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LunoStreamingTradeService implements StreamingTradeService {

    private Map<CurrencyPair, LunoStreamingService> streamingServices;

    public LunoStreamingTradeService(Map<CurrencyPair, LunoStreamingService> streamingServices) {
        this.streamingServices = streamingServices;
    }

    /**
     * Gets a stream of all trade updates filtered by currency pair to which we are subscribed.
     *
     * @return The stream of trade updates filtered by currency pair.
     */
    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        Observable<Order> orderObservable = null;

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                if (orderObservable == null) {
                    orderObservable = pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptLimitOrder(s, currencyPair)
                    );
                } else {
                    orderObservable.mergeWith(pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptLimitOrder(s, currencyPair)
                    ));
                }
            }
        }

        return orderObservable;
    }

    /**
     * Gets a stream of all user trades to which we are subscribed.
     *
     * @return The stream of user trades.
     */
    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
        /*Observable<UserTrade> tradeObservable = null;

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                if (tradeObservable == null) {
                    tradeObservable = pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptUserTrade(s, currencyPair)
                    );
                } else {
                    tradeObservable.mergeWith(pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptUserTrade(s, currencyPair)
                    ));
                }

                if (tradeObservable == null) {
                    tradeObservable = pair.getValue().getDeleteUpdates().map(
                            s -> LunoStreamingAdapters.adaptUserTrade(s, currencyPair)
                    );
                } else {
                    tradeObservable.mergeWith(pair.getValue().getDeleteUpdates().map(
                            s -> LunoStreamingAdapters.adaptUserTrade(s, currencyPair)
                    ));
                }
            }
        }

        return tradeObservable;*/
    }
}
