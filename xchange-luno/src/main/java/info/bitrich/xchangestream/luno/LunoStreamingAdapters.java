package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.luno.dto.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

class LunoStreamingAdapters {

    private static final Logger LOG = LoggerFactory.getLogger(LunoStreamingAdapters.class);

    protected static OrderBook adaptOrderBooks(LunoWebSocketOrderBook lunoOrderBook, CurrencyPair currencyPair) {
        List<LimitOrder> asks = new ArrayList<>(0);
        List<LimitOrder> bids = new ArrayList<>(0);

        for (LunoWebSocketOrder ask : lunoOrderBook.getAsks()) {
            asks.add(new LimitOrder(
                            ASK,
                            new BigDecimal(ask.getVolume()),
                            currencyPair,
                            ask.getId(),
                            null,
                            new BigDecimal(ask.getPrice())
                    )
            );
        }

        for (LunoWebSocketOrder bid : lunoOrderBook.getBids()) {
            bids.add(new LimitOrder(
                            BID,
                            new BigDecimal(bid.getVolume()),
                            currencyPair,
                            bid.getId(),
                            null,
                            new BigDecimal(bid.getPrice())
                    )
            );
        }


        OrderBook orderBook = new OrderBook(null, asks, bids);
        return orderBook;
    }

    static LimitOrder adaptLimitOrder(LunoWebSocketCreateUpdate userTrade, CurrencyPair currencyPair) {
        return new LimitOrder.Builder(Order.OrderType.valueOf(userTrade.getType()), currencyPair)
                .id(userTrade.getOrderId())
                .orderStatus(Order.OrderStatus.NEW)
                .limitPrice(new BigDecimal(userTrade.getPrice()))
                .remainingAmount(new BigDecimal(userTrade.getVolume()))
                .build();
    }

    static UserTrade adaptUserTrade(LunoWebSocketTradeUpdate trade, CurrencyPair currencyPair) {
        UserTrade userTrade = new UserTrade.Builder()
                    .currencyPair(currencyPair)
                    .id(trade.getMakerOrderId())
                    .build();

        return userTrade;
    }

    static UserTrade adaptUserTrade(LunoWebSocketDeleteUpdate trade, CurrencyPair currencyPair) {
        return new UserTrade.Builder()
                .currencyPair(currencyPair)
                .id(trade.getOrderId())
                .build();
    }

    static Trade adaptTrade(LunoWebSocketCreateUpdate trade, CurrencyPair currencyPair) {
        return new Trade.Builder()
                .currencyPair(currencyPair)
                .id(trade.getOrderId())
                .price(new BigDecimal(trade.getPrice()))
                .originalAmount(new BigDecimal(trade.getVolume()))
                .build();
    }

    static Trade adaptTrade(LunoWebSocketTradeUpdate trade, CurrencyPair currencyPair) {
        return new Trade.Builder()
                .currencyPair(currencyPair)
                .id(trade.getMakerOrderId())
                .build();
    }

    static Trade adaptTrade(LunoWebSocketDeleteUpdate trade, CurrencyPair currencyPair) {
        return new Trade.Builder()
                .currencyPair(currencyPair)
                .id(trade.getOrderId())
                .build();
    }
}