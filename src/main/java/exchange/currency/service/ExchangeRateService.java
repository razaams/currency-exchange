package exchange.currency.service;

import exchange.currency.client.ExchangeRateClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;

    public double convert(String fromCurrency, String toCurrency, double amount) {
        Map<String, Double> rates = exchangeRateClient.getRates(fromCurrency);
        double rate = rates.get(toCurrency);
        return amount * rate;
    }
}
