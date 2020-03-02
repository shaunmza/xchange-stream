package info.bitrich.xchangestream.valr;

import info.bitrich.xchangestream.core.StreamingAccountService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class ValrStreamingAccountService implements StreamingAccountService {

    private ValrStreamingService service;

    public ValrStreamingAccountService(ValrStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return service.getBalanceUpdates()
                .filter(o -> currency.equals(Currency.getInstance(o.getCurrency().getSymbol())))
                .map(ValrStreamingAdapters::adaptBalance);
    }
}
