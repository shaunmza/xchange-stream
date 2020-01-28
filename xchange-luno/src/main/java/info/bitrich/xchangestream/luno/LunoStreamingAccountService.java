package info.bitrich.xchangestream.luno;

import info.bitrich.xchangestream.core.StreamingAccountService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LunoStreamingAccountService implements StreamingAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(LunoStreamingAccountService.class);

    private Map<CurrencyPair, LunoStreamingService> streamingServices;

    public LunoStreamingAccountService(Map<CurrencyPair, LunoStreamingService> streamingServices) {
        this.streamingServices = streamingServices;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }
}
