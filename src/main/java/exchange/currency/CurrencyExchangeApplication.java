package exchange.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CurrencyExchangeApplication {

    private final String API_KEY = "9fe916ed620e3666235115fa";
    private final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeApplication.class, args);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(API_URL).build();
    }
}
