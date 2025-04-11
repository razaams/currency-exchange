package exchange.currency;

import exchange.currency.controller.BillController;
import exchange.currency.domain.BillResponse;
import exchange.currency.security.SecurityConfig;
import exchange.currency.service.BillCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillCalculationService billCalculationService;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;

    @BeforeEach
    void setup() {
        when(webClientBuilder.baseUrl(Mockito.anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void testCalculatePayable_WithMockedBillCalculationService_withUnAuthenticated() throws Exception {
        BillResponse billResponse = BillResponse.builder()
                .currency("PKR")
                .payableAmount(200.0)
                .build();

        when(billCalculationService.calculatePayableBill(any())).thenReturn(billResponse);

        String requestJson = """
                {
                    "user": {
                        "userType": "EMPLOYEE",
                        "customerSince": "2024-07-07"
                    },
                    "originalCurrency": "USD",
                    "targetCurrency": "PKR",
                    "items": [
                        {
                            "name": "Apple",
                            "price": 30,
                            "grocery": true
                        },
                        {
                            "name": "Room Cooler",
                            "price": 35,
                            "grocery": false
                        },
                        {
                            "name": "Air Condition",
                            "price": 80,
                            "grocery": false
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCalculatePayable_WithMockedBillCalculationService_withAuthenticated() throws Exception {
        BillResponse billResponse = BillResponse.builder()
                .currency("PKR")
                .payableAmount(200.0)
                .build();

        when(billCalculationService.calculatePayableBill(any())).thenReturn(billResponse);

        String requestJson = """
                {
                    "user": {
                        "userType": "EMPLOYEE",
                        "customerSince": "2024-07-07"
                    },
                    "originalCurrency": "USD",
                    "targetCurrency": "PKR",
                    "items": [
                        {
                            "name": "Apple",
                            "price": 30,
                            "grocery": true
                        },
                        {
                            "name": "Room Cooler",
                            "price": 35,
                            "grocery": false
                        },
                        {
                            "name": "Air Condition",
                            "price": 80,
                            "grocery": false
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("Authorization", getBasicAuthHeader("user1", "SecurePassword@1")))
                .andExpect(status().isOk());
    }


    private String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }
}
