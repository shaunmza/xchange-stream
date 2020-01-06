package info.bitrich.xchangestream.valr;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketAggregatedOrderBookData;
import info.bitrich.xchangestream.valr.dto.ValrWebSocketAggregatedOrderBookUpdate;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.valr.ValrAdapters;

import java.util.HashMap;
import java.util.Map;


public class ValrStreamingMarketDataService implements StreamingMarketDataService {

    private final ValrStreamingService service;

    private final Map<CurrencyPair, ValrWebSocketAggregatedOrderBookData> orderbooks = new HashMap<>();

    public ValrStreamingMarketDataService(ValrStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channelName = "AGGREGATED_ORDERBOOK_UPDATE";
        final String depth = args.length > 0 ? args[0].toString() : "20";
        String pair = currencyPair.base.toString() + currencyPair.counter.toString();
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        Observable<ValrWebSocketAggregatedOrderBookUpdate> subscribedChannel = service.subscribeChannel(channelName,
                new Object[]{pair, "P0", depth})
                .map(s -> {
                    if (s.get(1).get(0).isArray()) return mapper.treeToValue(s,
                            ValrWebSocketAggregatedOrderBookUpdate.class);
                    else return mapper.treeToValue(s, ValrWebSocketAggregatedOrderBookUpdate.class);
                });

        return subscribedChannel
                .map(s -> ValrStreamingAdapters.adaptOrderBook(s));
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        return service.getTrades()
                .filter(o -> currencyPair.equals(o.getCurrencyPair()))
                .map(ValrStreamingAdapters::adaptTrade);
    }
}