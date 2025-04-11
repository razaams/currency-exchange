package exchange.currency;

import exchange.currency.discount.BillAmountDiscountContext;
import exchange.currency.domain.BillRequest;
import exchange.currency.domain.Item;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class BillAmountDiscountTests {

    @Autowired
    private BillAmountDiscountContext billAmountDiscountContext;

    @Test
    void testDiscountTotalAmountOver_100() {
        User customer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest customerBill = BillRequest.builder().user(customer).items(Arrays.asList(new Item("Room cooler", 40, false),
                new Item("Air condition", 90, false), new Item("Chair", 70, true))).build();

        assertEquals(10, billAmountDiscountContext.applyDiscount(customerBill));
    }

    @Test
    void testDiscountTotalAmountBelow_100() {
        User customer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest customerBill = BillRequest.builder().user(customer).items(Arrays.asList(new Item("Room cooler", 10, false),
                new Item("Air condition", 50, false), new Item("Chair", 30, true))).build();

        assertEquals(0, billAmountDiscountContext.applyDiscount(customerBill));
    }
}
