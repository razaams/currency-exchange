package exchange.currency;

import exchange.currency.client.ExchangeRateClient;
import exchange.currency.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
@AutoConfigureMockMvc
class ExchangeRateServiceTests {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @Test
    void testEmployeeBill() {

        Map<String, Double> rates = new HashMap<>();
        rates.put("PKR", 280d);

        when(exchangeRateClient.getRates("USD")).thenReturn(rates);

        double convertedAmount = exchangeRateService.convert("USD", "PKR", 10);

        assertEquals(2800, convertedAmount);
    }
}
