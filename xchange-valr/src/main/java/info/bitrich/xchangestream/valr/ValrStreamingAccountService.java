package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.StreamingAccountService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValrStreamingAccountService implements StreamingAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(ValrStreamingAccountService.class);

    private ValrStreamingService service;

    public ValrStreamingAccountService(ValrStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return service.getBalanceUpdates()
                .filter(o -> currency.equals(o.getCurrency()))
                .map(ValrStreamingAdapters::adaptBalance);
    }
}
