package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketAggregatedOrderBookData;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

import java.util.function.Function;

public class ValrStreamingTradeService implements StreamingTradeService {

    private final ValrStreamingService service;

    public ValrStreamingTradeService(ValrStreamingService service) {
        this.service = service;
    }

    /**
     * Gets a stream of all trade updates filtered by currency pair to which we are subscribed.
     *
     * @return The stream of trade updates filtered by currency pair.
     */
    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return service.getOrderStatusUpdates()
                .filter(o -> currencyPair.equals(o.getCurrencyPair()))
                .map(ValrStreamingAdapters::adaptLimitOrder);
    }

    /**
     * Gets a stream of all user trades to which we are subscribed.
     *
     * @return The stream of user trades.
     */
    public Observable<UserTrade> getUserTrades() {
        return service.getAccountTrades()
                .map(ValrStreamingAdapters::adaptUserTrade);
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return getUserTrades()
                .filter(t -> currencyPair.equals(t.getCurrencyPair()));
    }
}
