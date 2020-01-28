package info.bitrich.xchangestream.luno;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.luno.LunoStreamingExchange;
import info.bitrich.xchangestream.luno.LunoStreamingService;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketOrder;
import info.bitrich.xchangestream.luno.dto.LunoWebSocketOrderBook;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class LunoStreamingServiceTest {

  private LunoStreamingService service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = new LunoStreamingService(LunoStreamingExchange.API_URI);
  }

  @Test
  public void testOrderbook() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
            objectMapper.readTree(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("order-book.json"));

    TestObserver<LunoWebSocketOrderBook> test = service.getOrderBook().test();

    service.handleMessage(jsonNode);

    List<LunoWebSocketOrder> asks = new ArrayList<>();
    asks.add(new LunoWebSocketOrder(
            "23298343",
            "1234.00",
            "0.93"));

    List<LunoWebSocketOrder> bids = new ArrayList<>();
    bids.add(new LunoWebSocketOrder(
            "3498282",
            "1201.00",
            "1.22"));

    LunoWebSocketOrderBook expected =
            new LunoWebSocketOrderBook(
                    "24352",
                    asks,
                    bids,
                    "ACTIVE",
                    -124026355
            );

    test.assertValue(expected);

  }
/*
  @Test
  public void testGetOrderExecution_orderPlace() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("order-place.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<Order> test = service.getOrderData().test();

    service.handleMessage(jsonNode);

    CexioOrder expected =
        new CexioOrder(
            Order.OrderType.BID,
            CurrencyPair.BTC_USD,
            new BigDecimal("0.002"),
            "5913254239",
            new Date(1522135708956L),
            new BigDecimal("7176.5"),
            new BigDecimal("0.16"),
            Order.OrderStatus.NEW);
    test.assertValue(expected);
  }

  @Test
  public void testGetOrderExecution_orderFill() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("order-fill.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<Order> test = service.getOrderData().test();

    service.handleMessage(jsonNode);

    CexioOrder expected =
        new CexioOrder(
            CurrencyPair.BTC_USD, "5891752542", Order.OrderStatus.FILLED, BigDecimal.ZERO);
    test.assertValue(expected);
  }

  @Test
  public void testGetOrderExecution_orderPartialFill() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("order-partial-fill.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<Order> test = service.getOrderData().test();

    service.handleMessage(jsonNode);

    CexioOrder expected =
        new CexioOrder(
            CurrencyPair.BTC_USD,
            "5891752542",
            Order.OrderStatus.PARTIALLY_FILLED,
            new BigDecimal("0.002"));
    test.assertValue(expected);
  }

  @Test
  public void testGetOrderExecution_orderCancel() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("order-cancel.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<Order> test = service.getOrderData().test();

    service.handleMessage(jsonNode);

    CexioOrder expected =
        new CexioOrder(
            CurrencyPair.BTC_USD,
            "5891717811",
            Order.OrderStatus.CANCELED,
            new BigDecimal("0.002"));
    test.assertValue(expected);
  }

  @Test
  public void testGetOrderExecution_invalidJson() throws Exception {
    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<Order> test = service.getOrderData().test();

    service.messageHandler("wrong");

    test.assertError(IOException.class);
  }

  @Test
  public void testGetTransaction_orderPlace() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("transaction-place.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<CexioWebSocketTransaction> test = service.getTransactions().test();

    service.handleMessage(jsonNode);

    CexioWebSocketTransaction transaction =
        new CexioWebSocketTransaction(
            "5915157030",
            "order:5915157028:a:USD",
            "user:up118134628:a:USD",
            new BigDecimal("0.02"),
            new BigDecimal("16.40"),
            new BigDecimal("35.24"),
            "up118134628",
            "USD",
            null,
            new BigDecimal("-16.40"),
            5915157028L,
            null,
            null,
            null,
            "buy",
            Date.from(Instant.parse("2018-03-27T15:16:52.016Z")),
            new BigDecimal("35.24"),
            null);

    test.assertValue(transaction);
  }

  @Test
  public void testGetTransaction_orderExecute() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("transaction-exec.json"));

    CexioStreamingRawService service = cexioStreamingExchange.getStreamingRawService();

    TestObserver<CexioWebSocketTransaction> test = service.getTransactions().test();

    service.handleMessage(jsonNode);

    CexioWebSocketTransaction transaction =
        new CexioWebSocketTransaction(
            "5918682827",
            "order:5918682821:a:BTC",
            "user:up118134628:a:BTC",
            new BigDecimal("0.002"),
            new BigDecimal("0"),
            new BigDecimal("0.006"),
            "up118134628",
            "BTC",
            "USD",
            new BigDecimal("0.002"),
            5918682821L,
            5918682821L,
            5918682779L,
            new BigDecimal("8030"),
            "buy",
            Date.from(Instant.parse("2018-03-28T05:41:49.482Z")),
            new BigDecimal("0.006"),
            new BigDecimal("0.05"));

    test.assertValue(transaction);
  }*/
}
