package exchange.currency;


import exchange.currency.discount.BillAmountDiscount;
import exchange.currency.discount.strategy.PerHundredAmountDiscountStrategy;
import exchange.currency.domain.BillRequest;
import exchange.currency.domain.Item;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class BillAmountDiscountStrategyApplicabilityTests {

    @Test
    void testBillAmountOver_100_Applicable() {

        User customer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest bill = BillRequest.builder().user(customer).items(Arrays.asList(new Item("Apple", 50, true), new Item("Banana", 50, true), new Item("Chair", 90, false))).build();

        BillAmountDiscount perHundredBillAmountDiscountStrategy = new PerHundredAmountDiscountStrategy();

        assertTrue(perHundredBillAmountDiscountStrategy.supports(bill));
    }

    @Test
    void testBillAmountBelow_100_Applicable() {
        User customer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest bill = BillRequest.builder().user(customer).items(Arrays.asList(new Item("Apple", 5, true), new Item("Banana", 3, true), new Item("Chair", 40, false))).build();

        BillAmountDiscount perHundredBillAmountDiscountStrategy = new PerHundredAmountDiscountStrategy();

        assertFalse(perHundredBillAmountDiscountStrategy.supports(bill));
    }
}
