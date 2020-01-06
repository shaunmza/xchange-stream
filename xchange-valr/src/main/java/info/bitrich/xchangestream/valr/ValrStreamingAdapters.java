package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.valr.dto.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.valr.ValrAdapters;
import org.knowm.xchange.valr.ValrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.StreamSupport.stream;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

class ValrStreamingAdapters {

    private static final Logger LOG = LoggerFactory.getLogger(ValrStreamingAdapters.class);

    private static final BigDecimal THOUSAND = new BigDecimal(1000);

    static UserTrade adaptUserTrade(ValrWebSocketNewTradeData userTrade) {
        CurrencyPair pair = toCurrencyPair(userTrade.getCurrencyPair());
        return new UserTrade.Builder()
            .currencyPair(pair)
            .feeAmount(null)
            .feeCurrency(null)
            .id(null)
            .orderId(null)
            .originalAmount(new BigDecimal(userTrade.getQuantity()))
            .price(new BigDecimal(userTrade.getPrice()))
            .timestamp(ValrUtils.toDate(userTrade.getTradedAt()))
            .type(userTrade.getTakerSide() == "buy" ? BID : ASK)
            .build();
    }

    static LimitOrder adaptLimitOrder(ValrWebSocketNewTradeData userTrade) {
        CurrencyPair pair = toCurrencyPair(userTrade.getCurrencyPair());
        return new LimitOrder.Builder(userTrade.getTakerSide() == "buy" ? BID : ASK, pair)
                .id(null)
                .originalAmount(new BigDecimal(userTrade.getQuantity()))
                .timestamp(ValrUtils.toDate(userTrade.getTradedAt()))
                .build();
    }

    static LimitOrder adaptLimitOrder(ValrWebSocketOrderStatusUpdateData userTrade) {
        return new LimitOrder.Builder(userTrade.getOrderType() == "buy" ? BID : ASK, userTrade.getCurrencyPair().toCurrencyPair())
                .id(userTrade.getOrderId())
                .orderStatus(toOrderStatus(userTrade.getOrderStatusType()))
                .build();
    }

    static Trade adaptTrade(ValrWebSocketNewTradeData trade) {
        CurrencyPair pair = toCurrencyPair(trade.getCurrencyPair());
        return new Trade.Builder()
                .type(trade.getTakerSide() == "buy" ? BID : ASK)
                .currencyPair(pair)
                .id(null)
                .originalAmount(new BigDecimal(trade.getQuantity()))
                .price(new BigDecimal(trade.getPrice()))
                .timestamp(ValrUtils.toDate(trade.getTradedAt()))
                .build();
    }

    static OrderBook adaptOrderBook(ValrWebSocketAggregatedOrderBookUpdate orderbookUpdate) {
        List<LimitOrder> asks = new ArrayList<>(0);
        List<LimitOrder> bids = new ArrayList<>(0);

        CurrencyPair pair = toCurrencyPair(orderbookUpdate.getCurrencyPairSymbol());

        for (ValrWebSocketAggregatedOrderBookTrade ask : orderbookUpdate.getData().getAsks()) {
            asks.add(new LimitOrder(
                    ASK,
                    new BigDecimal(ask.getQuantity()),
                    pair,
                    null,
                    (Date)null,
                    new BigDecimal(ask.getPrice())
                    )
            );
        }

        for (ValrWebSocketAggregatedOrderBookTrade bid : orderbookUpdate.getData().getBids()) {
            bids.add(new LimitOrder(
                            BID,
                            new BigDecimal(bid.getQuantity()),
                            pair,
                            null,
                            (Date)null,
                            new BigDecimal(bid.getPrice())
                    )
            );
        }


        OrderBook orderBook = new OrderBook(null, asks, bids);
        return orderBook;

    }

    static CurrencyPair toCurrencyPair(String pair){
        return new CurrencyPair(pair.substring(0, 3), pair.substring(3));
    }

    static Balance adaptBalance(ValrWebSocketBalanceUpdateData balance) {
        return new Balance.Builder()
                .currency(new Currency(balance.getCurrency().toString()))
                .available(new BigDecimal(balance.getAvailable()))
                .frozen(new BigDecimal(balance.getReserved()))
                .total(new BigDecimal(balance.getTotal()))
                .build();
    }

    static Order.OrderStatus toOrderStatus(String status){
        switch (status) {
            case "Placed":
                return Order.OrderStatus.NEW;

            case "Failed":
            case "Instant Order Balance Reserve Failed":
                return Order.OrderStatus.REJECTED;

            case "Cancelled":
                return Order.OrderStatus.CANCELED;

            case "Filled":
            case"Instant Order Completed":
                return Order.OrderStatus.FILLED;

            case "Partially Filled":
                return Order.OrderStatus.PARTIALLY_FILLED;

            case "Instant Order Balance Reserved":
                return Order.OrderStatus.PENDING_NEW;

            default:
                return Order.OrderStatus.UNKNOWN;

        }
    }
}