package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.luno.LunoUtil;

import java.util.List;
import java.util.Map;

public class LunoStreamingMarketDataService implements StreamingMarketDataService {

    private final Map<CurrencyPair, LunoStreamingService> streamingServices;

    public LunoStreamingMarketDataService(Map<CurrencyPair, LunoStreamingService> streamingServices) {
        this.streamingServices = streamingServices;
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        Observable<Trade> tradeObservable = null;

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                if (tradeObservable == null) {
                    tradeObservable = pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    );
                } else {
                    tradeObservable.mergeWith(pair.getValue().getCreateUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    ));
                }

                /*if (tradeObservable == null) {
                    tradeObservable = pair.getValue().getTradeUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    );
                } else {
                    tradeObservable.mergeWith(pair.getValue().getTradeUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    ));
                }*/

                if (tradeObservable == null) {
                    tradeObservable = pair.getValue().getDeleteUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    );
                } else {
                    tradeObservable.mergeWith(pair.getValue().getDeleteUpdates().map(
                            s -> LunoStreamingAdapters.adaptTrade(s, currencyPair)
                    ));
                }
            }
        }

        return tradeObservable;
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        Observable<OrderBook> orderBookObservable = null;

        for (Map.Entry<CurrencyPair, LunoStreamingService> pair : this.streamingServices.entrySet()) {
            if (pair.getKey().toString().equals(currencyPair.toString())) {
                if (orderBookObservable == null) {
                    orderBookObservable = pair.getValue().getOrderBook().map(
                            s -> LunoStreamingAdapters.adaptOrderBooks(s, currencyPair)
                    );
                }
            }
        }

        return orderBookObservable;
    }
}
