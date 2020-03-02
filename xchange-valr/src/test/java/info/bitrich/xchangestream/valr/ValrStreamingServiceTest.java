package info.bitrich.xchangestream.valr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.valr.dto.*;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ValrStreamingServiceTest {

  private ValrStreamingService service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = new ValrStreamingService(ValrStreamingExchange.API_URI, null);
  }

  /*@Test
  public void testAggregatedOrderBookUpdate() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("aggregated_order_book_update.json"));

    TestObserver<ValrWebSocketAggregatedOrderBookUpdate> test = service.getOrderBook().test();

    service.handleMessage(jsonNode);

    List<ValrWebSocketAggregatedOrderBookTrade> asks = new ArrayList<>();
    asks.add(new ValrWebSocketAggregatedOrderBookTrade(
            "sell",
            "0.005",
            "9500",
            "BTCZAR",
            1));

    List<ValrWebSocketAggregatedOrderBookTrade> bids = new ArrayList<>();
    bids.add(new ValrWebSocketAggregatedOrderBookTrade(
            "buy",
            "0.038",
            "9000",
            "BTCZAR",
            1));

    ValrWebSocketAggregatedOrderBookData expected =
            new ValrWebSocketAggregatedOrderBookData(
                    asks,
                    bids
            );

    test.assertValue(expected);

  }*/

  /*@Test
  public void testNewTradeBucket() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("new_trade_bucket.json"));

    TestObserver<ValrWebSocketNewTradeBucketData> test = service.getTradeBuckets().test();

    service.handleMessage(jsonNode);

    ValrWebSocketNewTradeBucketData expected =
            new ValrWebSocketNewTradeBucketData(
                    "BTCZAR",
                    60,
                    "2019-04-25T19:41:00Z",
                    "9500",
                    "9500",
                    "9500",
                    "9500",
                    "0"
            );

    test.assertValue(expected);

  }*/

  /*@Test
  public void testNewTrade() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("new_trade.json"));

    TestObserver<ValrWebSocketNewTradeData> test = service.getTrades().test();

    service.handleMessage(jsonNode);

    ValrWebSocketNewTradeData expected =
            new ValrWebSocketNewTradeData(
                    "9500", "0.001", "BTCZAR", "2019-04-25T19:51:55.393Z", "buy"
            );

    test.assertValue(expected);

  }*/

  @Test
  public void testNewAccountTrade() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("new_account_trade.json"));

    TestObserver<ValrWebSocketNewTradeData> test = service.getAccountTrades().test();

    service.handleMessage(jsonNode);

    ValrWebSocketNewTradeData expected =
            new ValrWebSocketNewTradeData(
                    "9500", "0.00105263", "BTCZAR", "2019-04-25T20:36:53.426Z", "buy"
            );

    test.assertValue(expected);

  }

  @Test
  public void testNewOpenOrdersUpdate() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("new_open_orders_update.json"));

    TestObserver<ValrWebSocketOpenOrdersUpdate> test = service.getOrders().test();

    service.handleMessage(jsonNode);

    ValrWebSocketCurrency baseCurrency = new ValrWebSocketCurrency(
            2,
            "BTC",
            8,
            true,
            "BTC",
            "Bitcoin",
            8,
            8
    );

    ValrWebSocketCurrency quoteCurrency = new ValrWebSocketCurrency(
            1,
            "R",
            2,
            true,
            "ZAR",
            "Rand",
            2,
            2
    );

    ValrWebSocketCurrencyPair currencyPair = new ValrWebSocketCurrencyPair(
            1,
            "BTCZAR",
            baseCurrency,
            quoteCurrency,
            "BTC/ZAR",
            "VALR",
            true,
            0.0001,
            2,
            10,
            100000
    );

    List<ValrWebSocketOpenOrdersUpdateDatum> orders = new ArrayList<>();
    orders.add(new ValrWebSocketOpenOrdersUpdateDatum(
            "38511e49-a755-4f8f-a2b1-232bae6967dc",
            "sell",
            "0.1",
            "10000",
            currencyPair,
            "2019-04-17T19:51:35.776Z",
            "0.1",
            "0.00",
            ""
            ));

    orders.add(new ValrWebSocketOpenOrdersUpdateDatum(
            "d1d9f20a-778c-4f4a-98a1-d336da960158",
            "sell",
            "0.1",
            "10000",
            currencyPair,
            "2019-04-20T13:48:44.922Z",
            "0.1",
            "0.00",
            "4"
    ));

    ValrWebSocketOpenOrdersUpdate expected =
            new ValrWebSocketOpenOrdersUpdate(
                    "OPEN_ORDERS_UPDATE",
                    orders
            );

    test.assertValue(expected);

  }

  @Test
  public void testOrderProcessed() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("order_processed.json"));

    TestObserver<ValrWebSocketOrderProcessedData> test = service.getOrdersProcessed().test();

    service.handleMessage(jsonNode);

    ValrWebSocketOrderProcessedData expected =
            new ValrWebSocketOrderProcessedData(
                    "247dc157-bb5b-49af-b476-2f613b780697",true, ""
            );

    test.assertValue(expected);

  }

  @Test
  public void testOrderStatusUpdated() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("order_status_update.json"));

    TestObserver<ValrWebSocketOrderStatusUpdateData> test = service.getOrderStatusUpdates().test();

    service.handleMessage(jsonNode);

    ValrWebSocketCurrency baseCurrency = new ValrWebSocketCurrency(
            2,
            "BTC",
            8,
            true,
            "BTC",
            "Bitcoin",
            8,
            8
    );

    ValrWebSocketCurrency quoteCurrency = new ValrWebSocketCurrency(
            1,
            "R",
            2,
            true,
            "ZAR",
            "Rand",
            2,
            2
    );

    ValrWebSocketCurrencyPair currencyPair = new ValrWebSocketCurrencyPair(
            1,
            "BTCZAR",
            baseCurrency,
            quoteCurrency,
            "BTC/ZAR",
            "VALR",
            true,
            0.0001,
            2,
            10,
            100000
    );

    ValrWebSocketOrderStatusUpdateData expected =
            new ValrWebSocketOrderStatusUpdateData(
                    "247dc157-bb5b-49af-b476-2f613b780697",
                    "Filled",
                    currencyPair,
                    "80000",
                    "0.01",
                    "0.01",
                    "buy",
                    "limit",
                    "",
                    "2019-05-10T14:47:24.826Z",
                    "2019-05-10T14:42:37.333Z",
                    "4"

            );

    test.assertValue(expected);

  }

  @Test
  public void testFailedCancelOrder() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("failed_cancel_order.json"));

    TestObserver<ValrWebSocketFailedCancelOrderData> test = service.getFailedCancelOrders().test();

    service.handleMessage(jsonNode);

    ValrWebSocketFailedCancelOrderData expected =
            new ValrWebSocketFailedCancelOrderData(
                    "247dc157-bb5b-49af-b476-2f613b780697", "An error occurred while cancelling your order."
            );

    test.assertValue(expected);

  }

  @Test
  public void testBalanceUpdate() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("balance_update.json"));

    TestObserver<ValrWebSocketBalanceUpdateData> test = service.getBalanceUpdates().test();

    service.handleMessage(jsonNode);

    ValrWebSocketBalanceUpdateData expected =
            new ValrWebSocketBalanceUpdateData(
                    new ValrWebSocketCurrency(
                            null,
                            "BTC",
                            8,
                            true,
                            "BTC",
                            "Bitcoin",
                            null,
                            8
                    ),
                    "0.88738681",
                    "0.97803484",
                    "1.86542165"
            );

    test.assertValue(expected);

  }

}
