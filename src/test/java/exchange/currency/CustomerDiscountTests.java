package exchange.currency;

import exchange.currency.discount.CustomerDiscountContext;
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
class CustomerDiscountTests {

    @Autowired
    private CustomerDiscountContext customerDiscountContext;

    @Test
    void testEmployeeDiscount() {
        User employee = new User(UserType.EMPLOYEE, LocalDate.now());
        BillRequest employeeBill = BillRequest.builder().user(employee).items(Arrays.asList(new Item("Room cooler", 40, false),
                new Item("Air condition", 90, false), new Item("Chair", 70, true))).build();

        assertEquals(39, customerDiscountContext.applyDiscount(employeeBill));
    }

    @Test
    void testAffiliateDiscount() {
        User affiliate = new User(UserType.AFFILIATE, LocalDate.now());
        BillRequest affiliateBill = BillRequest.builder().user(affiliate)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .build();

        assertEquals(13, customerDiscountContext.applyDiscount(affiliateBill));
    }

    @Test
    void testNewCustomerDiscount() {
        User newCustomer = new User(UserType.CUSTOMER, LocalDate.now());
        BillRequest newCustomerBill = BillRequest.builder().user(newCustomer)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .build();

        assertEquals(0, customerDiscountContext.applyDiscount(newCustomerBill));
    }

    @Test
    void testLoyalCustomerDiscount() {
        User loyalCustomer = new User(UserType.CUSTOMER, LocalDate.now().minusYears(3));
        BillRequest loyalCustomerBill = BillRequest.builder().user(loyalCustomer)
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, false),
                        new Item("Chair", 70, true)))
                .build();

        assertEquals(6.5, customerDiscountContext.applyDiscount(loyalCustomerBill));
    }
}
