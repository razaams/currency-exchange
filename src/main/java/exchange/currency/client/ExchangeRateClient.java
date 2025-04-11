package exchange.currency.client;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@AllArgsConstructor
public class ExchangeRateClient {

    private WebClient webClient;

    @Cacheable(value = "exchangeRates", key = "#fromCurrency")
    public Map<String, Double> getRates(String fromCurrency) {
        Map<String, Object> response = webClient.get()
                .uri(fromCurrency)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (Map<String, Double>) response.get("conversion_rates");
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        Map<String, Double> rates = getRates(fromCurrency);
        double rate = rates.get(toCurrency);
        return amount * rate;
    }
}
