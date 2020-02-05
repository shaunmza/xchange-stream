package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.luno.dto.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
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

    static Trade adaptTrade(LunoWebSocketCreateUpdate createUpdate, CurrencyPair currencyPair) {
        Trade trade = new UserTrade.Builder()
                .orderId(createUpdate.getOrderId())
                .currencyPair(currencyPair)
                .type(Order.OrderType.valueOf(createUpdate.getType()))
                .originalAmount(new BigDecimal(createUpdate.getVolume()))
                .price(new BigDecimal(createUpdate.getPrice()))
                .build();

        return trade;
    }

    static UserTrade adaptUserTrade(LunoWebSocketDeleteUpdate trade, CurrencyPair currencyPair) {
        return new UserTrade.Builder()
                .currencyPair(currencyPair)
                .orderId(trade.getOrderId())
                .build();
    }

    static Ticker adaptTicker(LunoWebSocketTradeUpdate trade, CurrencyPair currencyPair) {
        BigDecimal bid = BigDecimal.ZERO;
        BigDecimal ask = BigDecimal.ZERO;
        BigDecimal price = new BigDecimal(trade.getCounter()).divide(new BigDecimal(trade.getBase()));
        //if (Order.OrderType.valueOf(trade.getType()) == BID) {
            bid = price;
        //} else {
        //    ask = price;
        //}
        return new Ticker.Builder()
                .currencyPair(currencyPair)
                .volume(new BigDecimal(trade.getBase()))
                .bid(bid)
                .ask(ask)
                .build();
    }

    static Trade adaptTrade(LunoWebSocketTradeUpdate tradeUpdate, CurrencyPair currencyPair) {
        Trade trade = new UserTrade.Builder()
                .currencyPair(currencyPair)
                .originalAmount(new BigDecimal(tradeUpdate.getBase()))
                .price(new BigDecimal(tradeUpdate.getCounter()).divide(new BigDecimal(tradeUpdate.getBase())))
                .build();

        trade.setMakerOrderId(tradeUpdate.getMakerOrderId());
        trade.setTakerOrderId(tradeUpdate.getTakerOrderId());
        return trade;
    }

    static Trade adaptTrade(LunoWebSocketDeleteUpdate trade, CurrencyPair currencyPair) {
        return new Trade.Builder()
                .currencyPair(currencyPair)
                .id(trade.getOrderId())
                .build();
    }
}