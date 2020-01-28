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
}
