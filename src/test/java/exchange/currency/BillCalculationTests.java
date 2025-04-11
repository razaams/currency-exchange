package exchange.currency;

import exchange.currency.domain.*;
import exchange.currency.service.BillCalculationService;
import exchange.currency.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
@AutoConfigureMockMvc
class BillCalculationTests {

    @Autowired
    private BillCalculationService billCalculationService;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    void testEmployeeBill() {

        User employee = new User(UserType.EMPLOYEE, LocalDate.now());
        BillRequest employeeBill = BillRequest.builder().user(employee).items(Arrays.asList(new Item("Room cooler", 40, false),
                        new Item("Air condition", 90, false), new Item("Chair", 70, true)))
                .originalCurrency("USD")
                .targetCurrency("PKR")
                .build();

        when(exchangeRateService.convert("USD", "PKR", 151d)).thenReturn(2800.0);

        BillResponse billResponse = billCalculationService.calculatePayableBill(employeeBill);

        assertEquals(2800, billResponse.getPayableAmount());
        assertEquals("PKR", billResponse.getCurrency());

        verify(exchangeRateService, times(1)).convert("USD", "PKR", 151);
    }

    @Test
    void testAffiliateBill() {
        User affiliate = new User(UserType.AFFILIATE, LocalDate.now());
        BillRequest affiliateBill = BillRequest.builder().user(affiliate)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .originalCurrency("USD")
                .targetCurrency("PKR")
                .build();

        when(exchangeRateService.convert("USD", "PKR", 177d)).thenReturn(2800.0);

        BillResponse billResponse = billCalculationService.calculatePayableBill(affiliateBill);

        assertEquals(2800, billResponse.getPayableAmount());
        assertEquals("PKR", billResponse.getCurrency());

        verify(exchangeRateService, times(1)).convert("USD", "PKR", 177);
    }

    @Test
    void testNotLoyalCustomerBill() {
        User newCustomer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest newCustomerBill = BillRequest.builder().user(newCustomer)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .originalCurrency("USD")
                .targetCurrency("PKR")
                .build();

        when(exchangeRateService.convert("USD", "PKR", 190d)).thenReturn(2800.0);

        BillResponse billResponse = billCalculationService.calculatePayableBill(newCustomerBill);

        assertEquals(2800, billResponse.getPayableAmount());
        assertEquals("PKR", billResponse.getCurrency());

        verify(exchangeRateService, times(1)).convert("USD", "PKR", 190);
    }

    @Test
    void testLoyalCustomerBill() {
        User loyalCustomer = new User(UserType.CUSTOMER, LocalDate.now().minusYears(3));
        BillRequest loyalCustomerBill = BillRequest.builder().user(loyalCustomer)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .originalCurrency("USD")
                .targetCurrency("PKR")
                .build();

        when(exchangeRateService.convert("USD", "PKR", 183.5d)).thenReturn(2800.0);

        BillResponse billResponse = billCalculationService.calculatePayableBill(loyalCustomerBill);

        assertEquals(2800, billResponse.getPayableAmount());
        assertEquals("PKR", billResponse.getCurrency());

        verify(exchangeRateService, times(1)).convert("USD", "PKR", 183.5);
    }
}
